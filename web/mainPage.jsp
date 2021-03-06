<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html lang="en">

<!-- head -->
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<c:if test="${empty sessionScope.content or empty requestScope.categories}">
    <c:redirect url="/mainPage"/>
</c:if>

<table cellspacing="0" width="100%">
    <tr>
        <td colspan="2">
            <!-- header -->
            <%@ include file="/WEB-INF/jspf/header.jspf" %>
        </td>
        <td width="100%"></td>
    </tr>
    <tr>
        <td bgcolor="#f0f8ff" valign="top" width="20%">
            <!-- categories table -->
            <%@ include file="/WEB-INF/jspf/categories.jspf" %>
        </td>
        <td bgcolor="#f0ffff" valign="center" width="80%">
            <jsp:useBean id="Content" scope="application" class="controller.constants.Content"/>

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

            <c:if test="${sessionScope.content.equals(Content.placeOrderResultContent)}">
                <!-- result of place an order content -->
                <%@ include file="/WEB-INF/jspf/placeOrderResult.jspf" %>
            </c:if>

            <c:if test="${sessionScope.content.equals(Content.PROFILE)}">
                <!-- profile content -->
                <%@ include file="/WEB-INF/jspf/profile.jspf" %>
            </c:if>

            <c:if test="${sessionScope.content.equals(Content.adminControl)}">
                <!-- admin content -->
                <%@ include file="/WEB-INF/jspf/admin/adminControl.jspf" %>
            </c:if>
        </td>
    </tr>
</table>

</body>
</html>
