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

package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.RankRnaSeqProfilesCommand;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.SortedSet;

@Controller
@Scope("request")
public class DifferentialQueryPageController {

    private RnaSeqRequestContextBuilder differentialRequestContextBuilder;
    private RankRnaSeqProfilesCommand command;

    @Inject
    public DifferentialQueryPageController(RnaSeqRequestContextBuilder differentialRequestContextBuilder,
                                           RankRnaSeqProfilesCommand rankDifferentialProfilesCommand){
        this.differentialRequestContextBuilder = differentialRequestContextBuilder;
        this.command = rankDifferentialProfilesCommand;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}", params={"type=DIFFERENTIAL"})
    public String showDifferentialExperimentGeneProfiles(@ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        DifferentialExperiment experiment = (DifferentialExperiment)request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        RequestContext requestContext = differentialRequestContextBuilder.forExperiment(experiment)
                .withPreferences(preferences).build();

        SortedSet<Contrast> contrasts = experiment.getContrasts();

        model.addAttribute("allQueryFactors", contrasts);

//      if there is only one contrast we want to preselect it... from Robert feedback
        if(contrasts.size() == 1){
            preferences.setQueryFactorValues(experiment.getContrastIds());
        }

        //required by autocomplete
        model.addAttribute("species", requestContext.getFilteredBySpecies());

        model.addAttribute("queryFactorName", "Contrast");

        model.addAttribute("allQueryFactors", contrasts);
        model.addAttribute("regulationValues", Regulation.values());

        if (!result.hasErrors()) {

            try {
                GeneProfilesList<DifferentialProfile> differentialProfiles = command.execute(experiment.getAccession());

                model.addAttribute("geneProfiles", differentialProfiles);

                model.addAttribute("downloadUrl", ExperimentDispatcher.buildDownloadURL(request));


            } catch (GenesNotFoundException e) {
                result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
            }

        }

        return "experiment";
    }

}