package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Firstname VARCHAR(20), " +
                        "Lastname VARCHAR(20), " +
                        "Age INT)");
                connection.commit();
                System.out.println("Table is created");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("Table is not created");
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Table is not created");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS Users");
                connection.commit();
                System.out.println("Table is dropped");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("Table is not dropped");
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Table is not dropped");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (Firstname, LastName, Age) VALUES(?, ?, ?)";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("User with name - " + name + " add to DB");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("User is not add to DB");
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("User is not add to DB");
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE Id = ?";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("User with ID - " + id + " deleted from DB");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("User is not deleted from DB");
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("User is not deleted from DB");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String query = "SELECT Id, Firstname, LastName, Age FROM users";
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("Id"));
                    user.setName(resultSet.getString("Firstname"));
                    user.setLastName(resultSet.getString("LastName"));
                    user.setAge(resultSet.getByte("Age"));
                    usersList.add(user);
                }
                System.out.println(usersList);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("Users is not import to list");
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
                return usersList;
            }
        } catch (SQLException e) {
            System.out.println("Users is not import to list");
            e.printStackTrace();
            return null;
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM users");
                connection.commit();
                System.out.println("Table is cleared");
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                System.out.println("Table is not cleared");
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Table is not created");
            e.printStackTrace();
        }
    }
}
