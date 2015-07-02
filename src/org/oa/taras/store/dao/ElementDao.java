package org.oa.taras.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.oa.taras.store.data.Element;

public class ElementDao implements AbstractDao<Element> {
	private static final String TABLE_NAME = "elements";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_NAME = "name";
	private static final String COLUMN_CODE = "year";
	private static final String COLUMN_QUANTITY = "quantity";
	private static final String COLUMN_DESCRIPTION = "elementDescription";
	private Statement statement;
	private Connection connection;

	public ElementDao(Statement statement, Connection connection) {
		this.connection = connection;
		this.statement = statement;
		try {
			statement
					.execute("CREATE TABLE IF NOT EXISTS "
							+ TABLE_NAME
							+ " ("
							+ "`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,"
							+ " `name` varchar(100) DEFAULT NULL,"
							+ "`year` varchar(11) DEFAULT NULL,"
							+ " `quantity` int(11) DEFAULT NULL,"
							+ " `description` varchar(100) DEFAULT NULL,"
							+ " PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public List<Element> loadAll() {
		List<Element> models = new ArrayList<>();

		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String year = resultSet.getString("year");
				int quantity = resultSet.getInt("quantity");
				String elementDescription = resultSet.getString("description");
				models.add(new Element(id, name, year, quantity, elementDescription));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return models;
	}

	@Override
	public Element findById(long objectId) {
		Element model = null;
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=" + objectId + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String year = resultSet.getString("year");
				int quantity = resultSet.getInt("quantity");
				String elementDescription = resultSet.getString("description");
				model = new Element(id, name, year, quantity, elementDescription);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}

	@Override
	public boolean delete(long id) {
		try {
			statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean update(Element changed) {
		try {
			statement.executeUpdate("UPDATE " + TABLE_NAME + " SET name='" + changed.getName() + "',year='"
					+ changed.getYear() + "',quantity='" + changed.getQuantity() + "',description='"
					+ changed.getElementDescription() + "' WHERE id=" + changed.getId() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean add(Element item) {
		try {
			statement.executeUpdate("INSERT INTO " + TABLE_NAME + " (name, year,quantity,description)" + " VALUES ('"
					+ item.getName() + "','" + item.getYear() + "','" + item.getQuantity() + "','"
					+ item.getElementDescription() + "')");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<Element> collection) {

		String sqlQuery = "INSERT INTO " + TABLE_NAME + " (name, year,quantity,description)" + " VALUES ( ? , ? ,?,?)";
		try (PreparedStatement statement = connection.prepareStatement(sqlQuery)){
			connection.setAutoCommit(false);
			for (Element elem : collection) {
				statement.setString(1, elem.getName());
				statement.setString(2, elem.getYear());
				statement.setInt(3, elem.getQuantity());
				statement.setString(4, elem.getElementDescription());
				statement.executeUpdate();
			}
			connection.commit();
			connection.setAutoCommit(true);
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

}
