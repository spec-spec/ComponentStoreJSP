package org.oa.taras.store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.oa.taras.store.data.Element;
import org.oa.taras.store.data.Model;

class ModelDao implements AbstractDao<Model> {

    private static final String TABLE_NAME = "model";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_MODEL_SET_ID = "model_set_id";
    private Statement statement;
    private Connection connection;

    public ModelDao(Statement statement, Connection connection) {
        this.statement = statement;
        this.connection = connection;
        try {
			statement.execute("CREATE TABLE IF NOT EXISTS " +TABLE_NAME+ " ("+
		"`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,"+
		" `name` varchar(100) DEFAULT NULL,"+
		"`code` varchar(11) DEFAULT NULL,"+
		" `description` varchar(100) DEFAULT NULL,"+
		" PRIMARY KEY (`id`), UNIQUE KEY `id` (`id`)) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<Model> loadAll() {
        List<Model> models = new ArrayList<>();

        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultSet.next()) {
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                String code = resultSet.getString(COLUMN_CODE);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
                models.add(new Model(id, name,code, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return models;
    }

    @Override
    public Model findById(long objectId) {
       Model model = null;
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + objectId
							+ ";");
			if (resultSet.next()) {
				int id = resultSet.getInt(COLUMN_ID);
				String name = resultSet.getString(COLUMN_NAME);
				String code = resultSet.getString(COLUMN_CODE);
                String description = resultSet.getString(COLUMN_DESCRIPTION);
				 model = new Model(id, name,code, description);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  model;
    }

    @Override
    public boolean delete(long id) {
    	try {
			statement.executeUpdate("DELETE FROM "+TABLE_NAME+" WHERE id=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }

    @Override
    public boolean update(Model changed) {
    	try {
			statement.executeUpdate("UPDATE "+TABLE_NAME+" SET name='"
					+ changed.getName() + "',code='" + changed.getCode()
					+ "',description='" + changed.getDescription()
					+ "' WHERE id=" + changed.getId() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }

    @Override
    public boolean add(Model item) {
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + " " + " (name, code,description)"
                    + " VALUES ('" + item.getName()+"','"+item.getCode()+"','"
            		+item.getDescription()+ "')");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Model> collection) {
		String sqlQuery = "INSERT INTO "+TABLE_NAME+ " (name, code,description)"
				+ " VALUES ( ? , ? ,?)";
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			for (Model model : collection) {
				statement.setString(1, model.getName());
				statement.setString(2, model.getCode());
				statement.setString(3, model.getDescription());
//				statement.setString(4, model.getModel_set_name());
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
