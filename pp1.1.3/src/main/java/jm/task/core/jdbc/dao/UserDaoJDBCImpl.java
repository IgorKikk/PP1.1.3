package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "Firstname VARCHAR(20), " +
                    "Lastname VARCHAR(20), " +
                    "Age INT)");
            try {
                connection.commit();
                System.out.println("Table is created");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Table is not created");
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            try {
                connection.commit();
                System.out.println("Table is dropped");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Table is not dropped");
                e.printStackTrace();
            }
        }
    }
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String query = "INSERT INTO users (Firstname, LastName, Age) VALUES(?, ?, ?)";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            try {
                connection.commit();
                System.out.println("User with name - " + name + " add to DB");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("User is not add to DB");
                e.printStackTrace();
            }
        }
    }
    public void removeUserById(long id) throws SQLException {
        String query = "DELETE FROM users WHERE Id = ?";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            try {
                connection.commit();
                System.out.println("User with ID - " + id + " deleted from DB");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("User is not deleted from DB");
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> usersList = new ArrayList<>();
        String query = "SELECT Id, Firstname, LastName, Age FROM users";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Firstname"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));
                usersList.add(user);
            }
            try {
                connection.commit();
                System.out.println(usersList);
                return usersList;
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Users is not import to list");
                e.printStackTrace();
                return null;
            }
        }
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM users");
            try {
                connection.commit();
                System.out.println("Table is cleared");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Table is not cleared");
                e.printStackTrace();
            }
        }
    }
}
