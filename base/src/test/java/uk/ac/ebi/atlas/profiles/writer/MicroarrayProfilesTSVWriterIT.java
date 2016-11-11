package uk.ac.ebi.atlas.profiles.writer;

import uk.ac.ebi.atlas.testutils.RegexMatcher;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class MicroarrayProfilesTSVWriterIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-GEOD-13316";

    @Inject
    private MicroarrayProfilesTSVWriter subject;

    @Inject
    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    private MicroarrayRequestContextBuilder microarrayRequestContextBuilder;

    private MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

    private MicroarrayExperiment microarrayExperiment;

    @Before
    public void setUp() throws Exception {

        microarrayExperiment = microarrayExperimentsCache.getExperiment(MICROARRAY_EXPERIMENT_ACCESSION);

    }

    @Test
    public void secondHeaderLineShouldDescribeQueryAlsoWhenSelectingContrasts() {

        requestPreferences.setQueryFactorValues(Sets.newTreeSet(Sets.newHashSet("g2_g1")));

        MicroarrayRequestContext requestContext = microarrayRequestContextBuilder.forExperiment(microarrayExperiment)
                .withPreferences(requestPreferences).build();

        String[] headerRows = subject.getTsvFileMasthead(requestContext, false).split("\n");

        assertThat(headerRows[1], RegexMatcher.matches("# Query: Genes.*differentially.*"+MICROARRAY_EXPERIMENT_ACCESSION));
        assertThat(headerRows[2], startsWith("# Timestamp: "));
    }
}