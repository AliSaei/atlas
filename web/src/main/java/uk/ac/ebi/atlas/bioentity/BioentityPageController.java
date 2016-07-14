package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BioentityPageController {

    private static final String BIOENTITY_PROPERTY_NAME = "symbol";
    private static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private BioEntityCardProperties bioEntityCardProperties;

    protected AnalyticsSearchService analyticsSearchService;
    protected BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer;
    protected BioEntityPropertyService bioEntityPropertyService;
    protected DifferentialAnalyticsSearchService differentialAnalyticsSearchService;

    protected String[] propertyNames;

    @Inject
    public void setAnalyticsSearchService(AnalyticsSearchService analyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
    }

    @Inject
    public void setBioentityPropertyServiceInitializer(BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer) {
        this.bioentityPropertyServiceInitializer = bioentityPropertyServiceInitializer;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    @Inject
    public void setBioEntityCardProperties(BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    @Inject
    public void setDifferentialAnalyticsSearchService(DifferentialAnalyticsSearchService differentialAnalyticsSearchService) {
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
    }

    @Inject
    public void setBaselineAnalyticsSearchService(BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    // identifier (gene) = an Ensembl identifier (gene, transcript, or protein) or a mirna identifier or an MGI term.
    // identifier (gene set) = a Reactome id, Plant Ontology or Gene Ontology accession or an InterPro term
    public String showBioentityPage(String identifier, Model model, Set<String> experimentTypes) {

        boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
        boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

        if (!hasDifferentialResults && !hasBaselineResults) {
            return "empty-search-page";
        }

        model.addAttribute("hasBaselineResults", hasBaselineResults);
        model.addAttribute("hasDifferentialResults", hasDifferentialResults);

        if (hasBaselineResults) {
            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(GeneQuery.create(identifier)));
        }

        if (model.containsAttribute("searchDescription")) {
            model.addAttribute("isSearch", true);
        }

        model.addAttribute("identifier", identifier);
        model.addAllAttributes(pageDescriptionAttributes(identifier));
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioentities";
    }

    protected Map<String, Object> pageDescriptionAttributes(String identifier){
        String s = "Expression summary for " + bioEntityPropertyService.getBioEntityDescription();
        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "bioentity,"+identifier
        );
    }

    protected Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyName : propertyNames) {
            if (isDisplayedInPropertyList(propertyName)) {
                result.put(propertyName, bioEntityCardProperties.getPropertyName(propertyName));
            }
        }
        return result;
    }

    private boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(PROPERTY_TYPE_DESCRIPTION) && !propertyType.equals(BIOENTITY_PROPERTY_NAME);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }
}
