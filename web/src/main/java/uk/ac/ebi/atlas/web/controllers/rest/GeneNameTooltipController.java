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

package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@Scope("request")
public class GeneNameTooltipController {
    private static final Logger LOGGER = Logger.getLogger(GeneNameTooltipController.class);
    private static final String WORD_SPAN_OPEN = "<span class='property-value-markup'>";
    private static final String WORD_SPAN_CLOSE = "</span>";
    private static final int NUMBER_OF_TERMS_TO_SHOW = 20;

    private SolrQueryService solrQueryService;

    private Resource htmlTemplateResource;

    private String htmlTemplate;

    @Inject
    public GeneNameTooltipController(SolrQueryService solrQueryService,
                                     @Value("classpath:/html-templates/geneNameTooltipTemplate.html") Resource htmlTemplateResource) {
        this.solrQueryService = solrQueryService;
        this.htmlTemplateResource = htmlTemplateResource;
    }

    @PostConstruct
    void initTemplate() {
        try (InputStream inputStream = htmlTemplateResource.getInputStream()) {
            htmlTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @RequestMapping(value = "/rest/genename-tooltip", method = RequestMethod.GET, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTooltipContent(@RequestParam(value = "geneName") String geneName,
                                    @RequestParam(value = "identifier") String identifier) {

        Multimap<String, String> multimap = solrQueryService.fetchTooltipProperties(identifier);

        String synonyms = buildSynonyms(identifier, multimap);

        String goTerms = format(multimap.get("goterm"), true, NUMBER_OF_TERMS_TO_SHOW);

        String interproTerms = format(multimap.get("interproterm"), true, NUMBER_OF_TERMS_TO_SHOW);

        return MessageFormat.format(htmlTemplate, geneName, synonyms, goTerms, interproTerms);

    }

    String format(Collection<String> values, boolean returnEmptyValuesAsNA, int restrictListLengthTo) {
        if (CollectionUtils.isEmpty(values)) {
            return returnEmptyValuesAsNA ? "NA" : StringUtils.EMPTY;
        }
        List<String> subList;
        if (values.size() > restrictListLengthTo) {
            subList = Collections.list(Collections.enumeration(values)).subList(0, restrictListLengthTo);
            subList.add("...(total " + values.size() + ")");
        } else {
            subList = Collections.list(Collections.enumeration(values));
        }

        return WORD_SPAN_OPEN + Joiner.on(WORD_SPAN_CLOSE + " " + WORD_SPAN_OPEN).join(subList) + WORD_SPAN_CLOSE;
    }

    String buildSynonyms(String identifier, Multimap<String, String> multimap) {

        String synonyms = format(multimap.get("synonym"), false, NUMBER_OF_TERMS_TO_SHOW);

        String enclosedIdentifier = WORD_SPAN_OPEN + identifier + WORD_SPAN_CLOSE;

        if (synonyms.isEmpty()) {
            return enclosedIdentifier;
        }

        return Joiner.on(" ").join(synonyms, enclosedIdentifier);
    }

}
