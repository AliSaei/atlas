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

/*global $, jQuery, console:false */

var helpTooltipsModule = (function($) {
    "use strict";

    function buildHelpAnchor() {
        return $("<a/>", {
                    'class': 'help-icon',
                    'href': '#',
                    'title': '',
                    'text': '?'
                });
    }

    function getHelpLocation(pageName, inlineAnchor) {
        return 'resources/html/' + getHelpFileName(pageName) + ' ' + inlineAnchor;
    }

    function getHelpFileName(pageName){
        return 'help-tooltips.' + pageName + '-page.html';
    }

    function initTooltips(pageName){

        var anchor = buildHelpAnchor();

        $("[data-help-loc]")
            .append(anchor)
            .tooltip(
            {
                tooltipClass: "help-tooltip",
                content: function(callback) {
                    var tooltipHelpHtmlId = $(this).parent().attr('data-help-loc');
                    $("#test").load(getHelpLocation(pageName, tooltipHelpHtmlId),
                        function (response, status, xhr) {
                            var tooltipContent;
                            if (status === "error") {
                                tooltipContent = "Sorry but there was an error: " + xhr.status + " " + xhr.statusText;
                            }
                            tooltipContent = $(this).text();
                            if (!tooltipContent){
                                tooltipContent = "Missing help section for id = " + tooltipHelpHtmlId + " in html file " + getHelpFileName(pageName);
                            }
                            callback(tooltipContent);

                        }
                    );

                }
            });

    }

    return {
        init:  function(pageName) {
                    initTooltips(pageName);
                },
        buildHelpAnchor: buildHelpAnchor()
    };

}(jQuery));
