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

package uk.ac.ebi.atlas.solr.query;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
@Scope("request")
public class SuggestionService {

    private static final Logger LOGGER = Logger.getLogger(SuggestionService.class);

    private static final int MAX_NUMBER_OF_SUGGESTIONS = 15;

    private GeneIdSuggestionService geneIdSuggestionService;
    private MultiTermSuggestionService multiTermSuggestionService;

    @Inject
    public SuggestionService(GeneIdSuggestionService geneIdSuggestionService, MultiTermSuggestionService multiTermSuggestionService) {
        this.geneIdSuggestionService = geneIdSuggestionService;
        this.multiTermSuggestionService = multiTermSuggestionService;
    }

    public List<String> fetchTopSuggestions(String query, @Nullable String species) {
        LOGGER.info(String.format("fetchTopSuggestions for query %s, species %s", query, species));

        LinkedHashSet<String> suggestions = Sets.newLinkedHashSet();

        species = StringUtils.lowerCase(species);

        if (!StringUtils.containsWhitespace(query)) {
            suggestions.addAll(geneIdSuggestionService.fetchGeneIdSuggestionsInName(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(geneIdSuggestionService.fetchGeneIdSuggestionsInSynonym(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            suggestions.addAll(geneIdSuggestionService.fetchGeneIdSuggestionsInIdentifier(query, species));
        }

        if (suggestions.size() < MAX_NUMBER_OF_SUGGESTIONS) {
            List<String> multiTermSuggestions = multiTermSuggestionService.fetchMultiTermSuggestions(query);
            suggestions.addAll(multiTermSuggestions);
        }

        List<String> topSuggestions = Lists.newArrayList(suggestions);

        if (topSuggestions.size() > MAX_NUMBER_OF_SUGGESTIONS) {
            topSuggestions = topSuggestions.subList(0, MAX_NUMBER_OF_SUGGESTIONS);
        }

        return topSuggestions;
    }

}
