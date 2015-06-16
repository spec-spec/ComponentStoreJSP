<%@page import="org.oa.tp.dao.DaoFacade" %>
<%@ page import="org.oa.tp.data.Element" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit element</title>
</head>
<body>
<h1>Edit element</h1>

<form action="elements" method="POST">
    <%
        String id = request.getParameter("id");
        if (id == null) {%>

    <input type="hidden" name="method" value="create"/>
    Name:
    <input type="text" name="name"><br><br>
    Year:
    <input type="text" name="year"><br><br>
     Quantity:
    <input type="text" name="quantity"><br><br>
     Description:
    <input type="text" name="description"><br><br>
    <% } else {
        DaoFacade daoFacade = new DaoFacade(request.getServletContext());
        Element elem = daoFacade.getElementDao().findById(Long.parseLong(id));
    %>
    <input type="hidden" name="method" value="update"/>
    
    <input type="hidden" name="id" value="<%=elem.getId()%>"><br><br>
    Name:
    <input type="text" name="name" value="<%=elem.getName()%>"><br><br>
    Year:
    <input type="text" name="year" value="<%=elem.getYear()%>"><br><br>
    Quantity:
    <input type="text" name="quantity" value="<%=elem.getQuantity()%>"><br><br>
    Description:
    <input type="text" name="description" value="<%=elem.getElementDescription()%>"><br><br>
    <% } %>
    <input type="submit" value="Save">
</form>
</body>
</html>
