package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExpression;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class BaselineAnalyticsFacetsReader {

    private static final String EXPERIMENTS_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[?(@.val=='%s')].defaultQueryFactorType.buckets[?(@.val=='%s')].experimentAccession.buckets[*]";
    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder;

    private static final String FACET_TREE_PATH = "$.facets.experimentType.buckets[?(@.val=='rnaseq_mrna_baseline')].species.buckets[*]";

    @Inject
    public BaselineAnalyticsFacetsReader(BaselineExpressionLevelRounder baselineExpressionLevelRounder) {
        this.baselineExpressionLevelRounder = baselineExpressionLevelRounder;
    }

    public ImmutableList<BaselineExpression> extractAverageExpressionLevel(String json, String species, String defaultQueryFactorType) {
        String experimentsPath = String.format(EXPERIMENTS_PATH, species, defaultQueryFactorType);

        List<Map<String, Object>> results = JsonPath.read(json, experimentsPath);

        ImmutableList.Builder<BaselineExpression> builder = ImmutableList.builder();

        for (Map<String, Object> experiment : results) {
            String experimentAccession = (String) experiment.get("val");
            int numberOfGenesExpressedAcrossAllAssayGroups = (int) experiment.get("uniqueIdentifiers");

            Map<String, Object> assayGroupIdRoot = (Map<String, Object>) experiment.get("assayGroupId");
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) assayGroupIdRoot.get("buckets");

            for(Map<String, Object> assayGroup : buckets)  {
                String assayGroupId = (String) assayGroup.get("val");
                double sumExpressionLevel = (double) assayGroup.get("sumExpressionLevel");

                double expression = baselineExpressionLevelRounder.round(sumExpressionLevel / numberOfGenesExpressedAcrossAllAssayGroups);
                BaselineExpression bslnExpression = BaselineExpression.create(experimentAccession, assayGroupId, expression);

                builder.add(bslnExpression);
            }
        }

        return builder.build();
    }

    public static String generateFacetsTreeJson(String json) {

        List<Map<String, Object>> results = JsonPath.read(json, FACET_TREE_PATH);

        Map<String,List<FacetTree>> facetTree = Maps.newLinkedHashMap();

        for (Map<String, Object> experiment : results) {
            String species = (String) experiment.get("val");

            Map<String, Object> factorRoot = (Map<String, Object>) experiment.get("defaultQueryFactorType");
            List<Map<String, Object>> buckets = (List<Map<String, Object>>) factorRoot.get("buckets");

            List<FacetTree> facetTreeList = Lists.newArrayList();
            for(Map<String, Object> defaultQueryFactorType : buckets)  {
                String organism = (String) defaultQueryFactorType.get("val");
                String source = Factor.convertToLowerCase(organism);

                FacetTree factor = new FacetTree(organism, source);

                facetTreeList.add(factor);
            }

            facetTree.put(Factor.convertToLowerCase(species), facetTreeList);

        }

        Gson gson = new Gson();

        return gson.toJson(facetTree);
    }

    private static class FacetTree {
        String name;
        String value;

        public FacetTree(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

}
