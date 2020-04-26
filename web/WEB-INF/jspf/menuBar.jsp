<%--===========================================================================
Menu bar.
===========================================================================--%>

<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<% request.getSession(); %>
<div>
    <table border="1">
        <tr>
            <td>
                Total products: ${sessionScope.products.size()}
            </td>
            <td>
                <div style="vertical-align: middle">
                    <div style="float: left;">Sort:</div>
                    <div style="float: right;">
                        <form action="${pageContext.request.contextPath}/mainPage" method="get">
                            <select id="method" name="sortMethod" onchange="this.form.submit()">
                                <c:forEach var="criterion" items="${sessionScope.criteriaSortingProducts.list}">
                                    <option value="${criterion.name}"
                                            <c:if test="${criterion.selected}">
                                                selected
                                            </c:if>
                                            <c:if test="${criterion.name == \"null\"}">
                                                disabled hidden
                                            </c:if>
                                    >${criterion.webText}</option>
                                </c:forEach>
                            </select>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </table>

</div>
