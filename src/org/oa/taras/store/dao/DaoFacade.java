package org.oa.taras.store.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;

import org.oa.taras.store.data.Element;
import org.oa.taras.store.data.Model;
import org.ua.taras.store.utils.L;

public class DaoFacade {

	private final String DATABASE_NAME = "component_store";
	private final String URI_CONNECTION = "jdbc:mysql://82.117.234.24:3306/" + DATABASE_NAME;
	private final String DB_LOGIN = "oxigen";
	private final String DB_PASSWORD = "toor";
	private Connection connection;
	private Statement statement;

	public DaoFacade(ServletContext context) {
		try {
			downloadJDBCDriver();
			connection = DriverManager.getConnection(URI_CONNECTION, DB_LOGIN, DB_PASSWORD);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (connection == null) {
			System.exit(1);
		}

	}

	private void downloadJDBCDriver() {
		try {
			L.debug("driver download");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("exception loading DB driver");
		}
	}

	public AbstractDao<Model> getModelDao() {
		return new ModelDao(statement, connection);
	}

	public AbstractDao<Element> getElementDao() {
		return new ElementDao(statement, connection);
	}

	public void closeSqlConnection() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
