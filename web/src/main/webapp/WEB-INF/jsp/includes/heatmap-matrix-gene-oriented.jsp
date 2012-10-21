<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="configuration" var="configuration"/>

<display:table name="${heatmapGenes}" id="geneId"
               htmlId="heatmap-table" class="table-grid">
    <display:column title="<button id='display-levels' /><label for='display-levels'>Display levels</label>"
                    class="header-cell">
        <fmt:message bundle="${configuration}" key="gxa.gene.url.template" var="genePageURL">
            <fmt:param value="${geneId}"/>
        </fmt:message>
        <a href='${genePageURL}' target='_blank'>${geneId}</a>
    </display:column>

    <c:forEach var="organismPart" items="${heatmapOrganismParts}">

       <c:set var="roundedExpressionLevel"
               value="${geneExpressions.getRoundedExpressionLevel(geneId, organismPart)}"/>

        <c:set var="cellColour"
               value="${colourGradient.getGradientColour(roundedExpressionLevel, roundedMinExpressionLevel, roundedMaxExpressionLevel)}"/>

        <display:column title="<div data-organism-part='${organismPart}' class='rotate_text'>${organismPart}</div>"
                        headerClass='rotated_cell'
                        style="background-color:${cellColour};color:${cellColour};font-size:1px">
            <div style="font-size:1px" data-organism-part="${!empty roundedExpressionLevel ? organismPart :''}" data-color="${cellColour}" >
                <fmt:formatNumber type="number" maxFractionDigits="${roundedExpressionLevel >= 1 ? 0 : 1}" value="${roundedExpressionLevel}" groupingUsed="false" />
            </div>
        </display:column>

    </c:forEach>

</display:table>

