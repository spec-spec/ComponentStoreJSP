<%@page import="org.oa.taras.store.dao.DaoFacade" %>
<%@page import="org.oa.taras.store.data.Model" %>
<%@page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Models</title>
</head>
<body>
<%
    DaoFacade facade = new DaoFacade(request.getServletContext());
    List<Model> models = facade.getModelDao().loadAll();
%>

<h1>Models</h1>
<table border=2>
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>CODE</th>
        <th>Description</th>
        <th>UPDATE</th>
        <th>DELETE</th>
    </tr>
    <% for (Model model : models) {%>
    <tr>
        <td>
            <%= model.getId()%>
        </td>
        <td>
            <%= model.getName()%>
        </td>
        <td>
            <%= model.getCode()%>
        </td>
        <td>
            <%= model.getDescription()%>
        </td>
        <td>
            
            <a href="edit_model.jsp?id=<%= model.getId()%>">UPDATE</a>
        </td>
        <td>
            <form action="models" method="POST">
            <input type="hidden" name="method" value="delete"/>
            <input type="hidden" name="id" value="<%=model.getId()%>">
            <input type="submit" value="Delete"></form>
        </td>
    </tr>

    <% }%>
</table>
<br/>
<a href="edit_model.jsp"> CREATE </a>
<br/>
<a href="elements.jsp"> Elements </a>

</body>
</html>
