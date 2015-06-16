/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.oa.tp.servlets;

import com.google.gson.Gson;
import org.oa.tp.dao.DaoFacade;
import org.oa.tp.data.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Taras
 */
public class ElementServiceServlet extends HttpServlet {

    private static final String PARAMETER_METHOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_QUANTITY = "quantity";
    private static final String PARAMETER_DESCRIPTION = "description";

    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryMethod = request.getParameter(PARAMETER_METHOD);

        System.out.println("method " + queryMethod);
        response.setContentType("application/json;charset=UTF-8");

        DaoFacade facade = new DaoFacade(getServletContext());
        if (GET_ALL_METHOD.equalsIgnoreCase(queryMethod)) {
            List<Element> elements = facade.getElementDao().loadAll();
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(elements, out);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (DELETE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString = request.getParameter(PARAMETER_ID);
            long id = Long.parseLong(idString);
            boolean deleted = facade.getElementDao().delete(id);
            try (PrintWriter out = response.getWriter()) {
                if (deleted) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed delete album\"}");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Not found method\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String queryMethod = request.getParameter(PARAMETER_METHOD);

        System.out.println("method " + queryMethod);
        response.setContentType("application/json;charset=UTF-8");

        DaoFacade facade = new DaoFacade(getServletContext());

        if (CREATE_METHOD.equalsIgnoreCase(queryMethod)) {
            String nameString = request.getParameter(PARAMETER_NAME);
            String yearString = request.getParameter(PARAMETER_YEAR);
            String quantityString=request.getParameter(PARAMETER_QUANTITY);
            int quantity = Integer.parseInt(quantityString);
            String elementDescription = request.getParameter(PARAMETER_DESCRIPTION);
			Element elem = new Element(0, nameString, yearString, quantity,elementDescription);
			
            boolean created = facade.getElementDao().add(elem);
            try (PrintWriter out = response.getWriter()) {
                if (created) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed create album\"}");
                }
            }
        } else if (UPDATE_METHOD.equalsIgnoreCase(queryMethod)) {
        	 String idString=request.getParameter(PARAMETER_ID);
        	 System.out.println("id string "+idString);
             long id=Long.parseLong(idString);
        	String nameString = request.getParameter(PARAMETER_NAME);
            String yearString = request.getParameter(PARAMETER_YEAR);
            String quantityString=request.getParameter(PARAMETER_QUANTITY);
            int quantity = Integer.parseInt(quantityString);
            String elementDescription = request.getParameter(PARAMETER_DESCRIPTION);
			Element elem = new Element(id, nameString, yearString, quantity,elementDescription);
            boolean updated = facade.getElementDao().update(elem);
            try (PrintWriter out = response.getWriter()) {
                if (updated) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed update album\"}");
                }
            }
        } else if (DELETE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString = request.getParameter(PARAMETER_ID);
            long id = Long.parseLong(idString);
            boolean deleted = facade.getElementDao().delete(id);
            try (PrintWriter out = response.getWriter()) {
                if (deleted) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Deleted by post\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed delete album\"}");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Not found method\"}");
            }
        }
       
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
