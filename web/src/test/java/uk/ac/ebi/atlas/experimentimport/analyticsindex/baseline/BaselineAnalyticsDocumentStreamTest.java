package uk.ac.ebi.atlas.experimentimport.analyticsindex.baseline;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.baseline.BaselineAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsDocument;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineAnalyticsDocumentStreamTest {

    private static final String EXPERIMENT_ACCESSION = "EXP1";
    private static final ExperimentType EXPERIMENT_TYPE = ExperimentType.RNASEQ_MRNA_BASELINE;
    private static final String HOMO_SAPIENS = "homo sapiens";
    private static final String DEFAULT_QUERY_FACTOR_TYPE = "ORGANISM_PART";

    private static final String GENEID1 = "GENE1";
    private static final String ASSAYGROUPID1 = "G1";
    private static final BaselineAnalytics BASELINE_ANALYTICS1 = new BaselineAnalytics(GENEID1, ASSAYGROUPID1, 1.1);

    private static final String GENEID2 = "GENE2";
    private static final String ASSAYGROUPID2 = "G2";
    private static final BaselineAnalytics BASELINE_ANALYTICS2 = new BaselineAnalytics(GENEID2, ASSAYGROUPID2, 2.2);
    public static final String G1_SEARCH_TERM_1 = "g1_1";

    private static final String UNKNOWN_GENEID = "GENE3";
    private static final BaselineAnalytics BASELINE_ANALYTICS3 = new BaselineAnalytics(UNKNOWN_GENEID, ASSAYGROUPID2, 3.3);

    private static final String G2_SEARCH_TERM_1 = "g2_1";
    private static final String G2_SEARCH_TERM_2 = "g2_2";
    public static final String GENE_1_SEARCHTERM_1 = "gene1_searchterm_1";

    public static final String GENE_2_SEARCHTERM_1 = "gene2_searchterm_1";
    public static final String GENE_2_SEARCHTERM_2 = "gene2_searchterm_2";
    private static final String MUS_MUSCULUS = "mus musculus";

    private static final String ENSEMBL_KINGDOM = "ensembl";

    @Mock
    private IdentifierSearchTermsDao identifierSearchTermsDao;

    @Mock
    private SpeciesKingdomTrader speciesKingdomTraderMock;

    private ImmutableMap<String, String> ensemblSpeciesGroupedByAssayGroupId = ImmutableMap.of(ASSAYGROUPID1, HOMO_SAPIENS, ASSAYGROUPID2, MUS_MUSCULUS);

    @Test
    public void test() {

        when(identifierSearchTermsDao.fetchSearchTerms(GENEID1)).thenReturn(ImmutableSet.of(GENE_1_SEARCHTERM_1));
        when(identifierSearchTermsDao.fetchSearchTerms(GENEID2)).thenReturn(ImmutableSet.of(GENE_2_SEARCHTERM_1, GENE_2_SEARCHTERM_2));
        when(identifierSearchTermsDao.fetchSearchTerms(UNKNOWN_GENEID)).thenReturn(Collections.<String>emptySet());

        when(speciesKingdomTraderMock.getKingdom(HOMO_SAPIENS)).thenReturn(ENSEMBL_KINGDOM);
        when(speciesKingdomTraderMock.getKingdom(MUS_MUSCULUS)).thenReturn(ENSEMBL_KINGDOM);

        ImmutableSetMultimap.Builder<String, String> conditionSearchBuilder = ImmutableSetMultimap.builder();

        conditionSearchBuilder.putAll(ASSAYGROUPID1, G1_SEARCH_TERM_1);
        conditionSearchBuilder.putAll(ASSAYGROUPID2, G2_SEARCH_TERM_1, G2_SEARCH_TERM_2);

        ImmutableSetMultimap<String, String> conditionSearchTermByAssayAccessionId = conditionSearchBuilder.build();

        BaselineAnalyticsDocumentStream stream = new BaselineAnalyticsDocumentStreamFactory(identifierSearchTermsDao, speciesKingdomTraderMock).create(
                EXPERIMENT_ACCESSION, EXPERIMENT_TYPE, ensemblSpeciesGroupedByAssayGroupId, DEFAULT_QUERY_FACTOR_TYPE,
                ImmutableSet.of(BASELINE_ANALYTICS1, BASELINE_ANALYTICS2, BASELINE_ANALYTICS3),
                conditionSearchTermByAssayAccessionId);

        Iterator<AnalyticsDocument> analyticsDocumentIterator = stream.iterator();

        AnalyticsDocument analyticsDocument1 = analyticsDocumentIterator.next();
        AnalyticsDocument analyticsDocument2 = analyticsDocumentIterator.next();
        AnalyticsDocument analyticsDocument3 = analyticsDocumentIterator.next();

        assertThat(analyticsDocumentIterator.hasNext(), is(false));

        assertThat(analyticsDocument1.getBioentityIdentifier(), is(GENEID1));
        assertThat(analyticsDocument1.getSpecies(), is(HOMO_SAPIENS));
        assertThat(analyticsDocument1.getKingdom(), is(ENSEMBL_KINGDOM));
        assertThat(analyticsDocument1.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(analyticsDocument1.getExperimentType(), is(EXPERIMENT_TYPE));
        assertThat(analyticsDocument1.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument1.getIdentifierSearch(), is(GENEID1 + " " + GENE_1_SEARCHTERM_1));
        assertThat(analyticsDocument1.getConditionsSearch(), is(G1_SEARCH_TERM_1));
        assertThat(analyticsDocument1.getAssayGroupId(), is(ASSAYGROUPID1));
        assertThat(analyticsDocument1.getExpressionLevel(), is(1.1));

        assertThat(analyticsDocument2.getBioentityIdentifier(), is(GENEID2));
        assertThat(analyticsDocument2.getSpecies(), is(MUS_MUSCULUS));
        assertThat(analyticsDocument2.getKingdom(), is(ENSEMBL_KINGDOM));
        assertThat(analyticsDocument2.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(analyticsDocument2.getExperimentType(), is(EXPERIMENT_TYPE));
        assertThat(analyticsDocument2.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument2.getIdentifierSearch(), is(GENEID2 + " " + GENE_2_SEARCHTERM_1 + " " + GENE_2_SEARCHTERM_2));
        assertThat(analyticsDocument2.getConditionsSearch(), is(G2_SEARCH_TERM_1 + " " + G2_SEARCH_TERM_2));
        assertThat(analyticsDocument2.getAssayGroupId(), is(ASSAYGROUPID2));
        assertThat(analyticsDocument2.getExpressionLevel(), is(2.2));

        assertThat(analyticsDocument3.getBioentityIdentifier(), is(UNKNOWN_GENEID));
        assertThat(analyticsDocument3.getSpecies(), is(MUS_MUSCULUS));
        assertThat(analyticsDocument3.getKingdom(), is(ENSEMBL_KINGDOM));
        assertThat(analyticsDocument3.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
        assertThat(analyticsDocument3.getExperimentType(), is(EXPERIMENT_TYPE));
        assertThat(analyticsDocument3.getDefaultQueryFactorType(), is(DEFAULT_QUERY_FACTOR_TYPE));
        assertThat(analyticsDocument3.getIdentifierSearch(), is(UNKNOWN_GENEID));
        assertThat(analyticsDocument3.getConditionsSearch(), is(G2_SEARCH_TERM_1 + " " + G2_SEARCH_TERM_2));
        assertThat(analyticsDocument3.getAssayGroupId(), is(ASSAYGROUPID2));
        assertThat(analyticsDocument3.getExpressionLevel(), is(3.3));


    }



}