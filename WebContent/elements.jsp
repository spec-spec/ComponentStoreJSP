<%@page import="org.oa.tp.dao.DaoFacade" %>
<%@page import="java.util.List" %>
<%@ page import="org.oa.tp.data.Element" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Elements</title>
</head>
<body>
<%
    DaoFacade facade = new DaoFacade(request.getServletContext());
    List<Element> items = facade.getElementDao().loadAll();
%>

<h1>Elements</h1>
<table border=1>
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>YEAR</th>
        <th>Quantity</th>
        <th>Description</th>
        <th>UPDATE</th>
        <th>DELETE</th>
    </tr>
    
     
    <% for (Element item : items) {%>
    <tr>
        <td>
            <%= item.getId()%>
        </td>
        <td>
            <%= item.getName()%>
        </td>
        <td>
            <%= item.getYear()%>
        </td>
        <td>
            <%= item.getQuantity()%>
        </td>
        <td>
            <%= item.getElementDescription()%>
        </td>
        <td>
            <a href="edit_element.jsp?id=<%= item.getId()%>">UPDATE</a>
        </td>
        <td>
            <form action="elements" method="POST">
            <input type="hidden" name="method" value="delete"/>
            <input type="hidden" name="id" value="<%=item.getId()%>">
            <input type="submit" value="Delete"></form>
        </td>
    </tr>

    <% }%>
</table>
<br/>
<a href="edit_element.jsp"> CREATE </a>

</body>
</html>
