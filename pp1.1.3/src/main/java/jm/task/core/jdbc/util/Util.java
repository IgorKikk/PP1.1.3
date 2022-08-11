package jm.task.core.jdbc.util;


import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    // реализуйте настройку соеденения с БД
    private final static String url = "jdbc:mysql://localhost/Usersdb";
    private final static String username = "root";
    private final static String password = "!2345qweASD";
    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(url, username, password);
        } catch(SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            System.out.println("Connection failed...");
            return null;
        }
    }
}
