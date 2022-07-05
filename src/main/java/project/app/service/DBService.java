package project.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

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

    //testBed Functionality
    public static boolean addTestBed(String multiselect, File img) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement statement = null;
        FileInputStream inputStream = null;

        try {
            connection = connectToDB();
            System.out.println("YOU'RE IN HERE maybe");
            if(connection != null) {
                System.out.println("YOU'RE IN HERE THOUGH");
                inputStream = new FileInputStream(img);
                
                //to trigger, rename img to image
                String sql = "INSERT INTO TESTBED(multipleselect, img)\n" +
                    "VALUES(?, ?)";
                
                statement = connection.prepareStatement(sql);
                statement.setString(1, multiselect);
                statement.setBinaryStream(2, inputStream, (int) img.length());
                statement.executeUpdate();
                
                return true;
            } else {
                System.out.println("YOU'RE ACTUALLY IN HERE");
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
            if (inputStream != null) try { inputStream.close(); } catch (IOException ignore) {}
         }
    }

    public static String getBase64CoverImage(int id) throws SQLException  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blob = null;
        byte[] cover = null;

        try {
            connection = connectToDB();

            if (connection != null) {
                String sql = "SELECT img FROM testbed\n" + 
                    "WHERE id = '" + id + "'";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                
                
                while(rs.next()){  
                    // System.out.println("This is from DBSERVICE: " + rs.getString(1));
                    blob = rs.getBlob(1);
                    cover = blob.getBytes(1l, (int)blob.length());
                }

                return new String(Base64.getEncoder().encode(cover));
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
}
