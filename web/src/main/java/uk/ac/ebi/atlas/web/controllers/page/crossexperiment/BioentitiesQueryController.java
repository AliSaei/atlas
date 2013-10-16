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

package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.DifferentialBioentityExpressionsBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Controller
@Scope("prototype")
public class BioentitiesQueryController {

    private SolrQueryService solrQueryService;
    private DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder;

    @Inject
    public BioentitiesQueryController(SolrQueryService solrQueryService, DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder) {
        this.solrQueryService = solrQueryService;
        this.differentialBioentityExpressionsBuilder = differentialBioentityExpressionsBuilder;
    }

    @RequestMapping(value = "/query")
    public String showConditionsResultPage(@RequestParam (value="condition", required = true) String condition, Model model) {
        model.addAttribute("entityIdentifier", condition);

        DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.withCondition(condition).build();

        model.addAttribute("bioentities", bioentityExpressions);

        model.addAttribute("preferences", new DifferentialRequestPreferences());

        return "bioEntities";
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public String handleException(Exception e) {
        return "bioEntities";
    }

    @RequestMapping(value = "/query", params = {"geneQuery"})
    public String showGeneQueryResultPage(@RequestParam(value="geneQuery", required = true) String geneQuery, Model model) {

        List<String> identifiers = Lists.newArrayList(StringUtils.split(geneQuery, " "));

        model.addAttribute("entityIdentifier", geneQuery);

        Set<String> ensemblIDs = Sets.newHashSet();
        for (String identifier : identifiers) {
            ensemblIDs.addAll(solrQueryService.fetchGeneIdentifiersFromSolr(identifier, "ensgene", true, "mirbase_id"));
        }

        if (ensemblIDs.size() > 0) {
            model.addAttribute("ensemblIdentifiersForMiRNA", "+" + Joiner.on("+").join(ensemblIDs));
        }

        DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.withGeneIdentifiers(Sets.newHashSet(identifiers)).build();

        model.addAttribute("bioentities", bioentityExpressions);

        model.addAttribute("preferences", new DifferentialRequestPreferences());

        return "bioEntities";
    }


}
