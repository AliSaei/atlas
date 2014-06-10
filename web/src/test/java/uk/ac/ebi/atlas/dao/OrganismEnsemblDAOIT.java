package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class OrganismEnsemblDAOIT {

    @Inject
    private OrganismEnsemblDAO subject;


    @Test
    public void getOrganismEnsemblNamesMap() throws Exception {
        Map<String, String> organismEnsemblNames = subject.getEnsemblOrganismNamesMap();

        assertThat(organismEnsemblNames.get("Homo sapiens"), is("ensembl"));
        assertThat(organismEnsemblNames.get("Mus musculus"), is("ensembl"));
        assertThat(organismEnsemblNames.size(), is(30));
        assertThat(organismEnsemblNames.get("Arabidopsis thaliana"), is("plants"));

    }

}
