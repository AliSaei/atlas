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
import uk.ac.ebi.atlas.geneindex.SolrClient;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Controller
@Scope("request")
public class GenePageController extends BioentityPageController {

    public static final String PROPERTY_TYPE_SYMBOL = "symbol";

    private String genePagePropertyTypes;

    @Inject
    GenePageController(SolrClient solrClient,
                       @Named("genecard") Properties geneCardProperties,
                       @Value("#{configuration['index.types.genepage']}") String genePagePropertyTypes) {
        super(solrClient, geneCardProperties);
        this.genePagePropertyTypes = genePagePropertyTypes;
    }

    @RequestMapping(value = "/genes/{identifier}")
    public String showGenePage(@PathVariable String identifier, Model model) {
        return super.showGenePage(identifier, model);
    }

    @Override
    String getPagePropertyTypes() {
        return genePagePropertyTypes;
    }

    @Override
    String getSymbolType() {
        return PROPERTY_TYPE_SYMBOL;
    }

}