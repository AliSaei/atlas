/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.page.experimentquery;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.*;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfilesHeatMap;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;
import uk.ac.ebi.atlas.web.controllers.page.validators.DifferentialRequestPreferencesValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public abstract class DifferentialExperimentPageController<T extends DifferentialExperiment, K extends DifferentialRequestPreferences, Z extends DifferentialProfile> {

    private DownloadURLBuilder downloadURLBuilder;
    private DifferentialRequestContextBuilder differentialRequestContextBuilder;
    private DifferentialProfilesHeatMap<Z, DifferentialRequestContext> profilesHeatMap;


    @SuppressWarnings("unchecked")
    protected DifferentialExperimentPageController(DifferentialRequestContextBuilder differentialRequestContextBuilder,
                                                   DifferentialProfilesHeatMap<Z, ? extends DifferentialRequestContext> profilesHeatMap,
                                                   DownloadURLBuilder downloadURLBuilder) {
        this.differentialRequestContextBuilder = differentialRequestContextBuilder;
        // cast here to avoid having to make a type parameter for DifferentialRequestContext
        this.profilesHeatMap = (DifferentialProfilesHeatMap<Z, DifferentialRequestContext>) profilesHeatMap;
        this.downloadURLBuilder = downloadURLBuilder;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }

    // called from sub classes
    public String showGeneProfiles(K requestPreferences, BindingResult result, Model model, HttpServletRequest request) {

        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initRequestPreferences(model, requestPreferences, experiment);

        DifferentialRequestContext requestContext = initRequestContext(experiment, requestPreferences);

        Set<Contrast> contrasts = experiment.getContrasts();

        model.addAttribute("allQueryFactors", contrasts);

        //required by autocomplete
        model.addAttribute("species", requestContext.getFilteredBySpecies());

        model.addAttribute("queryFactorName", "Contrast");

        model.addAttribute("allQueryFactors", contrasts);

        model.addAttribute("regulationValues", Regulation.values());

        if (!result.hasErrors()) {

            try {
                DifferentialProfilesList differentialProfiles = profilesHeatMap.fetch(requestContext);

                model.addAttribute("geneProfiles", differentialProfiles);

                downloadURLBuilder.addDataDownloadUrlsToModel(model, request);

            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + requestPreferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

    private void initRequestPreferences(Model model, K requestPreferences, T experiment) {
        //if there is only one contrast we want to preselect it... from Robert feedback
        if (experiment.getContrasts().size() == 1) {
            requestPreferences.setQueryFactorValues(experiment.getContrastIds());
        }
        initExtraPageConfigurations(model, requestPreferences, experiment);

    }

    protected abstract void initExtraPageConfigurations(Model model, K requestPreferences, T experiment);

    private DifferentialRequestContext initRequestContext(T experiment, DifferentialRequestPreferences requestPreferences) {
        return differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences).build();

    }

}