package org.oa.taras.store.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oa.taras.store.dao.DaoFacade;
import org.oa.taras.store.data.Model;

/**
 * Servlet implementation class ModelServiceServletPost
 */
@WebServlet("/ModelServiceServletPost")
public class ModelServiceServletPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String PARAMETER_METHOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_CODE = "code";
    private static final String PARAMETER_DESCRIPTION = "description";

    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModelServiceServletPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        final String queryMethod = request.getParameter(PARAMETER_METHOD);

        System.out.println("models method  " + queryMethod);
        response.setContentType("application/json;charset=UTF-8");

        DaoFacade facade = new DaoFacade(getServletContext());

        if (CREATE_METHOD.equalsIgnoreCase(queryMethod)) {
            String nameString = request.getParameter(PARAMETER_NAME);
            String codeString = request.getParameter(PARAMETER_CODE);
            String modelDescription = request.getParameter(PARAMETER_DESCRIPTION);
			Model model = new Model(0, nameString, codeString,modelDescription);
			
            boolean created = facade.getModelDao().add(model);
            try (PrintWriter out = response.getWriter()) {
                if (created) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed create model\"}");
                }
            }
        } else if (UPDATE_METHOD.equalsIgnoreCase(queryMethod)) {
        	 String idString=request.getParameter(PARAMETER_ID);
        	 long id=Long.parseLong(idString);
        	 String nameString = request.getParameter(PARAMETER_NAME);
             String codeString = request.getParameter(PARAMETER_CODE);
             String modelDescription = request.getParameter(PARAMETER_DESCRIPTION);
 			Model model = new Model(id, nameString, codeString,modelDescription);   
 			boolean updated = facade.getModelDao().update(model);
            try (PrintWriter out = response.getWriter()) {
                if (updated) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed update model\"}");
                }
            }
        } else if (DELETE_METHOD.equalsIgnoreCase(queryMethod)) {
            String idString = request.getParameter(PARAMETER_ID);
            long id = Long.parseLong(idString);
            boolean deleted = facade.getModelDao().delete(id);
            try (PrintWriter out = response.getWriter()) {
                if (deleted) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"response\":\"Deleted by post\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Failed delete model\"}");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Not found method\"}");
            }
        }
	}

}
