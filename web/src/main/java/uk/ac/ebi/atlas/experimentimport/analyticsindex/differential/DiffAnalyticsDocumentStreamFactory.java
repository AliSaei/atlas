package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.DifferentialAnalytics;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsDao;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

@Named
public class DiffAnalyticsDocumentStreamFactory {

    private final IdentifierSearchTermsDao identifierSearchTermsDao;
    private final SpeciesKingdomTrader speciesKingdomTrader;

    @Inject
    public DiffAnalyticsDocumentStreamFactory(IdentifierSearchTermsDao identifierSearchTermsDao, SpeciesKingdomTrader speciesKingdomTrader) {
        this.identifierSearchTermsDao = identifierSearchTermsDao;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }

    public DiffAnalyticsDocumentStream create(String experimentAccession,
                                          ExperimentType experimentType,
                                          Set<String> factors,
                                          Map<String, String> ensemblSpeciesGroupedByAssayGroupId,
                                          Iterable<? extends DifferentialAnalytics> inputStream,
                                          SetMultimap<String, String> conditionSearchTermsByContrastId,
                                          Map<String, Integer> numReplicatesByContrastId) {
        return new DiffAnalyticsDocumentStream(experimentAccession, experimentType, factors, ensemblSpeciesGroupedByAssayGroupId,
                inputStream, conditionSearchTermsByContrastId, numReplicatesByContrastId, identifierSearchTermsDao, speciesKingdomTrader);
    }

}
