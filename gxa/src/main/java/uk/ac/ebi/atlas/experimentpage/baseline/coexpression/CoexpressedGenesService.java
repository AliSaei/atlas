package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.solr.bioentities.query.GeneQueryResponse;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Named
public class CoexpressedGenesService {

    private final CoexpressedGenesDao coexpressedGenesDao;

    @Inject
    public CoexpressedGenesService(CoexpressedGenesDao coexpressedGenesDao) {
        this.coexpressedGenesDao = coexpressedGenesDao;
    }

    Pair<GeneQueryResponse, List<String>> extendGeneQueryResponseWithCoexpressions(GeneQueryResponse originalResponse,
                                                                                   BaselineExperiment experiment,
                                                                                   Map<String,Integer> coexpressionsRequested,
                                                                                   boolean alsoIncludeOriginalGeneQueryResponse) {
        GeneQueryResponse newGeneQuery = new GeneQueryResponse();
        List<String> prescribedOrder = new ArrayList<>();
        for (String queryTerm: originalResponse.getQueryTerms()) {
            Collection<String> idsForThatTerm = originalResponse.getIds(queryTerm);
            Set<String> idsToAdd = new HashSet<>();
            for (String id : idsForThatTerm) {
                if( alsoIncludeOriginalGeneQueryResponse) {
                    prescribedOrder.add(id);
                }
                if (coexpressionsRequested.containsKey(id.toUpperCase())) {
                    List<String> coexpressionsForThisId = fetchCoexpressions(experiment.getAccession(), id, coexpressionsRequested.get(id.toUpperCase()));
                    idsToAdd.addAll(coexpressionsForThisId);
                    prescribedOrder.addAll(coexpressionsForThisId);
                }
            }
            if (alsoIncludeOriginalGeneQueryResponse) {
                newGeneQuery.addGeneIds(queryTerm, new HashSet<>(idsForThatTerm));
            }
            if (idsToAdd.size() > 0) {
                newGeneQuery.addGeneIds(queryTerm + ":coexpressions", idsToAdd);
            }
        }

        return Pair.of(newGeneQuery,prescribedOrder);
    }

    public List<String> fetchCoexpressions(String experimentAccession, String identifier, int requestedAmount) {
        List<String> coexpressedGenes = coexpressedGenesDao.coexpressedGenesFor(experimentAccession, identifier);
        return coexpressedGenes.subList(0, Math.min(Math.max(0, requestedAmount), coexpressedGenes.size()));
    }

    public Optional<Pair<GeneQueryResponse, List<String>>> tryGetRelatedCoexpressions(BaselineExperiment experiment,
                                                                                      GeneQueryResponse geneQueryResponse,
                                                                                      Map<String, Integer> coexpressionsRequested) {
        Pair<GeneQueryResponse, List<String>> result =
                extendGeneQueryResponseWithCoexpressions(geneQueryResponse, experiment, coexpressionsRequested, false);
        return result.getLeft().getAllGeneIds().isEmpty()
                ? Optional.empty()
                : Optional.of(result);
    }
}
