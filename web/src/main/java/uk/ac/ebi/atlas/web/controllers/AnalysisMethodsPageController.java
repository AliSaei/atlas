/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web.controllers;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.model.readers.AnalysisMethodsTsvReader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public class AnalysisMethodsPageController {

    private static final String ANALYSIS_METHODS_FILE_SUFFIX = "-analysis-methods.tsv";

    private AnalysisMethodsTsvReader analysisMethodsTsvReader;
    private ExperimentsCache experimentsCache;

    @Inject
    public AnalysisMethodsPageController(AnalysisMethodsTsvReader analysisMethodsTsvReader, ExperimentsCache experimentsCache) {
        this.analysisMethodsTsvReader = analysisMethodsTsvReader;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}-analysis-methods")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model) throws IOException {

        model.addAttribute("csvLines", analysisMethodsTsvReader.readAll(experimentAccession));

        model.addAttribute("experimentAccession", experimentAccession);

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);

        String specie = experiment.getSpecie();

        model.addAttribute("specie", specie);

        model.addAttribute("experimentDescription", experiment.getDescription());

        return "experiment-analysis-methods";
    }


}
















