package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.common.collect.Lists;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.*;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.FoldChangeRounder;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ColourGradient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
public class DifferentialResultsReader {

    private final ExperimentTrader experimentTrader;
    private final ContrastTrader contrastTrader;

    private static final String DOCS_PATH                  = "$.response.docs[*]";
    private static final String EXPERIMENT_TYPE_FIELD      = "experimentType";
    private static final String EXPERIMENT_ACCESSION_FIELD = "experimentAccession";
    private static final String CONTRAST_ID_FIELD          = "contrastId";
    private static final String LOG2_FOLD_CHANGE_FIELD     = "foldChange";

    private static final ParseContext parser = JsonPath.using(Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST));

    private final ColourGradient colourGradient;

    @Inject
    public DifferentialResultsReader(ExperimentTrader experimentTrader, ContrastTrader contrastTrader, ColourGradient colourGradient) {
        this.experimentTrader = experimentTrader;
        this.contrastTrader = contrastTrader;
        this.colourGradient = colourGradient;
    }


    public String extractResultsAsJson(String solrResponseAsJson) {
        Map<String, Object> resultsWithLevels = new HashMap<>(5);

        double minUpLevel = Double.POSITIVE_INFINITY;
        double maxUpLevel = 0.0;
        double minDownLevel = Double.NEGATIVE_INFINITY;
        double maxDownLevel = 0.0;

        List<Map<String, Object>> filteredDocuments = Lists.newArrayList();
        TreeMultimap<String, String> experimentContrastMap = TreeMultimap.create();

        List<Map<String, Object>> documents = parser.parse(solrResponseAsJson).read(DOCS_PATH);

        if(!documents.isEmpty()) {
            for (Map<String, Object> document : documents) {
                String experimentAccession = (String) document.get(EXPERIMENT_ACCESSION_FIELD);
                String contrastId = (String) document.get(CONTRAST_ID_FIELD);
                if (experimentContrastMap.put(experimentAccession, contrastId)) {
                    ExperimentType experimentType = ExperimentType.get((String) document.get(EXPERIMENT_TYPE_FIELD));

                    Object foldChangeSymbol = document.get(LOG2_FOLD_CHANGE_FIELD);
                    double foldChange = foldChangeSymbol instanceof Double ? (double) foldChangeSymbol : Double.parseDouble((String) foldChangeSymbol);

                    document.put("foldChange", foldChange);
                    document.put("comparison", contrastTrader.getContrastFromCache(experimentAccession, experimentType, contrastId).getDisplayName());
                    document.put("experimentName", experimentTrader.getExperimentFromCache(experimentAccession, experimentType).getDescription());

                    if (foldChange > 0.0) {
                        minUpLevel = Math.min(minUpLevel, foldChange);
                        maxUpLevel = Math.max(maxUpLevel, foldChange);
                    } else {
                        minDownLevel = Math.max(minDownLevel, foldChange);
                        maxDownLevel = Math.min(maxDownLevel, foldChange);
                    }

                    filteredDocuments.add(document);
                }
            }

            for (Map<String, Object> document : filteredDocuments) {
                double foldChange = (Double) document.get("foldChange");
                String colour = foldChange > 0.0 ? colourGradient.getGradientColour(foldChange, minUpLevel, maxUpLevel, "pink", "red") : colourGradient.getGradientColour(foldChange, minDownLevel, maxDownLevel, "lightGray", "blue");
                document.put("colour", colour);
                document.put("foldChange", FoldChangeRounder.round(foldChange));
            }

            resultsWithLevels.put("maxDownLevel", FoldChangeRounder.round(maxDownLevel));
            resultsWithLevels.put("minDownLevel", FoldChangeRounder.round(minDownLevel));
            resultsWithLevels.put("minUpLevel", FoldChangeRounder.round(minUpLevel));
            resultsWithLevels.put("maxUpLevel", FoldChangeRounder.round(maxUpLevel));
        }

        resultsWithLevels.put("results", filteredDocuments);

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
        return gson.toJson(resultsWithLevels);
    }
}
