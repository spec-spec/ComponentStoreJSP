package org.oa.tp.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;

import org.oa.tp.data.Element;
import org.oa.tp.data.Model;


public class DaoFacade {

	private final String DATABASE_NAME="component_store";
	private final String URI_CONNECTION = "jdbc:mysql://82.117.234.24:3306/"+DATABASE_NAME;
	private Connection connection;
	private Statement statement;
	private ModelDao modelDao;
	private ElementDao elementDao;
//	= new GenreDao(statement,connection);
	


	public DaoFacade(ServletContext context) {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("exception loading DB driver");
			}
        connection = DriverManager.getConnection(URI_CONNECTION,"oxigen","toor");
		} catch (SQLException e) {
				e.printStackTrace();
			}
		if(connection==null){
			System.exit(1);
		}
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		elementDao = new ElementDao(statement, connection);
		modelDao = new ModelDao(statement, connection);
		
	}
	public AbstractDao<Model> getModelDao() {
		return modelDao;
	}
	public AbstractDao<Element> getElementDao() {
		return elementDao;
	}
	public void closeSqlConnection(){
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(connection!= null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
