package project.app.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import project.app.model.Account;
import project.app.model.BookCopy;
import project.app.model.BookEntry;
import project.app.model.CheckoutRecord;
import project.app.model.CheckoutRecordInnerJoinBookEntryLeftJoinAccount;
import project.app.model.OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount;
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

    //catalog functions
    public static List<BookEntry> getBookEntriesForCatalog(String genre) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blobCover = null;
        byte[] byteCover = null;
        List<BookEntry> bookEntries = new ArrayList<BookEntry>();

        try {
            connection = connectToDB();

            if(connection != null) {
                String sql = "SELECT ISBN, title, authors, cover\n" +
                    "FROM book_entries\n" +
                    "WHERE genre = ?\n" + 
                    "ORDER BY id DESC\n" +
                    "LIMIT 3";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, genre);
                ResultSet rs = preparedStatement.executeQuery();
                                
                while(rs.next()){  
                    BookEntry bookEntry = new BookEntry();
                    bookEntry.setISBN(rs.getString(1));
                    bookEntry.setTitle(rs.getString(2));
                    bookEntry.setAuthors(rs.getString(3).split(","));

                    blobCover = rs.getBlob(4);
                    byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                    bookEntry.setCover(new String(Base64.getEncoder().encode(byteCover)));

                    bookEntries.add(bookEntry);
                }

                return bookEntries;
            } else {
                error = "DB connection failed";
                return null;
            }
         } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return null;  
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //general
    public static BookEntry getBookEntryByISBN(String ISBN) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BookEntry bookEntry = null;
        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            String sql = "SELECT id, title, authors, cover, isbn, page_count, publisher, published_date, genre\n" +
                "FROM book_entries\n" +
                "WHERE isbn = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ISBN);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                bookEntry = new BookEntry();
                bookEntry.setDbId(rs.getInt(1));
                bookEntry.setTitle(rs.getString(2));
                bookEntry.setAuthors(rs.getString(3).split(",")); 
                
                blobCover = rs.getBlob(4);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                bookEntry.setCover(new String(Base64.getEncoder().encode(byteCover)));

                bookEntry.setISBN(rs.getString(5));
                bookEntry.setPageCount(rs.getInt(6));
                bookEntry.setPublisher(rs.getString(7));

                Date date = new Date(rs.getDate(8).getTime());
                DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
                bookEntry.setPublishedDate(dateFormat.format(date));

                bookEntry.setGenre(rs.getString(9));

                System.out.println("BOOK ENTRY = " + bookEntry.toString());
            }

            return bookEntry;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static int getAvailableCopiesCount(int bookEntryId) throws SQLException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            if(connection != null) {
                statement = connection.createStatement();
                String sql = "SELECT COUNT(*) AS recordCount\n" + 
                    "FROM BOOK_COPIES\n" + 
                    "WHERE book_entry_id = '" + bookEntryId + "' " +
                    "AND checked_out = '0'";

                ResultSet rs = statement.executeQuery(sql);

                rs.next();

                System.out.println("SQL QUERY = " + sql);

                return rs.getInt("recordCount");
            } else {
                error = "DB connection failed";
                return -1;
            }
         } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return -1;  
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static List<BookEntry> searchBookEntriesByTitle(String titleQuery) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blobCover = null;
        byte[] byteCover = null;
        List<BookEntry> bookEntries = new ArrayList<BookEntry>();

        try {
            connection = connectToDB();

            if(connection != null) {
                String sql = "SELECT ISBN, title, authors, cover\n" +
                    "FROM book_entries\n" +
                    "WHERE title LIKE '%" + titleQuery + "%'";

                preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                                
                while(rs.next()){  
                    BookEntry bookEntry = new BookEntry();
                    bookEntry.setISBN(rs.getString(1));
                    bookEntry.setTitle(rs.getString(2));
                    bookEntry.setAuthors(rs.getString(3).split(","));

                    blobCover = rs.getBlob(4);
                    byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                    bookEntry.setCover(new String(Base64.getEncoder().encode(byteCover)));

                    bookEntries.add(bookEntry);
                    System.out.println("bookentry = " + bookEntry.toString());
                }

                return bookEntries;
            } else {
                error = "DB connection failed";
                return null;
            }
         } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return null;  
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //user functions
    public static User getUserById(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User user = null;
        
        try {
            connection = connectToDB();

            String sql = "SELECT first_name, last_name, email, contact_number\n" + 
            "FROM accounts\n" +
            "WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                user = new User();
                user.setFirstName(rs.getString(1));
                user.setLastName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setContactNumber(rs.getString(4));
            }

            return user;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static void editUser(int id, User user) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            statement = connection.createStatement();
            String sql = "UPDATE accounts\n" + 
                "SET first_name = '" + user.getFirstName() + "', " +
                "last_name = '" + user.getLastName() + "', " +
                "email = '" + user.getEmail() + "', " +
                "contact_number = '" + user.getContactNumber() + "'\n" +
                "WHERE ( id = '" + id +"')";
            
            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //checkout functions
    public static boolean addCheckoutRequest(int userId, int bookEntryId) throws SQLException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            if(connection != null) {
                statement = connection.createStatement();
                String sql = "INSERT INTO online_checkout_requests(requester_id, book_to_checkout_id)\n" +
                    "VALUES('"+userId+"','"+
                    bookEntryId+"')";
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

    public static boolean hasPendingCheckoutRequestForBook(int userId, int bookEntryId) throws SQLException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            if(connection != null) {
                statement = connection.createStatement();
                String sql = "SELECT COUNT(*) AS recordCount\n" + 
                    "FROM online_checkout_requests\n" + 
                    "WHERE requester_id = '" + userId + "' " +
                    "AND book_to_checkout_id = '" + bookEntryId + "' " +
                    "AND status = 'Pending'";
                
                System.out.println("SQL QUERY = " + sql);
                ResultSet rs = statement.executeQuery(sql);

                rs.next();

                int count = rs.getInt("recordCount");

                return count < 1 ? false:true;
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

    // public static boolean addCheckoutRecord(CheckoutRecord checkoutRecord) throws SQLException {
    //     error = "";
    //     Connection connection = null;
    //     Statement statement = null;

    //     try {
    //         connection = connectToDB();

    //         if(connection != null) {
    //             statement = connection.createStatement();
    //             String sql = "INSERT INTO checkout_records(book_entry_id, book_copy_id, borrower_id, online_checkout_request_id)\n" +
    //                 "VALUES('"+checkoutRecord.getBookEntryId()+"','"+
    //                 checkoutRecord.getBookCopyId()+"','"+
    //                 checkoutRecord.getBorrowerId()+"','"+
    //                 checkoutRecord.getOnlineCheckoutRequestId()+"')";
    //             statement.executeUpdate(sql);

    //             System.out.println("SQL = "+ sql);
    //             return true;
    //         } else {
    //             error = "DB connection failed";
    //             return false;
    //         }
    //      } catch (Exception e) {
    //         e.printStackTrace();
    //         error = e.toString();
    //         return false;  
    //      } finally {
    //         if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
    //         if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
    //      }
    // }

    public static List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckedOutBooksByUser(int userId) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords = new ArrayList<CheckoutRecordInnerJoinBookEntryLeftJoinAccount>();

        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            String sql = "SELECT cr.id, cr.book_entry_id, cr.book_copy_id, cr.borrower_id, cr.online_checkout_request_id, cr.checkout_date, cr.expected_return_date, cr.actual_return_date, cr.status, cr.fine, cr.fine_paid, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
            "FROM checkout_records cr\n" + 
            "INNER JOIN book_entries be\n" + 
            "ON cr.book_entry_id = be.id\n" + 
            "LEFT JOIN accounts ac\n" + 
            "ON cr.borrower_id = ac.id\n" + 
            "WHERE cr.borrower_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                CheckoutRecordInnerJoinBookEntryLeftJoinAccount checkoutRecord = new CheckoutRecordInnerJoinBookEntryLeftJoinAccount();
                checkoutRecord.setDbId(rs.getInt(1));
                checkoutRecord.setBookEntryId(2);
                checkoutRecord.setBookCopyId(rs.getInt(3));
                checkoutRecord.setBorrowerId(rs.getInt(4));
                checkoutRecord.setOnlineCheckoutRequestId(rs.getInt(5)  );
                checkoutRecord.setCheckoutDate(rs.getDate(6));
                checkoutRecord.setExpectedReturnDate(rs.getDate(7));
                checkoutRecord.setActualReturnDate(rs.getDate(8));
                checkoutRecord.setStatus(rs.getString(9));
                checkoutRecord.setFine(rs.getInt(10));
                checkoutRecord.setFinePaid(rs.getBoolean(11));
                checkoutRecord.setJoinBookEntryISBN(rs.getString(12));
                checkoutRecord.setJoinBookEntryTitle(rs.getString(13));

                blobCover = rs.getBlob(14);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                checkoutRecord.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                checkoutRecord.setJoinAccountFirstName(rs.getString(15));
                checkoutRecord.setJoinAccountLastName(rs.getString(16));

                checkoutRecords.add(checkoutRecord);
            }

            return checkoutRecords;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckoutRecordsByUser(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords = new ArrayList<CheckoutRecordInnerJoinBookEntryLeftJoinAccount>();

        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            String sql = "SELECT cr.id, cr.book_entry_id, cr.book_copy_id, cr.borrower_id, cr.online_checkout_request_id, cr.checkout_date, cr.expected_return_date, cr.actual_return_date, cr.status, cr.fine, cr.fine_paid, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
            "FROM checkout_records cr\n" + 
            "INNER JOIN book_entries be\n" + 
            "ON cr.book_entry_id = be.id\n" + 
            "LEFT JOIN accounts ac\n" + 
            "ON cr.borrower_id = ac.id\n" +
            "WHERE cr.borrower_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                CheckoutRecordInnerJoinBookEntryLeftJoinAccount checkoutRecord = new CheckoutRecordInnerJoinBookEntryLeftJoinAccount();
                checkoutRecord.setDbId(rs.getInt(1));
                checkoutRecord.setBookEntryId(2);
                checkoutRecord.setBookCopyId(rs.getInt(3));
                checkoutRecord.setBorrowerId(rs.getInt(4));
                checkoutRecord.setOnlineCheckoutRequestId(rs.getInt(5)  );
                checkoutRecord.setCheckoutDate(rs.getDate(6));
                checkoutRecord.setExpectedReturnDate(rs.getDate(7));
                checkoutRecord.setActualReturnDate(rs.getDate(8));
                checkoutRecord.setStatus(rs.getString(9));
                checkoutRecord.setFine(rs.getInt(10));
                checkoutRecord.setFinePaid(rs.getBoolean(11));
                checkoutRecord.setJoinBookEntryISBN(rs.getString(12));
                checkoutRecord.setJoinBookEntryTitle(rs.getString(13));

                blobCover = rs.getBlob(14);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                checkoutRecord.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                checkoutRecord.setJoinAccountFirstName(rs.getString(15));
                checkoutRecord.setJoinAccountLastName(rs.getString(16));

                checkoutRecords.add(checkoutRecord);
            }

            return checkoutRecords;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckoutRecords() throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords = new ArrayList<CheckoutRecordInnerJoinBookEntryLeftJoinAccount>();

        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            String sql = "SELECT cr.id, cr.book_entry_id, cr.book_copy_id, cr.borrower_id, cr.online_checkout_request_id, cr.checkout_date, cr.expected_return_date, cr.actual_return_date, cr.status, cr.fine, cr.fine_paid, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
            "FROM checkout_records cr\n" + 
            "INNER JOIN book_entries be\n" + 
            "ON cr.book_entry_id = be.id\n" + 
            "LEFT JOIN accounts ac\n" + 
            "ON cr.borrower_id = ac.id";

            preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                CheckoutRecordInnerJoinBookEntryLeftJoinAccount checkoutRecord = new CheckoutRecordInnerJoinBookEntryLeftJoinAccount();
                checkoutRecord.setDbId(rs.getInt(1));
                checkoutRecord.setBookEntryId(2);
                checkoutRecord.setBookCopyId(rs.getInt(3));
                checkoutRecord.setBorrowerId(rs.getInt(4));
                checkoutRecord.setOnlineCheckoutRequestId(rs.getInt(5)  );
                checkoutRecord.setCheckoutDate(rs.getDate(6));
                checkoutRecord.setExpectedReturnDate(rs.getDate(7));
                checkoutRecord.setActualReturnDate(rs.getDate(8));
                checkoutRecord.setStatus(rs.getString(9));
                checkoutRecord.setFine(rs.getInt(10));
                checkoutRecord.setFinePaid(rs.getBoolean(11));
                checkoutRecord.setJoinBookEntryISBN(rs.getString(12));
                checkoutRecord.setJoinBookEntryTitle(rs.getString(13));

                blobCover = rs.getBlob(14);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                checkoutRecord.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                checkoutRecord.setJoinAccountFirstName(rs.getString(15));
                checkoutRecord.setJoinAccountLastName(rs.getString(16));

                checkoutRecords.add(checkoutRecord);
            }

            return checkoutRecords;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static int addCheckoutRecordGetId(CheckoutRecord checkoutRecord) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int checkoutRecordId = -1;

        try {
            connection = connectToDB();

            if(connection != null) {
                String sql = "INSERT INTO checkout_records(book_entry_id, book_copy_id, borrower_id, online_checkout_request_id)\n" +
                "VALUES(?, ?, ?, ?)";

                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, checkoutRecord.getBookEntryId());                    
                preparedStatement.setInt(2, checkoutRecord.getBookCopyId());
                preparedStatement.setInt(3, checkoutRecord.getBorrowerId());
                preparedStatement.setInt(4, checkoutRecord.getOnlineCheckoutRequestId());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    checkoutRecordId = rs.getInt(1);
                }

                return checkoutRecordId;
            } else {
                error = "DB connection failed";
                return -1;
            }
         } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return -1;  
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static void checkinCheckoutRecord(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            statement = connection.createStatement();
            String sql = "UPDATE checkout_records\n" + 
                "SET actual_return_date = CURDATE(), " +
                "status = 'Checked In'\n" +
                "WHERE ( id = '" + id +"')";
            
            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static void checkoutBookCopy(int id, int checkoutRecordId) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            statement = connection.createStatement();
            String sql = "UPDATE book_copies\n" + 
                "SET current_checkout_record_id = " + "'" + checkoutRecordId + "', " +
                "checked_out = " + "" + true + "\n" +
                "WHERE ( id = '" + id +"')";
            
            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static void checkinBookCopy(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            statement = connection.createStatement();
            String sql = "UPDATE book_copies\n" + 
                "SET checked_out = " + "" + false + "\n" +
                "WHERE ( id = '" + id +"')";
            
            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getExpectedCheckins() throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords = new ArrayList<CheckoutRecordInnerJoinBookEntryLeftJoinAccount>();

        Blob blobCover = null;
        byte[] byteCover = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = new Date();  
        String today = formatter.format(date);

        try {
            connection = connectToDB();

            String sql = "SELECT cr.id, cr.book_entry_id, cr.book_copy_id, cr.borrower_id, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
            "FROM checkout_records cr\n" + 
            "INNER JOIN book_entries be\n" + 
            "ON cr.book_entry_id = be.id\n" + 
            "LEFT JOIN accounts ac\n" + 
            "ON cr.borrower_id = ac.id\n" + 
            "WHERE cr.expected_return_date = CURDATE()\n" +
            "AND cr.status = 'Checked Out'";

            // String sql = "SELECT id, book_copy_id, borrower_id, online_checkout_request_id, checkout_date, expected_return_date, actual_return_date, checkout_status\n" + 
            //     "FROM checkout_records";

            // String sql = "SELECT id, book_copy_id, borrower_id, online_checkout_request_id, checkout_date, expected_return_date, actual_return_date, checkout_status\n" + 
            // "FROM checkout_records\n" + 
            // "WHERE requester_id = ?";

            preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                CheckoutRecordInnerJoinBookEntryLeftJoinAccount checkoutRecord = new CheckoutRecordInnerJoinBookEntryLeftJoinAccount();
                checkoutRecord.setDbId(rs.getInt(1));
                checkoutRecord.setBookEntryId(2);
                checkoutRecord.setBookCopyId(rs.getInt(3));
                checkoutRecord.setBorrowerId(rs.getInt(4));
                checkoutRecord.setJoinBookEntryISBN(rs.getString(5));
                checkoutRecord.setJoinBookEntryTitle(rs.getString(6));

                blobCover = rs.getBlob(7);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                checkoutRecord.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                checkoutRecord.setJoinAccountFirstName(rs.getString(8));
                checkoutRecord.setJoinAccountLastName(rs.getString(9));

                checkoutRecords.add(checkoutRecord);
            }

            return checkoutRecords;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getLateExpectedCheckins() throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords = new ArrayList<CheckoutRecordInnerJoinBookEntryLeftJoinAccount>();

        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            String sql = "SELECT cr.id, cr.book_entry_id, cr.book_copy_id, cr.borrower_id, cr.expected_return_date, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
            "FROM checkout_records cr\n" + 
            "INNER JOIN book_entries be\n" + 
            "ON cr.book_entry_id = be.id\n" + 
            "LEFT JOIN accounts ac\n" + 
            "ON cr.borrower_id = ac.id\n" + 
            "WHERE cr.expected_return_date < CURDATE()\n" +
            "AND cr.status = 'Checked Out'";

            preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                CheckoutRecordInnerJoinBookEntryLeftJoinAccount checkoutRecord = new CheckoutRecordInnerJoinBookEntryLeftJoinAccount();
                checkoutRecord.setDbId(rs.getInt(1));
                checkoutRecord.setBookEntryId(2);
                checkoutRecord.setBookCopyId(rs.getInt(3));
                checkoutRecord.setBorrowerId(rs.getInt(4));
                checkoutRecord.setExpectedReturnDate(rs.getDate(5));
                checkoutRecord.setJoinBookEntryISBN(rs.getString(6));
                checkoutRecord.setJoinBookEntryTitle(rs.getString(7));

                blobCover = rs.getBlob(8);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                checkoutRecord.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                checkoutRecord.setJoinAccountFirstName(rs.getString(9));
                checkoutRecord.setJoinAccountLastName(rs.getString(10));

                checkoutRecords.add(checkoutRecord);
            }

            return checkoutRecords;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //user functions
    //general
    public static List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> getOnlineCheckoutRequestByUser(int userId) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blobCover = null;
        byte[] byteCover = null;
        List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> oCheckoutRequests = new ArrayList<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount>();

        try {
            connection = connectToDB();

            String sql = "SELECT ocr.id, ocr.book_to_checkout_id, ocr.status, ocr.request_date, ocr.status_update_date, ocr.checkout_record_id, be.id, be.isbn, be.title, be.cover\n" + 
                "FROM online_checkout_requests ocr\n" + 
                "INNER JOIN book_entries be\n" + 
                "ON ocr.book_to_checkout_id = be.id\n" + 
                "WHERE requester_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount oCheckoutRequest = new OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount();
                oCheckoutRequest.setDbId(rs.getInt(1));
                oCheckoutRequest.setBookToCheckoutId(rs.getInt(2));
                oCheckoutRequest.setStatus(rs.getString(3));
                oCheckoutRequest.setRequestDate(rs.getDate(4)); 
                oCheckoutRequest.setStatusUpdateDate(rs.getDate(5));
                oCheckoutRequest.setCheckoutRecordId(rs.getInt(6));
                oCheckoutRequest.setJoinBookEntryId(rs.getInt(7));
                oCheckoutRequest.setJoinBookEntryISBN(rs.getString(8));
                oCheckoutRequest.setJoinBookEntryTitle(rs.getString(9));
                
                blobCover = rs.getBlob(10);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                oCheckoutRequest.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                oCheckoutRequests.add(oCheckoutRequest);
            }

            return oCheckoutRequests;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> getOnlineCheckoutRequestByUserAndStatus(int userId, String status) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blobCover = null;
        byte[] byteCover = null;
        List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> oCheckoutRequests = new ArrayList<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount>();

        try {
            connection = connectToDB();

            String sql = "SELECT ocr.id, ocr.book_to_checkout_id, ocr.status, ocr.request_date, ocr.status_update_date, ocr.checkout_record_id, be.id, be.isbn, be.title, be.cover\n" + 
                "FROM online_checkout_requests ocr\n" + 
                "INNER JOIN book_entries be\n" + 
                "ON ocr.book_to_checkout_id = be.id\n" + 
                "WHERE requester_id = ?\n" + 
                "AND ocr.status = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, status);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount oCheckoutRequest = new OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount();
                oCheckoutRequest.setDbId(rs.getInt(1));
                oCheckoutRequest.setBookToCheckoutId(rs.getInt(2));
                oCheckoutRequest.setStatus(rs.getString(3));
                oCheckoutRequest.setRequestDate(rs.getDate(4)); 
                oCheckoutRequest.setStatusUpdateDate(rs.getDate(5));
                oCheckoutRequest.setCheckoutRecordId(rs.getInt(6));
                oCheckoutRequest.setJoinBookEntryId(rs.getInt(7));
                oCheckoutRequest.setJoinBookEntryISBN(rs.getString(8));
                oCheckoutRequest.setJoinBookEntryTitle(rs.getString(9));
                
                blobCover = rs.getBlob(10);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                oCheckoutRequest.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                oCheckoutRequests.add(oCheckoutRequest);
            }

            return oCheckoutRequests;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //admin
    public static List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> getCheckoutRequestByStatus(String status) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blobCover = null;
        byte[] byteCover = null;
        List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> oCheckoutRequests = new ArrayList<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount>();

        try {
            connection = connectToDB();

            String sql = "SELECT ocr.id, ocr.requester_id, ocr.book_to_checkout_id, ocr.status, ocr.request_date, ocr.status_update_date, ocr.checkout_record_id, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
                "FROM online_checkout_requests ocr\n" + 
                "INNER JOIN book_entries be\n" + 
                "ON ocr.book_to_checkout_id = be.id\n" + 
                "LEFT JOIN accounts ac\n" + 
                "ON ocr.requester_id = ac.id\n" + 
                "WHERE ocr.status = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount oCheckoutRequest = new OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount();
                oCheckoutRequest.setDbId(rs.getInt(1));
                oCheckoutRequest.setRequesterId(rs.getInt(2));
                oCheckoutRequest.setBookToCheckoutId(rs.getInt(3));
                oCheckoutRequest.setStatus(rs.getString(4));
                oCheckoutRequest.setRequestDate(rs.getDate(5)); 
                oCheckoutRequest.setStatusUpdateDate(rs.getDate(6));
                oCheckoutRequest.setCheckoutRecordId(rs.getInt(7));
                oCheckoutRequest.setJoinBookEntryISBN(rs.getString(8));
                oCheckoutRequest.setJoinBookEntryTitle(rs.getString(9));
                
                blobCover = rs.getBlob(10);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                oCheckoutRequest.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                oCheckoutRequest.setJoinAccountFirstName(rs.getString(11));
                oCheckoutRequest.setJoinAccountLastName(rs.getString(12));

                oCheckoutRequests.add(oCheckoutRequest);
            }

            return oCheckoutRequests;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //admin
    public static OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount getCheckoutRequestById(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Blob blobCover = null;
        byte[] byteCover = null;
        OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount oCheckoutRequest = null;

        try {
            connection = connectToDB();

            String sql = "SELECT ocr.id, ocr.requester_id, ocr.book_to_checkout_id, ocr.status, ocr.request_date, ocr.status_update_date, ocr.checkout_record_id, be.id, be.isbn, be.title, be.cover, ac.first_name, ac.last_name\n" + 
                "FROM online_checkout_requests ocr\n" + 
                "INNER JOIN book_entries be\n" + 
                "ON ocr.book_to_checkout_id = be.id\n" + 
                "LEFT JOIN accounts ac\n" + 
                "ON ocr.requester_id = ac.id\n" + 
                "WHERE ocr.id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                oCheckoutRequest = new OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount();
                oCheckoutRequest.setDbId(rs.getInt(1));
                oCheckoutRequest.setRequesterId(rs.getInt(2));
                oCheckoutRequest.setBookToCheckoutId(rs.getInt(3));
                oCheckoutRequest.setStatus(rs.getString(4));
                oCheckoutRequest.setRequestDate(rs.getDate(5)); 
                oCheckoutRequest.setStatusUpdateDate(rs.getDate(6));
                oCheckoutRequest.setCheckoutRecordId(rs.getInt(7));
                oCheckoutRequest.setJoinBookEntryId(rs.getInt(8));
                oCheckoutRequest.setJoinBookEntryISBN(rs.getString(9));
                oCheckoutRequest.setJoinBookEntryTitle(rs.getString(10));
                
                blobCover = rs.getBlob(11);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                oCheckoutRequest.setJoinBookEntryCover(new String(Base64.getEncoder().encode(byteCover)));

                oCheckoutRequest.setJoinAccountFirstName(rs.getString(12));
                oCheckoutRequest.setJoinAccountLastName(rs.getString(13));
            }

            return oCheckoutRequest;
        } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
    }
    
    //admin
    public static void updateCheckoutRequestStatus(int id, String status) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = new Date();  
        String today = formatter.format(date);

        System.out.println("THIS IS LITERALLY THE DATE = " + today );

        try {
            connection = connectToDB();
            statement = connection.createStatement();
            String sql = "UPDATE online_checkout_requests\n" + 
                "SET status = " + "'" + status + "', " +
                "status_update_date = " + "'" + today + "'\n" +
                "WHERE ( id = '" + id +"')";
            
            System.out.println("SQL +=" + sql);
            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static void updateCheckoutRequestCheckoutRecordId(int id, int checkoutRecordId) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = new Date();  
        String today = formatter.format(date);

        System.out.println("THIS IS LITERALLY THE DATE = " + today );

        try {
            connection = connectToDB();
            statement = connection.createStatement();
            String sql = "UPDATE online_checkout_requests\n" + 
                "SET checkout_record_id = " + "'" + checkoutRecordId + "', " +
                "status_update_date = " + "'" + today + "'\n" +
                "WHERE ( id = '" + id +"')";
            
            System.out.println("SQL +=" + sql);
            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    //admin only functions
    public static int addBookEntryGetId(BookEntry bookEntry) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String dummyString = "dummy";
        File coverFile = new File(dummyString);
        FileInputStream inputStream = null;
        int bookEntryId = -1;

        System.out.println("BOOK ENTRY = " + bookEntry.toString());;

        try {
            connection = connectToDB();

            if(connection != null) {
                System.out.println("In here 1 + bookCover = " + bookEntry.getCover());
                URL url = new URL(bookEntry.getCover());
                BufferedImage image = ImageIO.read(url);
                ImageIO.write(image, "jpg", coverFile);
                inputStream = new FileInputStream(coverFile);
                System.out.println("In here 2");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(bookEntry.getPublishedDate(), formatter);

                String sql = "INSERT INTO BOOK_ENTRIES(title, authors, cover, isbn, page_count, publisher, published_date, genre)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, bookEntry.getTitle());                    
                preparedStatement.setString(2, Arrays.toString(bookEntry.getAuthors()).replace("[", "").replace("]", ""));
                preparedStatement.setBinaryStream(3, inputStream, (int) coverFile.length());
                preparedStatement.setString(4, bookEntry.getISBN());
                preparedStatement.setInt(5, bookEntry.getPageCount());
                preparedStatement.setString(6, bookEntry.getPublisher());
                preparedStatement.setDate(7, java.sql.Date.valueOf(date));
                preparedStatement.setString(8, bookEntry.getGenre());
                preparedStatement.executeUpdate();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    bookEntryId = rs.getInt(1);
                }

                return bookEntryId;
            } else {
                error = "DB connection failed";
                return -1;
            }
         } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return -1;  
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
            if (inputStream != null) try { inputStream.close(); } catch (IOException ignore) {}
         }
    }

    public static BookEntry getBookEntryById(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BookEntry bookEntry = null;
        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            String sql = "SELECT id, title, authors, cover, isbn, page_count, publisher, published_date, genre\n" +
                "FROM book_entries\n" +
                "WHERE id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){  
                bookEntry = new BookEntry();
                bookEntry.setDbId(rs.getInt(1));
                bookEntry.setTitle(rs.getString(2));
                bookEntry.setAuthors(rs.getString(3).split(",")); 
                
                blobCover = rs.getBlob(4);
                byteCover = blobCover.getBytes(1l, (int)blobCover.length());
                bookEntry.setCover(new String(Base64.getEncoder().encode(byteCover)));

                bookEntry.setISBN(rs.getString(5));
                bookEntry.setPageCount(rs.getInt(6));
                bookEntry.setPublisher(rs.getString(7));

                Date date = new Date(rs.getDate(8).getTime());
                DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
                bookEntry.setPublishedDate(dateFormat.format(date));

                bookEntry.setGenre(rs.getString(9));
            }

            return bookEntry;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static boolean editBookEntry(BookEntry bookEntry) throws SQLException {
        error = "";
        Connection connection = null;
        Statement statement = null;
        System.out.println("IN HERE!");
        try {
            connection = connectToDB();

            if(connection != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(bookEntry.getPublishedDate(), formatter);
                
                statement = connection.createStatement();
                String sql = "UPDATE book_entries\n" + 
                    "SET title = " + "'" + bookEntry.getTitle() + "'," +
                    "authors = " + "'" + Arrays.toString(bookEntry.getAuthors()).replace("[", "").replace("]", "") + "'," +
                    "page_count = " + "'" + bookEntry.getPageCount() + "'," +
                    "publisher = " + "'" + bookEntry.getPublisher() + "'," +
                    "published_date = " + "'" + java.sql.Date.valueOf(date) + "'," +
                    "genre = " + "'" + bookEntry.getGenre() + "'\n" +
                    "WHERE ( id = '" + bookEntry.getDbId() +"')";

                System.out.println("SQL STATEMENT: " + sql);
                statement.executeUpdate(sql);

                return true;
            } else {
                System.out.println("IN HERE HAHA!");
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

    public static boolean addBookCopy(BookCopy bookCopy) throws SQLException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            if(connection != null) {
                statement = connection.createStatement();
                String sql = "INSERT INTO BOOK_COPIES(book_entry_id, purchase_price)\n" +
                    "VALUES('"+
                    bookCopy.getBookEntryId()+"','"+
                    bookCopy.getPurchasePrice()+"')";
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

    public static List<BookCopy> getBookCopies(int bookEntryId, boolean onlyAvailableCopies) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<BookCopy> bookCopies = new ArrayList<BookCopy>();

        try {
            connection = connectToDB();

            String sql = "SELECT id, current_checkout_record_id, checked_out, purchase_price, available\n" +
                "FROM book_copies\n" +
                "WHERE book_entry_id = ?";
            
            if(onlyAvailableCopies) {
                sql += " AND checked_out = '0'";
            }
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookEntryId);
            System.out.println("SQL = " + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();
                            
            while(rs.next()){  
                BookCopy bookCopy = new BookCopy();
                bookCopy.setDbId(rs.getInt(1));
                bookCopy.setCurrentCheckoutRecordId(rs.getInt(2));
                bookCopy.setCheckedOut(rs.getBoolean(3));
                bookCopy.setPurchasePrice(rs.getInt(4));
                bookCopy.setAvailable(rs.getBoolean(5));
                bookCopies.add(bookCopy);
            }

            return bookCopies;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static BookCopy getBookCopyById(int id) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BookCopy bookCopy = null;

        try {
            connection = connectToDB();

            String sql = "SELECT id, current_checkout_record_id, checked_out, purchase_price, available\n" +
                "FROM book_copies\n" +
                "WHERE id = ?";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
                            
            while(rs.next()){  
                bookCopy = new BookCopy();
                bookCopy.setDbId(rs.getInt(1));
                bookCopy.setCurrentCheckoutRecordId(rs.getInt(2));
                bookCopy.setCheckedOut(rs.getBoolean(3));
                bookCopy.setPurchasePrice(rs.getInt(4));
                bookCopy.setAvailable(rs.getBoolean(5));
            }

            return bookCopy;
         } finally {
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
    }

    public static void editBookCopy(BookCopy bookCopy) throws SQLException, ClassNotFoundException {
        error = "";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectToDB();

            statement = connection.createStatement();
            String sql = "UPDATE book_copies\n" + 
                "SET purchase_price = " + "'" + bookCopy.getPurchasePrice() + "'\n" +
                "WHERE ( id = '" + bookCopy.getDbId() +"')";

            statement.executeUpdate(sql);
         } finally {
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
         }
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
