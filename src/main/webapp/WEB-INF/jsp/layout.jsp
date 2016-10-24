<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml" lang="eng" class="no-js">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta content="en-GB" http-equiv="Content-Language">
    <meta content="_top" http-equiv="Window-target">
    <meta content="http://www.unspam.com/noemailcollection/" name="no-email-collection">
    <meta name="google-site-verification" content="4xV6HGqtqbEXtsFgcZGrPUSwcHb03eNNu7B_fFuwdUU" />

    <c:set var="baseUri" value="${requestScope['javax.servlet.forward.request_uri']}" />
    <c:set var="experimentAccession" value="${experimentAccession}"/>
    <c:set var="arrayDesign" value="${arrayDesign}"/>
    <c:set var="species" value="${species}" />
    <c:set var="specie_s" value="${fn:replace(species,' ','%20')}"/>

    <c:set var="serverPort" value="${pageContext.request.serverPort == 80 ? '' : ':'.concat(pageContext.request.serverPort)}"/>

    <c:set var="qcUri" value="${'/gxa/experiments/'.concat(experimentAccession).concat('/qc/').concat(arrayDesign).concat('/index.html')}" />
    <c:set var="fastQCUri" value="${'/gxa/experiments/'.concat(experimentAccession).concat('/fastqc/').concat(specie_s).concat('/qc.html')}" />
    <c:set var="fastQCMappingUri" value="${'/gxa/experiments/'.concat(experimentAccession).concat('/fastqc/').concat(specie_s).concat('/mapping/tophat2.html')}" />

    <c:if test="${!baseUri.equals(qcUri) && !baseUri.equals(fastQCUri) && !baseUri.equals(fastQCMappingUri) && !baseUri.endsWith('fastqc_report.html')}">
        <base href="//${pageContext.request.serverName}${serverPort}${pageContext.request.contextPath}/"/>
    </c:if>

    <!-- compliance style start -->

    <meta name="description"
          content="${ empty pageDescription ?
          'EMBL-EBI Expression Atlas- an open public repository of gene expression pattern data under different biological conditions' : pageDescription} ">
    <!-- Describe what this page is about -->

    <meta name="keywords"
          content="${empty pageKeywords?'bioinformatics, atlas, gxa, ebi, functional, genomics, repository, public':pageKeywords}">
    <!-- A few keywords that relate to the content of THIS PAGE (not the whole project) -->
    <meta name="author" content="EMBL-EBI Expression Atlas development team: github.com/gxa/atlas/graphs/contributors">
    <!-- Your [project-name] here -->

    <!-- Mobile viewport optimized: j.mp/bplateviewport -->
    <meta name="viewport" content="width=device-width,initial-scale=1">

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory: mathiasbynens.be/notes/touch-icons -->

    <!-- CSS: implied media=all -->
    <!-- CSS concatenated and minified via ant build script-->

    <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/boilerplate-style.css">
    <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-global.css">
    <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css">
    <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/984-24-col-fluid.css">
    <link type="text/css" rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/develop/embl-petrol-colours.css">

    <style type="text/css">
        /* You have the option of setting a maximum width for your page, and making sure everything is centered */
        /* body { max-width: 1600px; margin: 0 auto; } */
    </style>

    <!-- end CSS-->

    <!-- All JavaScript at the bottom, except for Modernizr / Respond.
         Modernizr enables HTML5 elements & feature detects; Respond is a polyfill for min/max-width CSS3 Media Queries
         For optimal performance, use a custom Modernizr build: www.modernizr.com/download/ -->

    <!-- Full build -->
    <script src="//www.ebi.ac.uk/web_guidelines/js/libs/modernizr.minified.2.1.6.js"></script>

    <!-- compliance style end -->
    <c:set var="mainTitle" value="${mainTitle}"/>
    <c:if test="${mainTitle != null}">
        <c:set var="completeTitle" value="${mainTitle.concat(' < Expression Atlas < EMBL-EBI ')}"/>
        <title>${completeTitle}</title></c:if>
    <c:if test="${mainTitle == null}">
        <title><tiles:insertAttribute name="title" ignore="true"/> &lt; EMBL-EBI</title>
    </c:if>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery-ui-1.11.4.custom/css/jquery-ui.min.css" media="screen">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/atlas.css" media="screen">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.json-tag-editor.css" media="screen">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/ae-autocomplete.css" media="screen">

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customEventPolyFillForIE.js"></script>

    <script language="JavaScript" type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/json2/20150503/json2.min.js"></script>

    <script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script language="javascript" type="text/javascript" src="https://code.jquery.com/jquery-migrate-1.4.0.min.js"></script>
    <script language="JavaScript" type="text/javascript" src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.caret.min.js"></script>
    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-json-tag-editor/jquery.json-tag-editor.min.js"></script>

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/helpTooltipsModule.js"></script>
    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/resources/js/geneQueryTagEditorModule.js"></script>

    <script>
        (function ($) { //self invoking wrapper function that prevents $ namespace conflicts
            $(document).ready(function () {
                geneQueryTagEditorModule.init("#local-searchbox");

                searchBoxEnterEventHandler("#submit-searchbox");
                function searchBoxEnterEventHandler(element) {
                    $('#local-search .tag-editor').on('submit', function (e) {
                        $(element).click();
                    });
                }
            });
        })(jQuery);
    </script>

</head>

<body class="gxaBody full-width">

<div id="skip-to">
    <ul>
        <li><a href="#content">Skip to main content</a></li>
        <li><a href="#gxaLocalNav">Skip to local navigation</a></li>
        <li><a href="#global-nav">Skip to EBI global navigation menu</a></li>
        <li><a href="#global-nav-expanded">Skip to expanded EBI global navigation menu (includes all sub-sections)</a>
        </li>
    </ul>
</div>

<div id="wrapper" class="container_24">

    <tiles:insertAttribute name="top-menu"/>

    <div id="content" class="grid_24 clearfix" role="main">
        <tiles:insertAttribute name="header" ignore="true"/>
        <tiles:insertAttribute name="body"/>
    </div>

    <tiles:insertAttribute name="footer"/>

</div>

</body>

</html>