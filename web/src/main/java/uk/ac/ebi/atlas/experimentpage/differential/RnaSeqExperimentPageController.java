
package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageCallbacks;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.viewmodel.DifferentialProfilesViewModelBuilder;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class RnaSeqExperimentPageController extends DifferentialExperimentPageController<DifferentialExperiment, DifferentialRequestPreferences, RnaSeqProfile> {

    private ExperimentPageCallbacks experimentPageCallbacks = new ExperimentPageCallbacks();

    private ExperimentTrader experimentTrader;

    @Inject
    @Required
    public void setExperimentTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @Inject
    public RnaSeqExperimentPageController(RnaSeqRequestContextBuilder rnaSeqRequestContextBuilder,
                                          RnaSeqProfilesHeatMap profilesHeatMap,
                                          DifferentialProfilesViewModelBuilder differentialProfilesViewModelBuilder,
                                          SpeciesKingdomTrader speciesKingdomTrader,
                                          TracksUtil tracksUtil,
                                          GseaPlotsBuilder gseaPlotsBuilder,
                                          ApplicationProperties applicationProperties) {
        super(rnaSeqRequestContextBuilder, profilesHeatMap, differentialProfilesViewModelBuilder,
                speciesKingdomTrader, tracksUtil, gseaPlotsBuilder,applicationProperties);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
                                   @RequestParam Map<String, String> allParameters,
                                   @RequestParam(required = false) String accessKey,
                                   @PathVariable String experimentAccession, Model model, HttpServletRequest request) {
        model.addAttribute("sourceURL", experimentPageCallbacks.create(preferences, allParameters, request.getRequestURI()));

        super.prepareRequestPreferencesAndHeaderData(
                (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences, model,request
        );

        return "experiment";
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showGeneProfilesData(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
                                       @PathVariable String experimentAccession,
                                       @RequestParam(required = false) String accessKey,
                                       BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        experimentPageCallbacks.adjustReceivedObjects(preferences);

        super.populateModelWithHeatmapData(
                (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences, result, model, request
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return "heatmap-data";
    }

}