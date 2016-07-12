<%--@elvariable id="exceptionMessage" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="gxaSection">
    <div class="gxaError">
        <h5>The query is not well formed</h5>
        <c:if test="${not empty exceptionMessage}">
            <p>${exceptionMessage}</p>
        </c:if>
        <h5>Please fix your query and try again.</h5>
    </div>
</section>

<section class="gxaSection">
    <p><a style="font-weight: bold" href="/gxa">Go to Expression Atlas home page</a></p>
</section>
