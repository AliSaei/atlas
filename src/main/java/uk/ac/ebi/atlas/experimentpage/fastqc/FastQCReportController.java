package uk.ac.ebi.atlas.experimentpage.fastqc;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

@Controller
@Scope("singleton")
public class FastQCReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastQCReportController.class);

    private final FastQCReportUtil fastQCReportUtil;
    private ExperimentTrader experimentTrader;

    @Inject
    public FastQCReportController(FastQCReportUtil fastQCReportUtil, ExperimentTrader experimentTrader) {
        this.fastQCReportUtil = fastQCReportUtil;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/fastqc/{species}/{resource:.*}",
                    method = RequestMethod.GET)
    public String getFastQCPage(HttpServletRequest request, Model model,
                                @PathVariable String experimentAccession,
                                @PathVariable String species,
                                @PathVariable String resource,
                                @RequestParam(value = "accessKey",required = false) String accessKey,
                                @ModelAttribute("preferences") @Valid FastQCReportRequestPreferences preferences, RedirectAttributes ra) throws IOException {

        if(!resource.equals("qc.html")) {
            // NB: resources do not need access key
            // otherwise we would have to add the access key to the query string for every resource in the page
            return forwardToQcResource(experimentAccession, species, resource);
        }

        // will generate 404 here for private experiments without access key
        prepareModel(model, preferences, experimentAccession, accessKey);

        String reportSelected = preferences.getSelectedReport();

        if (StringUtils.isBlank(reportSelected)) {
            reportSelected = FastQCReportRequestPreferences.FastQCReportType.QC.toString();
        }

        if(reportSelected != null) {
            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            if(reportSelected.equals("MAPPING")){
                String path = MessageFormat.format("/experiments/{0}/fastqc/{1}/mapping/{2}", experimentAccession, species, "tophat2.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setSelectedReport(reportSelected);

        //Specie selection
        String speciesSelected = preferences.getSelectedSpecies();
        if(StringUtils.isBlank(speciesSelected)) {
            speciesSelected = species;
        }

        if(speciesSelected != null) {
            if(!speciesSelected.equals(species)){
                String parsedSelectedSpecie = splitSpecies(speciesSelected).toLowerCase();
                String path = MessageFormat.format("/experiments/{0}/fastqc/{1}/{2}", experimentAccession, parsedSelectedSpecie, "qc.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }
        preferences.setSelectedSpecies(speciesSelected);

        if(!fastQCReportUtil.hasFastQC(experimentAccession, species)) {
            throw new ResourceNotFoundException("No fast qc report for " + experimentAccession);
        }

        String path = fastQCReportUtil.buildFastQCIndexHtmlPath(experimentAccession, species);
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));

        model.addAllAttributes(new DownloadURLBuilder(experimentAccession).dataDownloadUrls(request.getRequestURI()));

        return "fast-qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String species, String resource) throws IOException {
        String species_s = splitSpecies(species).toLowerCase();
        String path = MessageFormat.format("/expdata/{0}/qc/{1}/{2}/", experimentAccession, species_s, resource);

        return "forward:" + path;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/fastqc/{species}/mapping/{resource:.*}",
            method = RequestMethod.GET)
    public String getFastMappingQCPage(HttpServletRequest request, Model model,
                                @PathVariable String experimentAccession,
                                @PathVariable String resource,
                                @PathVariable String species,
                                @RequestParam(value = "accessKey",required = false) String accessKey,
                                @ModelAttribute("preferences") @Valid FastQCReportRequestPreferences preferences, RedirectAttributes ra) throws IOException {

        if(!resource.equals("tophat2.html")) {
            // NB: resources do not need access key
            // otherwise we would have to add the access key to the query string for every resource in the page
            return forwardToMappingQcResource(experimentAccession, species, resource);
        }

        prepareModel(model, preferences, experimentAccession, accessKey);

        String reportSelected = preferences.getSelectedReport();

        if (StringUtils.isBlank(reportSelected)) {
            reportSelected = FastQCReportRequestPreferences.FastQCReportType.MAPPING.toString();
        }

        if(reportSelected != null) {
            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            if(reportSelected.equals("QC")){
                String path = MessageFormat.format("/experiments/{0}/fastqc/{1}/{2}", experimentAccession, species, "qc.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setSelectedReport(reportSelected);

        //Specie selection
        String speciesSelected = preferences.getSelectedSpecies();
        if(StringUtils.isBlank(speciesSelected)) {
            speciesSelected = species;
        }

        if(speciesSelected != null) {
            if(!speciesSelected.equals(species)){
                String parsedSelectedSpecie = splitSpecies(speciesSelected).toLowerCase();
                String path = MessageFormat.format("/experiments/{0}/fastqc/{1}/mapping/{2}", experimentAccession, parsedSelectedSpecie, "tophat2.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }
        preferences.setSelectedSpecies(speciesSelected);

        if(!fastQCReportUtil.hasMappingQC(experimentAccession, species)) {
            throw new ResourceNotFoundException("No fast qc report for " + experimentAccession);
        }

        String path = fastQCReportUtil.buildMappingQCIndexHtmlPath(experimentAccession, species);
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));

        return "fast-qc-template";

    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToMappingQcResource(String experimentAccession, String species, String resource) throws IOException {
        String species_s = splitSpecies(species).toLowerCase();
        String path = MessageFormat.format("/expdata/{0}/qc/{1}/mapping/{2}", experimentAccession, species_s, resource);

        return "forward:" + path;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/fastqc/{species}/riq/**",
                    method = RequestMethod.GET)
    public String getFastQCReportPage(HttpServletRequest request, Model model, @PathVariable String experimentAccession,
                                      @PathVariable String species,
                                      @RequestParam(value = "accessKey",required = false) String accessKey,
                                      @ModelAttribute("preferences") @Valid FastQCReportRequestPreferences preferences) throws IOException {

        String reportSelected = preferences.getSelectedReport();

        if (StringUtils.isBlank(reportSelected)) {
            reportSelected = FastQCReportRequestPreferences.FastQCReportType.QC.toString();
        }

        if(reportSelected != null) {
            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            if(reportSelected.equals("MAPPING")){
                String path = MessageFormat.format("/experiments/{0}/fastqc/{1}/mapping/{2}", experimentAccession, species, "tophat2.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setSelectedReport(reportSelected);

        //Specie selection
        String speciesSelected = preferences.getSelectedSpecies();
        if(StringUtils.isBlank(speciesSelected)) {
            speciesSelected = species;
        }

        if(speciesSelected != null) {
            if(!speciesSelected.equals(species)){
                String parsedSelectedSpecie = splitSpecies(speciesSelected).toLowerCase();
                String path = MessageFormat.format("/experiments/{0}/fastqc/{1}/{2}", experimentAccession, parsedSelectedSpecie, "qc.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }
        preferences.setSelectedSpecies(speciesSelected);

        String beginPath = fastQCReportUtil.buildFastQCReportIndexHtmlPath(experimentAccession, species);

        String endPath = extractPath(request.getServletPath());

        //We need to check if the resource is an image or icon and handle it to the correspondent path
        String fileName = endPath.substring(endPath.lastIndexOf("/") + 1);
        if(!fileName.equals("fastqc_report.html")){
            return forwardToFastQCReportMappingResources(experimentAccession, species, endPath);
        }

        String fullPath = beginPath + "/" + endPath;
        Path path = FileSystems.getDefault().getPath(fullPath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException(fullPath + " does not exist");
        }

        //NB: by looking up the experiment at the end, we improve performance the other resources (eg: images)
        // that don't need the experiment, however we made it possible for those resources from private experiments
        // to become accessible publically - an acceptable tradeoff for now.
        prepareModel(model, preferences, experimentAccession, accessKey);

        request.setAttribute("contentPath", path);

        return "fast-qc-template";
    }

    private String extractPath(String path) {
        return path.substring(path.indexOf("riq/") + 4);
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToFastQCReportMappingResources(String experimentAccession, String species, String resource) throws IOException {
        String species_s = splitSpecies(species).toLowerCase();
        String path = MessageFormat.format("/expdata/{0}/qc/{1}/riq/{2}", experimentAccession, species_s, resource);

        return "forward:" + path;
    }

    private void prepareModel(Model model, FastQCReportRequestPreferences preferences, String experimentAccession, String accessKey) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        model.addAllAttributes(experiment.getAttributes());
        model.addAttribute("fastQCReports", preferences.fastQCReportsList());
    }

    private String splitSpecies(String species) {
        String[] splitStr = species.split("\\s+");

        return splitStr.length > 1 ? splitStr[0] + "_" + splitStr[1] : species;
    }


    @ExceptionHandler(value = FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleFileNotFound(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ModelAndView("error-page");
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllOtherExceptions(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ModelAndView("error-page");
    }

}