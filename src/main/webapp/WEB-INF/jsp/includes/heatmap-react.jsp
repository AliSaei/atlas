<%--@elvariable id="isMultiExperiment" type="boolean"--%>
<%--@elvariable id="type" type="uk.ac.ebi.atlas.model.ExperimentType"--%>

<script src="${pageContext.request.contextPath}/resources/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/resources/js-bundles/experimentPageHeatmap.bundle.js"></script>

<div id="genenametooltip-content" style="display: none"></div>

<script type="text/javascript">

    experimentPageHeatmap.render({
        atlasHost: "${not empty atlasHost? atlasHost: "www.ebi.ac.uk"}",
        sourceURL: "${sourceURL}",
        isMultiExperiment: ${isMultiExperiment ? true : false},
        isDifferential: ${type.differential},
        isProteomicsBaseline: ${type.proteomicsBaseline}
    });
</script>