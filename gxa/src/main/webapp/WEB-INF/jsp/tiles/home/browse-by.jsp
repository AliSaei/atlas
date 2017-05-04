<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="protocol" value="${pageContext.request.scheme}://"/>
<c:set var="atlasHost" value="${pageContext.request.serverName}"/>
<c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="callout browse-by padding-bottom-for-button" data-equalizer-watch>
    <ul class="tabs" data-tabs id="browse-by-tabs">
        <li class="tabs-title is-active"><a href="#by-species" aria-selected="true">By species</a></li>
        <li class="tabs-title"><a href="#by-animals">Animals</a></li>
        <li class="tabs-title"><a href="#by-plants">Plants</a></li>
        <li class="tabs-title"><a href="#by-fungi">Fungi</a></li>
    </ul>

    <div class="tabs-content" data-tabs-content="browse-by-tabs">
        <div class="tabs-panel is-active" id="by-species"></div>
        <div class="tabs-panel" id="by-animals"></div>
        <div class="tabs-panel" id="by-plants"></div>
        <div class="tabs-panel" id="by-fungi"></div>
    </div>

    <div class="row align-row-to-bottom">
        <div class="small-6 small-centered columns margin-top-large">
            <a id="viewAllLink" href='${pageContext.request.contextPath}/experiments' class="button float-center">View all species</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/vendorCommons.bundle.js"></script>
<script src="${pageContext.request.contextPath}/versioned-resources-${resourcesVersion}/js-bundles/expressionAtlasBrowseBySpecies.bundle.js"></script>

<script>
    var $viewAllLink = $('#viewAllLink');
    $('#browse-by-tabs').on('change.zf.tabs', function() {
        switch ($('#browse-by-tabs .tabs-title.is-active').text()) {
            case 'Animals':
                $viewAllLink.attr('href','${pageContext.request.contextPath}/experiments?kingdom=animals');
                break;
            case 'Plants':
                $viewAllLink.attr('href','${pageContext.request.contextPath}/experiments?kingdom=plants');
                break;
            case 'Fungi':
                $viewAllLink.attr('href','${pageContext.request.contextPath}/experiments?kingdom=fungi');
                break;
            default:
                $viewAllLink.attr('href','${pageContext.request.contextPath}/experiments');
                break;
        }
    });

    expressionAtlasBrowseBySpecies.render({
        atlasUrl: '${protocol}${atlasHost}${serverPort}/${contextPath}/',
        speciesInfoList: ${speciesList},
        container: document.getElementById('by-species')
    });
    expressionAtlasBrowseBySpecies.render({
        atlasUrl: '${protocol}${atlasHost}${serverPort}/${contextPath}/',
        speciesInfoList : ${animalsList},
        container: document.getElementById('by-animals')
    });
    expressionAtlasBrowseBySpecies.render({
        atlasUrl: '${protocol}${atlasHost}${serverPort}/${contextPath}/',
        speciesInfoList : ${plantsList},
        container: document.getElementById('by-plants')
    });
    expressionAtlasBrowseBySpecies.render({
        atlasUrl: '${protocol}${atlasHost}${serverPort}/${contextPath}/',
        speciesInfoList : ${fungiList},
        container: document.getElementById('by-fungi')
    });
</script>