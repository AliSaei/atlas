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
<%--@elvariable id="bioEntityPropertyService" type="uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div id="help-placeholder" style="display: none"></div>

<script language="JavaScript" type="text/javascript" src="//www.ebi.ac.uk/Tools/biojs/biojs/Biojs.js"></script>
<script language="JavaScript" type="text/javascript" src="/gxa/resources/biojs/AtlasHeatmapReact.js"></script>

<link type="text/css" rel="stylesheet" href="/gxa/resources/css/facets.css" />

<%@ include file="includes/react.jsp" %>
<%@ include file="includes/heatmap-js.jsp" %>
<%@ include file="includes/anatomogram.jsp" %>

<section class="grid_23 extra-padding">
    <%@ include file="includes/search-form.jsp" %>
</section>

<c:if test="${not empty searchDescription}" >
    <section class="grid_23 extra-padding">
        <section class="grid_12 alpha extra-padding">
            <c:if test="${hasBaselineResults}">
                <a href="${pageContext.request.contextPath}/search?geneQuery=${pageContext.request.getParameter("geneQuery")}">Baseline</a>
            </c:if>
            <c:if test="${!hasBaselineResults}">
                Baseline (no results)
            </c:if>
        </section>

        <section class="grid_11 extra-padding">
            <c:if test="${hasDifferentialResults}">
                <div style="font-weight: bold">
                    Differential
                </div>
            </c:if>
            <c:if test="${!hasDifferentialResults}">
                Differential (no results)
            </c:if>
        </section>
    </section>

    <section class="grid_17 alpha extra-padding">
        <h5 class="strapline">
            Differential results for <span class="searchterm">${searchDescription}</span>
        </h5>

    </section>
    <h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>
</c:if>


<section class="grid_23 extra-padding">

    <c:if test="${!hasDifferentialResults}">
        No differential results
    </c:if>

    <div id="facets"></div>
    <div id="results"></div>
</section>

<script src="${pageContext.request.contextPath}/resources/js/lib/query-string.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/facets.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/differentialResults.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/differentialRouter.js"></script>


<script>

    var facetsData = ${empty jsonDifferentialGeneQueryFacets ? 'null' : jsonDifferentialGeneQueryFacets};
    var diffResultsData = ${empty jsonDifferentialGeneQueryResults ? 'null': jsonDifferentialGeneQueryResults};

    (function (DifferentialRouter, facetsData, diffResultsData) {

        if (facetsData) {
            DifferentialRouter(document.getElementById('facets'), document.getElementById('results'), facetsData, diffResultsData);
        }

    })(DifferentialRouter, facetsData, diffResultsData);

</script>


