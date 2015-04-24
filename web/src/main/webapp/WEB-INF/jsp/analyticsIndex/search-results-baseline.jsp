<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<c:set var="thisPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<link type="text/css" rel="stylesheet" href="/gxa/resources/css/facets.css" />

<script language="JavaScript" type="text/javascript" src="//www.ebi.ac.uk/Tools/biojs/biojs/Biojs.js"></script>
<script language="JavaScript" type="text/javascript" src="/gxa/resources/biojs/AtlasHeatmapReact.js"></script>

<%@ include file="../includes/react.jsp" %>
<%@ include file="../includes/heatmap-js.jsp" %>
<%@ include file="../includes/anatomogram.jsp" %>

<section class="grid_23 extra-padding">
    <%@ include file="includes/search-form.jsp" %>
</section>

<c:if test="${not empty searchDescription}" >
    <section class="grid_23 extra-padding">
        <section class="grid_12 alpha extra-padding">
            <div style="font-weight: bold">
            <c:if test="${hasBaselineResults}">
                Baseline
            </c:if>
            <c:if test="${!hasBaselineResults}">
                Baseline (no results)
            </c:if>
            </div>
        </section>

        <section class="grid_11 extra-padding">
            <c:if test="${hasDifferentialResults}">
                <a href="${pageContext.request.contextPath}/search/differential?geneQuery=${geneQuery.asUrlQueryParameter()}">Differential</a>
            </c:if>
            <c:if test="${!hasDifferentialResults}">
                Differential (no results)
            </c:if>
        </section>
    </section>

    <section class="grid_17 alpha extra-padding">
        <h5 class="strapline">
            Baseline results for <span class="searchterm">${searchDescription}</span>
        </h5>
    </section>
    <h:ebiGlobalSearch ebiSearchTerm="${applicationProperties.urlParamEncode(globalSearchTerm)}"/>
</c:if>


<section class="grid_23 extra-padding">
    <c:if test="${!hasBaselineResults}">
        No baseline results
    </c:if>

    <div id="atlasAnalyticsSearchFacetContainer"></div>
    <div id="atlasAnalyticsSearchHeatmapsContainer"></div>
</section>


<script src="${pageContext.request.contextPath}/resources/js/lib/query-string.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/heatmaps.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/facets.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/heatmapsRouter.js"></script>


<script src="${pageContext.request.contextPath}/resources/jsx/bioJSAtlasHeatmap.js"></script>
<script src="${pageContext.request.contextPath}/resources/jsx/heatmapContainer.js"></script>


<script>

    var facetsData = ${empty jsonFacets ? 'null' : jsonFacets};

    (function (HeatmapsRouter, facetsData) {

        if (facetsData) {
            HeatmapsRouter(
                    document.getElementById('atlasAnalyticsSearchFacetContainer'),
                    document.getElementById('atlasAnalyticsSearchHeatmapsContainer'),
                    facetsData);
        }

    })(HeatmapsRouter, facetsData);

</script>
