package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.atlas.experimentpage.fastqc.FastQCReportUtil;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class ProteomicsBaselineDesignPageControllerIT {

    private static final String EXPERIMENT_ACCESSION = "E-PROT-1";

    /*
    // cut -f 1 ~/ATLAS3.TEST/integration-test-data/expdesign/ExpDesign-E-PROT-1.tsv | tr $'\n' ,
     */
    private static final List<String> RUNS = Lists.newArrayList("Adult_Adrenalgland","Adult_Bcells","Adult_CD4Tcells","Adult_CD8Tcells","Adult_Colon",
            "Adult_Esophagus","Adult_Frontalcortex","Adult_Gallbladder","Adult_Heart","Adult_Kidney","Adult_Liver","Adult_Lung","Adult_Monocytes","Adult_NKcells",
            "Adult_Ovary","Adult_Pancreas","Adult_Platelets","Adult_Prostate","Adult_Rectum","Adult_Retina","Adult_Spinalcord","Adult_Testis","Adult_Urinarybladder",
            "Fetal_Brain","Fetal_Gut","Fetal_Heart","Fetal_Liver","Fetal_Ovary","Fetal_Placenta","Fetal_Testis");

    private static final Set<String> runSet = Sets.newLinkedHashSet();

    ExperimentDesignPageRequestHandler subject;

    @Inject
    ArrayDesignTrader arrayDesignTrader;
    @Inject
    ExperimentTrader experimentTrader;
    @Inject
    FastQCReportUtil fastQCReportUtil;

    private HttpServletRequest requestMock;

    Model model = new BindingAwareModelMap();

    @Before
    public void initSubject() throws Exception {
        subject = new ExperimentDesignPageRequestHandler(experimentTrader);
        requestMock = mock(HttpServletRequest.class);
        when(requestMock.getRequestURI()).thenReturn("/gxa/experiments/" + EXPERIMENT_ACCESSION + "/experiment-design");

    }

    @Test
    public void testExtractProteomicsExperimentDesign() throws IOException {

        //given
        subject.handleRequest(EXPERIMENT_ACCESSION,model,requestMock,"","");

        Gson gson = new Gson();
        runSet.addAll(RUNS);
        // then
        Map<String, Object> map = model.asMap();
        assertThat(((String) map.get("assayHeaders")), is("[\"Run\"]"));

        // and
        assertThat((String) map.get("runAccessions"), is(gson.toJson(runSet)));

        // and
        assertThat((String) map.get("experimentAccession"), is(EXPERIMENT_ACCESSION));

        // and
        String[] samples = gson.fromJson((String) map.get("sampleHeaders"), String[].class);
        assertThat(samples, arrayContaining("developmental stage", "organism", "organism part"));
        String[] factors = gson.fromJson((String) map.get("factorHeaders"), String[].class);
        assertThat(factors, arrayContaining("developmental stage", "organism part"));

        // and
        Type listStringArrayType = new TypeToken<List<String[]>>() {
        }.getType();
        List<String[]> data = gson.fromJson((String) map.get("tableData"), listStringArrayType);
        assertThat(data.get(0), hasItemInArray("Adult_Adrenalgland"));
        assertThat(data.get(data.size() - 1), hasItemInArray("Fetal_Testis"));
    }
}