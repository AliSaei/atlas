package uk.ac.ebi.atlas.experimentimport.analyticsindex.differential;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialCondition;
import uk.ac.ebi.atlas.solr.admin.index.conditions.differential.DifferentialConditionsBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;

@Named
public class MicroArrayDiffAnalyticsIndexerService {

    private final EFOLookupService efoParentsLookupService;
    private final DifferentialConditionsBuilder diffConditionsBuilder;
    private final MicroArrayDiffAnalyticsDocumentStreamIndexer microArrayDiffAnalyticsDocumentStreamIndexer;

    @Inject
    public MicroArrayDiffAnalyticsIndexerService(EFOLookupService efoParentsLookupService, DifferentialConditionsBuilder diffConditionsBuilder, MicroArrayDiffAnalyticsDocumentStreamIndexer microArrayDiffAnalyticsDocumentStreamIndexer) {
        this.efoParentsLookupService = efoParentsLookupService;
        this.diffConditionsBuilder = diffConditionsBuilder;
        this.microArrayDiffAnalyticsDocumentStreamIndexer = microArrayDiffAnalyticsDocumentStreamIndexer;
    }

    public int index(MicroarrayExperiment experiment, Map<String, String> bioentityIdToIdentifierSearch, int batchSize) {


        ImmutableSetMultimap<String, String> ontologyTermIdsByAssayAccession = efoParentsLookupService
                .expandOntologyTerms(experiment.getExperimentDesign().getAllOntologyTermIdsByAssayAccession());

        ImmutableSetMultimap<String, String> conditionSearchTermsByContrastId = buildConditionSearchTermsByAssayGroupId(experiment, ontologyTermIdsByAssayAccession);


        Map<String, Integer> numReplicatesByContrastId = buildNumReplicatesByContrastId(experiment);

        return  microArrayDiffAnalyticsDocumentStreamIndexer.index(experiment, conditionSearchTermsByContrastId, numReplicatesByContrastId, bioentityIdToIdentifierSearch, batchSize);
    }

    private Map<String, Integer> buildNumReplicatesByContrastId(DifferentialExperiment experiment) {
        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();

        for (Contrast contrast : experiment.getContrasts()) {
            int numReplicates = Math.min(contrast.getReferenceAssayGroup().getReplicates(), contrast.getTestAssayGroup().getReplicates());
            builder.put(contrast.getId(), numReplicates);
        }

        return builder.build();
    }

    ImmutableSetMultimap<String, String> buildConditionSearchTermsByAssayGroupId(DifferentialExperiment experiment, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {

        Collection<DifferentialCondition> conditions = diffConditionsBuilder.buildProperties(experiment, ontologyTermIdsByAssayAccession);

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();

        for (DifferentialCondition condition : conditions) {
            builder.putAll(condition.getContrastId(), condition.getValues());
        }

        return builder.build();

    }

}