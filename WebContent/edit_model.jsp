<%@page import="org.oa.taras.store.dao.DaoFacade" %>
<%@page import="org.oa.taras.store.data.Model" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit model</title>
</head>
<body>
<h1>Edit model</h1>

<form action="models" method="POST">
    <%
        String id = request.getParameter("id");
        if (id == null) {%>

    <input type="hidden" name="method" value="create"/>
    Name:
    <input type="text" name="name"><br><br>
     Code:
    <input type="text" name="code"><br><br>
     Description:
     <input type="text" name="description"><br><br>
    <% } else {
        DaoFacade daoFacade = new DaoFacade(request.getServletContext());
        Model model = daoFacade.getModelDao().findById(Long.parseLong(id));
    %>
    <input type="hidden" name="method" value="update"/>
    <input type="hidden" name="id" value="<%=model.getId()%>"><br><br>
    Name:
    <input type="text" name="name" value="<%=model.getName()%>"><br><br>
    Code:
    <input type="text" name="code" value="<%=model.getCode()%>"><br><br>
    Description:
    <input type="text" name="description" value="<%=model.getDescription()%>"><br><br>
    <% } %>
    <input type="submit" value="Save">
</form>
</body>
</html>
