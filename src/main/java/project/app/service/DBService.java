package project.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import project.app.model.Account;
import project.app.model.User;
import project.app.model.UserSession;

public class DBService {
    private static String error;

    private static Connection connectToDB() throws ClassNotFoundException, SQLException {
        Connection connection;
        String URL = "jdbc:mysql://localhost:3306/app?useTimezone=true&serverTimezone=UTC";
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(URL, "root", "password");

        return connection;
    }

    public static boolean registerUser(User user) throws SQLException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            if(connection != null) {
                statement = connection.createStatement();
                String sql = "INSERT INTO ACCOUNTS(username, password, first_name, last_name, email, contact_number)\n" +
                    "VALUES('"+user.getUsername()+"','"+
                    user.getPassword()+"','"+
                    user.getFirstName()+"','"+
                    user.getLastName()+"','"+
                    user.getEmail()+"','"+
                    user.getContactNumber()+"')";
                statement.executeUpdate(sql);

                return true;
            } else {
                error = "DB connection failed";
                return false;
            }
         } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return false;  
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static UserSession verifyCredentials(Account account) throws SQLException  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UserSession userSessionData = null;

        try {
            connection = connectToDB();

            if (connection != null) {
                String sql = "SELECT id, first_name, role FROM ACCOUNTS\n" + 
                    "WHERE username = '" + account.getUsername() + "'\n" +
                    "AND password = '" + account.getPassword() + "'";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                
                
                while(rs.next()){  
                    userSessionData = new UserSession();
                    userSessionData.setId(rs.getInt(1)); 
                    userSessionData.setFirstName(rs.getString(2));
                    userSessionData.setRole(rs.getString(3));
                }

                return userSessionData;
            } else {
                error = "DB connection failed";
                return null;
            }
         } catch (Exception e) {
            error = e.toString();
            return null;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }


    public static String getError() {
        return error;
    }
}
