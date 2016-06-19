package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentExpression;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BaselineAnalyticsFacetsReaderTest {

    private BaselineAnalyticsFacetsReader subject = new BaselineAnalyticsFacetsReader();

    private final static String RESPONSE_2_JSON_FACETS =
            "{\n" +
            "  \"Homo sapiens\": [\n" +
            "    {\n" +
            "      \"name\": \"ORGANISM_PART\",\n" +
            "      \"value\": \"Organism part\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"CELL_LINE\",\n" +
            "      \"value\": \"Cell line\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Mus musculus\": [\n" +
            "    {\n" +
            "      \"name\": \"ORGANISM_PART\",\n" +
            "      \"value\": \"Organism part\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"CELL_LINE\",\n" +
            "      \"value\": \"Cell line\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void extractAverageExpressionLevel() throws IOException{
        ImmutableList<BaselineExperimentExpression> expressions = subject.extractAverageExpressionLevel(loadJsonWithExperiments());

        BaselineExperimentExpression expression1 = BaselineExperimentExpression.create("E-MTAB-1733", "g22", BaselineExpressionLevelRounder.round(467512.3999999801 / 25247));
        BaselineExperimentExpression expression2 = BaselineExperimentExpression.create("E-MTAB-1733", "g15", BaselineExpressionLevelRounder.round(486396.0999999961 / 25247));
        BaselineExperimentExpression expression3 = BaselineExperimentExpression.create("E-MTAB-513", "g13", BaselineExpressionLevelRounder.round(4510.1 / 352));

        assertThat(expressions, contains(expression1, expression2, expression3));
    }

    static List<Map<String, Object>> loadJsonWithExperiments() throws IOException {
        String species = "Homo sapiens";
        String defaultQueryFactorType = "ORGANISM_PART";
        InputStream in = BaselineAnalyticsFacetsReaderTest.class.getResourceAsStream("/test/baseline.heatmap.pivot.response.json");

        return JsonPath.read(in, String.format(BaselineAnalyticsSearchDao.EXPERIMENTS_PATH, species,
                defaultQueryFactorType));
    }

    static List<Map<String, Object>> loadJsonWithFacets() throws IOException {
        InputStream in = BaselineAnalyticsFacetsReaderTest.class.getResourceAsStream("/test/baseline.heatmap.pivot.response2.json");
        return JsonPath.read(in, BaselineAnalyticsSearchDao.FACET_TREE_PATH);
    }

    @Test
    public void extractTreeFacets() throws IOException {
        String facetsTreeJson = subject.generateFacetsTreeJson(loadJsonWithFacets());

        assertThat(facetsTreeJson, is(RESPONSE_2_JSON_FACETS));
    }

}