package uk.ac.ebi.atlas.species;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrQueryService;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Named
public class SpeciesInferrer {

    private SolrQueryService bioentitiesSearchService;
    private AnalyticsSearchService analyticsSearchService;
    private SpeciesFactory speciesFactory;

    @Inject
    public SpeciesInferrer(SolrQueryService bioentitiesSearchService,
                           AnalyticsSearchService analyticsSearchService,
                           SpeciesFactory speciesFactory) {
        this.bioentitiesSearchService = bioentitiesSearchService;
        this.analyticsSearchService = analyticsSearchService;
        this.speciesFactory = speciesFactory;
    }

    public Species inferSpecies(@Nonnull SemanticQuery geneQuery,
                                @Nonnull SemanticQuery conditionQuery,
                                @Nonnull String speciesString) {
        if (isBlank(speciesString)) {
            return inferSpecies(geneQuery, conditionQuery);
        }

        return speciesFactory.create(speciesString);
    }

    public Species inferSpeciesForGeneQuery(@Nonnull SemanticQuery geneQuery) {
        return inferSpecies(geneQuery, SemanticQuery.create());
    }

    public Species inferSpeciesForGeneQuery(@Nonnull SemanticQuery geneQuery, @Nonnull String speciesString) {
        return inferSpecies(geneQuery, SemanticQuery.create(), speciesString);
    }

    private Species inferSpecies(SemanticQuery geneQuery, SemanticQuery conditionQuery) {
        if (geneQuery == null || geneQuery.isEmpty() && conditionQuery.isEmpty()) {
            return speciesFactory.createUnknownSpecies();
        }

        ImmutableSet.Builder<String> speciesCandidatesBuilder = ImmutableSet.builder();
        speciesCandidatesBuilder.addAll(analyticsSearchService.findSpecies(geneQuery, conditionQuery));

        if (conditionQuery.size() == 0 && speciesCandidatesBuilder.build().size() == 0) {
            for (SemanticQueryTerm geneQueryTerm : geneQuery) {
                speciesCandidatesBuilder.addAll(
                        bioentitiesSearchService.fetchSpecies(geneQueryTerm).stream()
                                .map(speciesCandidate -> speciesFactory.create(speciesCandidate).getReferenceName())
                                .collect(Collectors.toSet()));
            }
        }

        ImmutableSet<String> speciesCandidates = speciesCandidatesBuilder.build();

        return speciesCandidates.size() == 1 ?
                speciesFactory.create(speciesCandidates.iterator().next()) :
                speciesFactory.createUnknownSpecies();
    }

}
