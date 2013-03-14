<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

<div id="preferencesFormBlock" class="block-max-width">
    <form:form method="get" commandName="preferences" id="prefForm">
        <form:hidden path="serializedFilterFactors"/>
        <form:hidden path="queryFactorType"/>
        <form:hidden path="heatmapMatrixSize"/>
        <form:hidden id="displayLevels" path="displayLevels"/>
        <form:hidden id="displayGeneDistribution" path="displayGeneDistribution"/>

        <form:errors path="*" cssClass="error"/>
        <table class="form-grid">
            <tr>
                <td>
                    <form:label path="geneQuery">Gene Query</form:label>
                    <span data-help-loc="#geneSearch"/>
                </td>
                <c:if test="${selectedFilterFactors.size() > 0 || type eq 'DIFFERENTIAL'}">
                    <td>
                        <label>${type eq 'BASELINE' ? 'Filtered by' : 'Differential'}</label>
                        <span data-help-loc="#filterBy"></span>
                    </td>
                </c:if>
                <td>
                    <form:label path="queryFactorValues">${queryFactorName}</form:label>
                    <span data-help-loc="#factorSearch"/>
                </td>
                <td style="width:100%;display:block">
                    <form:label path="cutoff">${type eq 'BASELINE' ? 'Expression level cutoff' : 'False discovery rate cutoff'}</form:label>
                    <span data-help-loc="#cutoff"/>
                </td>
                <td rowspan="2" style="display:table-cell;text-align:center;vertical-align: middle;">
                    <div>
                        <div>
                            <input id="submit-button" type="submit" value="Search"/>
                        </div>
                        <div>
                            <input id="reset-button" type="button" value="Reset"/>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div>
                        <form:textarea id="geneQuery" path="geneQuery" maxlenght="900" rows="3" cols="30"></form:textarea>
                    </div>
                </td>
                <c:if test="${selectedFilterFactors.size() > 0}">
                    <td>
                        <c:import url="includes/filterby-menu.jsp"/>
                    </td>
                </c:if>
                <c:if test="${type eq 'DIFFERENTIAL'}">
                    <td>
                        <c:import url="includes/contrast-up-down-menu.jsp"/>
                    </td>
                </c:if>
                <td>
                    <div>
                        <c:set var="isSingleContrast" value="${type eq 'DIFFERENTIAL' && allQueryFactors.size() == 1}"/>
                        <c:set var="itemLabel" value="${type eq 'DIFFERENTIAL'? 'displayName' : 'value'}"/>
                        <c:set var="itemValue" value="${type eq 'DIFFERENTIAL'? 'id' : 'value'}"/>
                        <form:select path="queryFactorValues" data-placeholder="(any ${queryFactorName}s)"
                                     tabindex="-1"
                                     items="${allQueryFactors}" itemValue="${itemValue}" itemLabel="${itemLabel}"
                                     cssStyle="width:300px"
                                     disabled="${isSingleContrast ? true : false}"/>
                    </div>
                        <span>
                            <form:checkbox id="specific"
                                           path="specific"
                                           label="Specific"
                                            disabled="${type eq 'DIFFERENTIAL' ? true : false}"></form:checkbox>
                        </span>
                    <span data-help-loc="#specific" style="display:inline-block"/>
                </td>
                <td>
                    <div>
                        <c:choose>
                            <c:when test="${fn:endsWith('' + preferences.cutoff, '.0')}">
                                <fmt:formatNumber value="${preferences.cutoff}" groupingUsed="false"
                                                  type="number"
                                                  maxFractionDigits="0"
                                                  var="formattedCutoff"/>
                                <form:input size="10" path="cutoff" value="${formattedCutoff}" id="cutoff"
                                            style="border:1; font-weight:bold;"/>
                            </c:when>
                            <c:otherwise>
                                <form:input size="10" path="cutoff" id="cutoff"
                                            style="border:1; font-weight:bold;"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
    </form:form>
    <div style="min-width: 955px;position:relative" id="gene-distribution-panel">
        <div class="barchart-tooltip" id="barChartTooltip"></div>
        <div id="gene-distribution" style="height:100px;width:940px;display:inline-block;">
        </div>
        <span data-help-loc="#gene-distribution" style="vertical-align: top"></span>
    </div>

    <div style="min-width: 955px;display:none;" id="sliderAndChart">
        <span style="display:inline-block">
            <div id="gene-distribution-button" style="float:left">
                <a id="display-chart" title="Display gene distribution" class="button-image" href="#">
                    <img alt="Display gene distribution" src="resources/images/yellow-chart-icon-16.png"/>
                </a>
            </div>
            <div id="slider-range-max"
                 style="font-size:65%;width:910px;margin-left:27px;margin-right:0px; margin-top:10px"></div>
        </span>
        <span id="slider-help" data-help-loc="#slider"></span>
    </div>
</div>


