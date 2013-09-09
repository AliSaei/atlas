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

package uk.ac.ebi.atlas.web.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

/**
 * Overview:
 * this is a proxy router / interceptor controller that makes up for the lack of workflow handling mechanisms in Spring MVC (HandlerInterceptors are a very poor thing).
 * It intercepts requests for any resource or sub-resource related to any experiment
 * and implements the following responsibilities:
 * <p/>
 * experiment lookup / experiment resolver
 * - lookup the experiment across different caches
 * -- if there is no experiment for the given accession then http response is routed to 404 - resource not fount
 * view decorator (to keep delegated controllers DRY)
 * - add the experiment to the HttpServletRequest, for any delegated controller to use it
 * - add model attributes that are required by all experiment related views
 * (i.e. required by layout elements that are shared by all experiment views)
 * proxy router for specialized controllers
 * - resolve experiment type (baseline or differential)
 * - forward to the original request URI,
 * but adding a "type = baseline" | "type=differential" http parameter.
 * This last step enables routing to different delegated controllers depending on the type of the requested experiment.
 * Each controller will have to specify params="type=baseline" or params="type=differential" or params="type" in order
 * to handle baseline experiments, differential experiments, or both.
 * <p/>
 * Note: the original query string - request.getQueryString() - is not re-appended to the forwarded request URI
 * because Spring MVC processes and transforms it into ModelAttribute(s) that will be
 * automatically / transparently available to delegated controller,
 * without any need for controller to access the query string.
 * <p/>
 * If more complex request mappings are required (i.e. url exclusions) this post may help:
 * http://stackoverflow.com/questions/8998419/requestmapping-annotation-in-spring-mvc
 * For a Spring MVC reference:
 * http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/mvc.html
 */

@Controller
public final class ExperimentDispatcher {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";

    private static final String ALL_SPECIES_ATTRIBUTE = "allSpecies";
    private static final String PUBMED_IDS_ATTRIBUTE = "pubMedIds";
    private static final String EXPERIMENT_DESCRIPTION_ATTRIBUTE = "experimentDescription";
    private static final String HAS_EXTRA_INFO_ATTRIBUTE = "hasExtraInfo";
    private static final String EXPERIMENT_TYPE_ATTRIBUTE = "type";

    private ApplicationProperties applicationProperties;

    private SolrQueryService solrQueryService;

    private ExperimentTrader experimentTrader;

    @Inject
    private ExperimentDispatcher(ExperimentTrader experimentTrader,
                                 ApplicationProperties applicationProperties, SolrQueryService solrQueryService) {
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}",
            "/experiments/{experimentAccession}/*"})
    public String dispatch(HttpServletRequest request, @PathVariable String experimentAccession,
                           @RequestParam(value = "accessKey",required = false) String accessKey,
                           Model model) {

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        prepareModel(request, model, experiment);

        return "forward:" + buildForwardURL(request, experiment, accessKey);
    }

    private String buildForwardURL(HttpServletRequest request, Experiment experiment, String accessKey){
        String requestURL = getRequestURL(request);
        requestURL += "?type=" + experiment.getType().getParent();
        if (StringUtils.isNotBlank(accessKey)){
            requestURL += "&accessKey=" + accessKey;
        }
        return requestURL;
    }

    @RequestMapping(value = "/widgets/heatmap/protein")
    public String dispatchWidget(HttpServletRequest request,
                                 @RequestParam(value = "geneQuery", required = true) String bioEntityAccession,
                                 @RequestParam(value = "propertyType", required = false) String propertyType,
                                 @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                 Model model) {

        String species = null;
        try{
            species = solrQueryService.getSpeciesForPropertyValue(bioEntityAccession, propertyType);
        } catch(Exception e){
            model.addAttribute("errorMessage", "Species could not be determined");
            return "widget-error";
        }
        String experimentAccession = applicationProperties.getExperimentAccessionBySpecies(species);

        if (!StringUtils.isEmpty(experimentAccession)) {
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);

            prepareModel(request, model, experiment);

            String requestURL = getRequestURL(request);

            String mappedSpecies = experiment.getRequestSpeciesName(species);

            String organismParameters = StringUtils.isEmpty(mappedSpecies) ? "" : "&serializedFilterFactors=ORGANISM:" + mappedSpecies;

            return "forward:" + requestURL + "?type=" + experiment.getType().getParent() + organismParameters;
        } else {
            model.addAttribute("identifier", bioEntityAccession);
            return "widget-error";
        }

    }

    private String getRequestURL(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();

        return StringUtils.substringAfter(requestURI, contextPath);
    }

    private void prepareModel(HttpServletRequest request, Model model, Experiment experiment) {
        request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);

        Set<String> allSpecies = experiment.getSpecies();

        model.addAttribute(EXPERIMENT_TYPE_ATTRIBUTE, experiment.getType());

        model.addAttribute(ALL_SPECIES_ATTRIBUTE, StringUtils.join(allSpecies, ", "));

        model.addAttribute(EXPERIMENT_DESCRIPTION_ATTRIBUTE, experiment.getDescription());

        model.addAttribute(HAS_EXTRA_INFO_ATTRIBUTE, experiment.hasExtraInfoFile());

        model.addAttribute(PUBMED_IDS_ATTRIBUTE, experiment.getPubMedIds());
    }

}
