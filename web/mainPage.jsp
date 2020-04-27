<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">

<!-- head -->
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<table>
    <tr>
        <td>
            <!-- header -->
            <%@ include file="/WEB-INF/jspf/header.jspf" %>
        </td>
    </tr>
    <tr>
        <td bgcolor="#f0f8ff" valign="top">
            <!-- categories table -->
            <%@ include file="/WEB-INF/jspf/categories.jspf" %>
        </td>
        <td bgcolor="#f0ffff">
            <!-- menu bar -->
            <%@ include file="/WEB-INF/jspf/menuBar.jspf" %>

            <c:if test="${empty sessionScope.content}">
                <!-- products table -->
                <%@ include file="/WEB-INF/jspf/products.jspf" %>
            </c:if>

            <c:if test="${not empty sessionScope.content}">
                <!-- content -->
<%--                <%@ include file="/WEB-INF/jsp/login.jsp" %>--%>
<%--                <jsp:include page="${sessionScope.content}" />--%>
            </c:if>
        </td>
    </tr>
</table>

</body>
</html>

