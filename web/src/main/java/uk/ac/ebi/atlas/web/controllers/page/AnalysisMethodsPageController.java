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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@Scope("request")
public class AnalysisMethodsPageController {

    private TsvReaderBuilder tsvReaderBuilder;

    private DownloadURLBuilder downloadURLBuilder;
    private static final String QC_ARRAY_DESIGNS_ATTRIBUTE = "qcArrayDesigns";
    private ExperimentTrader experimentTrader;

    @Inject
    public AnalysisMethodsPageController(TsvReaderBuilder tsvReaderBuilder, DownloadURLBuilder downloadURLBuilder, ExperimentTrader experimentTrader,
                                         @Value("#{configuration['experiment.analysis-method.path.template']}")
                                         String pathTemplate) {

        this.tsvReaderBuilder = tsvReaderBuilder.forTsvFilePathTemplate(pathTemplate);
        this.downloadURLBuilder = downloadURLBuilder;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/analysis-methods", params = "type")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model, HttpServletRequest request) throws IOException {

        TsvReader tsvReader = tsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        model.addAttribute("csvLines", tsvReader.readAll());

        model.addAttribute("experimentAccession", experimentAccession);

        downloadURLBuilder.addDataDownloadUrlsToModel(model, request);

        //For showing the QC REPORTS button in the header
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(experimentAccession);
        request.setAttribute(QC_ARRAY_DESIGNS_ATTRIBUTE, experiment.getArrayDesignAccessions());

        return "experiment-analysis-methods";
    }

}
















