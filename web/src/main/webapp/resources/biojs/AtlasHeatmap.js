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

/**
 * This is the description of the AtlasHeatmap component for displaying
 * baseline expression of genes based on RNA-seq experiments in the ExpressionAtlas
 * database.
 *
 * @class Biojs.AtlasHeatmap
 * @extends Biojs
 *
 * @author <a href="mailto:atlas-developers@ebi.ac.uk">ExpressionAtlas Team</a>
 * @version 1.0.0
 * @category 1
 *
 * @requires <a href='http://code.jquery.com/jquery-1.6.4.js'>jQuery Core 1.6.4</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="../biojs/dependencies/jquery/jquery-1.6.4.min.js"></script>
 *
 * @param {Object} options An object with the options for AtlasHeatmap component.
 *
 * @option {string} featuresUrl
 *    The query URL pointing to the ExpressionAtlas for retrieving gene page results
 *    displayed as part of this widget. It is usually composed to include the identifier
 *    of the gene you are interested in, see example.
 *
 * @option {string} target
 *    Identifier of the DIV tag where the component should be displayed.
 *
 * @example
 * var instance = new Biojs.Heatmap({
 * featuresUrl: '/gxa/widgets/heatmap/protein?geneQuery=P00846',
 * target : "YourOwnDivId"
 * });
 *
 */
Biojs.AtlasHeatmap = Biojs.extend({

    constructor:function (options) {

        var self = this;

        var containerDiv = jQuery("#" + self.opt.target);

        var options = self.opt;
        var httpRequest = {
            url:options.featuresUrl,

            methid:"GET",
            success:function (htmlResponse) {
                Biojs.console.log("SUCCESS: data received");
                Biojs.console.log(htmlResponse);
                containerDiv.append(htmlResponse);
            },
            error:function (textStatus) {
                Biojs.console.log("ERROR: " + textStatus);
            }
        };

        jQuery.ajax(httpRequest);

    },

    opt:{
        /* Features URL
         This mandatory parameter consists of the query for a particular
         gene or genes by given their properties. For a single gene query,
         please use a unique accession (e.g. ENSEMBL gene id or UniProt id).
         For example search with UniProt id P00846 returns the gene mt-atp6,
         a search for REACT_6900 returns genes belonging to this pathway.
         An additional parameter (&geneSetMatch=true) can be appended after
         the query term to collapse multiple returned gene profiles into one
         single line of average expression (this feature is still experimental).
         */
        featuresUrl:'/gxa/widgets/heatmap/protein?geneQuery=P00846',
        /* Target DIV
         This mandatory parameter is the identifier of the DIV tag where the
         component should be displayed. Use this value to draw your
         component into. */
        target:"YourOwnDivId"

    }
});
