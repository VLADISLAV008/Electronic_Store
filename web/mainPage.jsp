<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">

<!-- head -->
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<c:if test="${empty sessionScope.content}">
    <c:redirect url="/mainPage"/>
</c:if>

<table cellspacing="0">
    <tr>
        <td colspan="2">
            <!-- header -->
            <%@ include file="/WEB-INF/jspf/header.jspf" %>
        </td>
    </tr>
    <tr>
        <td bgcolor="#f0f8ff" valign="top">
            <!-- categories table -->
            <%@ include file="/WEB-INF/jspf/categories.jspf" %>
        </td>
        <td bgcolor="#f0ffff" valign="left" width="100%">
            <jsp:useBean id="Content" scope="application" class="ua.nure.sharmenko.finaltask.constants.Content"/>

            <c:if test="${sessionScope.content.equals(Content.LOGIN)}">
                <!-- login content -->
                <%@ include file="/WEB-INF/jspf/login.jspf" %>
            </c:if>

            <c:if test="${sessionScope.content.equals(Content.productsContent)}">
                <!-- products content -->
                <%@ include file="/WEB-INF/jspf/productsContent.jspf" %>
            </c:if>

            <c:if test="${sessionScope.content.equals(Content.signUp)}">
                <!-- sign up content -->
                <%@ include file="/WEB-INF/jspf/signUp.jspf" %>
            </c:if>

            <c:if test="${sessionScope.content.equals(Content.BASKET)}">
                <!-- basket content -->
                <%@ include file="/WEB-INF/jspf/basket.jspf" %>
            </c:if>
        </td>
    </tr>
</table>

</body>
</html>

