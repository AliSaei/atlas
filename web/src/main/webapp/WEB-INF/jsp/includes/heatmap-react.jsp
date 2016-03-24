<%--@elvariable id="isMultiExperiment" type="boolean"--%>

<c:choose>
    <c:when test="${empty jsonProfiles}">
        <c:if test="${not isPreferenceError}">
            <div id="heatmap-message">
                No expressions found
            </div>
        </c:if>
    </c:when>
    <c:otherwise>

    <script src="${pageContext.request.contextPath}/resources/js-bundles/vendor.bundle.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js-bundles/internalAtlasHeatmap.bundle.js"></script>

    <div id="genenametooltip-content" style="display: none"></div>

        <script type="text/javascript">

            var heatmapData = <%@ include file="heatmap-data.jsp" %>;
            var isMultiExperiment = ${isMultiExperiment ? true : false};

            internalAtlasHeatmap({
                heatmapData: heatmapData,
                isMultiExperiment: isMultiExperiment,
                isDifferential: ${type.differential},
                isProteomicsBaseline: ${type.proteomicsBaseline}
            });

        </script>
    </c:otherwise>
</c:choose>
