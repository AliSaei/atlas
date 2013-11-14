<%--
  ~ Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  ~
  ~
  ~ For further details of the Gene Expression Atlas project, including source code,
  ~ downloads and documentation, please see:
  ~
  ~ http://gxa.github.com/gxa
  --%>

<%--@elvariable id="applicationProperties" type="uk.ac.ebi.atlas.web.ApplicationProperties"--%>

<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="base" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty preferences.rootContext}">
    <c:set var="base" value="${preferences.rootContext}"/>
</c:if>


<table><tbody><tr><td><table id="diff-heatmap-table" class="table-grid">
    <thead>
    <tr>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Gene</div>
        </th>
        <th  id="design-element-header" class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Design Element</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Organism</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px; text-align:center;">
            <div>Contrast</div>
        </th>
        <th class="horizontal-header-cell" style="padding: 5px;">
            <div class='factor-header' data-organism-part=''>Adjusted P-value</div>
        </th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${bioentities}"
               var="differentialBioentityExpression">
        <tr>
            <td class="horizontal-header-cell">
                ${differentialBioentityExpression.bioentityName}
            </td>
            <td class="horizontal-header-cell">
                 ${differentialBioentityExpression.designElement}
            </td>
            <td class="horizontal-header-cell">
                    ${differentialBioentityExpression.species}
            </td>
            <td class="horizontal-header-cell contrastNameCell"
                data-experiment-accession="${differentialBioentityExpression.experimentAccession}"
                data-contrast-id="${differentialBioentityExpression.contrastId}">
                <a href="experiments/${differentialBioentityExpression.experimentPageUrl}">${differentialBioentityExpression.contrastDisplayName}</a>
            </td>

            <c:set var="expression" value="${differentialBioentityExpression.expression}"/>

            <c:set var="expressionLevel"
                   value="${expression.level}"/>

            <c:if test="${! empty expressionLevel}">

                <c:choose>
                    <c:when test="${expression.overExpressed}">
                        <c:set var="cellColour"
                               value="${colourGradient.getGradientColour(1 - expressionLevel, 1 - bioentities.getMaxUpRegulatedExpressionLevel(), 1 - bioentities.getMinUpRegulatedExpressionLevel(), 'pink', 'red')}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="cellColour"
                               value="${colourGradient.getGradientColour(1 - expressionLevel,  1 - bioentities.getMaxDownRegulatedExpressionLevel(), 1 - bioentities.getMinDownRegulatedExpressionLevel(), 'lightGray', 'blue')}"/>
                    </c:otherwise>
                </c:choose>

                <c:set var="style" value="background-color:${cellColour}"/>

            </c:if>

            <td style="${style}">

                <c:if test="${not empty expressionLevel}">

                    <c:choose>
                        <c:when test="${expression.notApplicable}">
                            <c:set var="foldChange" value="N/A"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatNumber type="number"
                                              maxFractionDigits="2"
                                              value="${expression.foldChange}"
                                              groupingUsed="false"
                                              var="foldChange"/>

                        </c:otherwise>
                    </c:choose>

                    <div class="hide_cell" ${type.isMicroarray() ? 'data-tstatistic="'.concat(tstatistic).concat('"'):""}
                        ${'data-fold-change="'.concat(foldChange).concat('"')}
                         data-organism-part="${firstInRow}" data-color="${cellColour}">
                            ${numberUtils.htmlFormatDouble(expressionLevel)}
                    </div>

                </c:if>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</td>
    <td style="vertical-align: top">
        <div style="float:left;">
            <a id="download-profiles-link"
               title="Top 50 genes displayed on page. Download results to see the rest."
               href="${applicationProperties.buildDownloadURL(pageContext.request)}"
               class="button-image" target="_blank">
                <img id="download-profiles" alt="Download query results" style="width:20px"
                     src="${base}/resources/images/download_blue_small.png">
            </a>
        </div>
    </td>
</tr>
</tbody>
</table>
<script language="JavaScript" type="text/javascript" src="${base}/resources/js/heatmapModule.js"></script>

<script type="text/javascript">
    (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
        $(document).ready(function () {

            heatmapModule.initRnaSeqHeatmap(${preferences.cutoff});

            $("#injected-header").remove();
            $("#heatmap-table th").attr("rowspan", "1");
        });
    })(jQuery);
</script>