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

package uk.ac.ebi.atlas.web.controllers.rest.experimentquery;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.BaselineProfilesWriteCommand;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.BaselineExperimentController;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Scope("request")
public class BaselineExperimentDownloadController extends BaselineExperimentController {
    private static final Logger LOGGER = Logger.getLogger(BaselineExperimentDownloadController.class);

    private BaselineProfilesWriteCommand baselineProfilesWriteCommand;

    private BaselineRequestContext requestContext;

    @Inject
    public BaselineExperimentDownloadController(BaselineRequestContextBuilder requestContextBuilder,
                                                FilterFactorsConverter filterFactorsConverter,
                                                BaselineProfilesWriteCommand baselineProfilesWriteCommand) {

        super(requestContextBuilder, filterFactorsConverter);
        this.baselineProfilesWriteCommand = baselineProfilesWriteCommand;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = "type=RNASEQ_MRNA_BASELINE")
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        initPreferences(preferences, experiment);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: " + preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        requestContext = initRequestContext(experiment, preferences);

        baselineProfilesWriteCommand.setResponseWriter(response.getWriter());

        try {
            long genesCount = baselineProfilesWriteCommand.write(requestContext);

            LOGGER.info("<downloadGeneProfiles> streamed " + genesCount + "gene expression profiles");

        } catch (GenesNotFoundException e) {
            LOGGER.info("<downloadGeneProfiles> no genes found");
        }


    }

}