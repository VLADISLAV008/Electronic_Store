<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<!-- head -->
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<div>  <!-- content -->
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>Email:
            <input type="text" name="email"><br/>
        </label>
        <label>Password:
            <input type="password" name="pass"><br/>
        </label>
        <button type="submit">Login</button>
    </form>

    <c:if test="${not empty requestScope.message}">
        <div>
            <h1>${requestScope.message}</h1>
        </div>
    </c:if>
</div>


</body>
</html>
