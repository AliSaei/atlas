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
import uk.ac.ebi.atlas.commands.WriteMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.download.DataWriterFactory;
import uk.ac.ebi.atlas.commands.download.ExpressionsWriter;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@Scope("request")
public class MicroarrayQueryDownloadController {

    private static final Logger LOGGER = Logger.getLogger(MicroarrayQueryDownloadController.class);
    private static final String NORMALIZED_EXPRESSIONS_TSV = "-normalized-expressions.tsv";
    private static final String LOG_FOLD_CHANGES_TSV = "-log-fold-changes.tsv";
    private static final String ANALYTICS_TSV = "-analytics.tsv";
    private static final String PARAMS_TYPE_MICROARRAY = "type=MICROARRAY_ANY";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";
    private static final String QUERY_RESULTS_TSV = "-query-results.tsv";

    private final MicroarrayRequestContextBuilder requestContextBuilder;

    private WriteMicroarrayProfilesCommand writeGeneProfilesCommand;

    private DataWriterFactory dataWriterFactory;

    @Inject
    public MicroarrayQueryDownloadController(
            MicroarrayRequestContextBuilder requestContextBuilder, WriteMicroarrayProfilesCommand writeGeneProfilesCommand, DataWriterFactory dataWriterFactory) {

        this.requestContextBuilder = requestContextBuilder;
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
        this.dataWriterFactory = dataWriterFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        LOGGER.info("<downloadMicroarrayGeneProfiles> received download request for requestPreferences: " + preferences);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), QUERY_RESULTS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            preferences.setArrayDesignAccession(experiment.getArrayDesignAccessions().first());
            requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

            writeMicroarrayGeneProfiles(response.getWriter(), experiment);

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + QUERY_RESULTS_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                //(B) this is very broken. It's overriding the same parameter for every array design
                preferences.setArrayDesignAccession(selectedArrayDesign);
                requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();
                //

                writeMicroarrayGeneProfiles(new PrintWriter(zipOutputStream), experiment);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }

    }

    void writeMicroarrayGeneProfiles(PrintWriter writer, MicroarrayExperiment experiment){
        writeGeneProfilesCommand.setResponseWriter(writer);
        writeGeneProfilesCommand.setExperiment(experiment);

        try {

            long genesCount = writeGeneProfilesCommand.execute(experiment.getAccession());
            LOGGER.info("<writeMicroarrayGeneProfiles> wrote " + genesCount + " genes for experiment " + experiment.getAccession());

        } catch (GenesNotFoundException e) {
            LOGGER.info("<writeMicroarrayGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/normalized.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadNormalizedData(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), NORMALIZED_EXPRESSIONS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                    response.getWriter(), experiment.getArrayDesignAccessions().first());

            long genesCount = writer.write();

            LOGGER.info("<download" + NORMALIZED_EXPRESSIONS_TSV + "> streamed " + genesCount + " gene expression profiles");

            writer.close();

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + NORMALIZED_EXPRESSIONS_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                        new PrintWriter(zipOutputStream),selectedArrayDesign);

                long genesCount = writer.write();

                LOGGER.info("<download" + NORMALIZED_EXPRESSIONS_TSV + "> zipped " + genesCount + " in " + filename);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/logFold.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadLogFoldData(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), LOG_FOLD_CHANGES_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayLogFoldDataWriter(experiment,
                    response.getWriter(), experiment.getArrayDesignAccessions().first());

            long genesCount = writer.write();

            LOGGER.info("<download" + LOG_FOLD_CHANGES_TSV + "> streamed " + genesCount + " gene expression profiles");

            writer.close();

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + LOG_FOLD_CHANGES_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayLogFoldDataWriter(experiment,
                        new PrintWriter(zipOutputStream), selectedArrayDesign);

                long genesCount = writer.write();

                LOGGER.info("<download" + LOG_FOLD_CHANGES_TSV + "> zipped " + genesCount + " in " + filename);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadAllAnalytics(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), ANALYTICS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            ExpressionsWriter writer = dataWriterFactory
                    .getMicroarrayAnalyticsDataWriter(experiment, response.getWriter(), experiment.getArrayDesignAccessions().first());

            long genesCount = writer.write();

            LOGGER.info("<download" + ANALYTICS_TSV + "> streamed " + genesCount + " gene expression profiles");

            writer.close();

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + ANALYTICS_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayAnalyticsDataWriter(experiment,
                        new PrintWriter(zipOutputStream),selectedArrayDesign);

                long genesCount = writer.write();

                LOGGER.info("<download" + ANALYTICS_TSV + "> zipped " + genesCount + " in " + filename);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }

    }

    private void prepareResponse(HttpServletResponse response, String experimentAccession, Set<String> arrayDesignAccessions, String fileExtension) {

        if (arrayDesignAccessions.size() > 1) {

            response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + fileExtension + ".zip\"");
            response.setContentType("application/octet-stream");

        } else {

            String arrayDesignAccession = arrayDesignAccessions.iterator().next();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "_" + arrayDesignAccession + fileExtension + "\"");
            response.setContentType("text/plain; charset=utf-8");

        }
    }

}