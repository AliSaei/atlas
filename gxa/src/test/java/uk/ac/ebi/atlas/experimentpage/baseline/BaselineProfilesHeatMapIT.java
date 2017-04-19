package uk.ac.ebi.atlas.experimentpage.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class BaselineProfilesHeatMapIT {

    public static final String ORGANISM_PART = "ORGANISM_PART";

    private BaselineProfilesHeatMap subject;

    @Inject
    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;

    @Inject
    SolrQueryService solrQueryService;

    @Inject
    RnaSeqBaselineProfileStreamFactory inputStreamFactory;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext baselineRequestContext;

    BaselineExperiment baselineExperiment;

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Before
    public void initRequestContext() throws ExecutionException {

        String randomAccession = experimentTrader.getAllBaselineExperimentAccessions().iterator().next();
        baselineExperiment = rnaSeqBaselineExperimentsCache.getExperiment(randomAccession);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        baselineRequestContext = new BaselineRequestContext(requestPreferences, baselineExperiment);

        subject = new BaselineProfilesHeatMap(inputStreamFactory);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1733?displayLevels=true&_specific=on&geneQuery=R-HSA-73887&geneSetMatch=true
    @Test
    public void weCanGetAnyExperimentAtAll()  {
        if(Math.random() < 0.5) {
            setNotSpecific();
        }
        if(Math.random() < 0.5) {
            setGeneQuery("protein_coding");
        }

        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse
                (baselineRequestContext.getGeneQuery(),baselineRequestContext.getSpecies().getReferenceName());

        BaselineProfilesList profiles = subject.fetch(baselineExperiment, baselineRequestContext,
                geneQueryResponse, true);

        assertThat(profiles.size(), greaterThan(0));
    }

    private void setGeneQuery(String geneQueryString) {
        requestPreferences.setGeneQuery(SemanticQuery.create(geneQueryString));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
