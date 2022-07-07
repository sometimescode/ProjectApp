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
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import project.app.model.Account;
import project.app.model.BookCopy;
import project.app.model.BookEntry;
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
    public static BookEntry getBookEntryByISBN(String ISBN) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BookEntry bookEntry = null;
        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            if(connection != null) {
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

                    java.util.Date utilDate = new java.util.Date(rs.getDate(8).getTime());
                    DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
                    bookEntry.setPublishedDate(dateFormat.format(utilDate));

                    bookEntry.setGenre(rs.getString(9));

                    System.out.println("BOOK ENTRY = " + bookEntry.toString());
                }

                return bookEntry;
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
                    "AND available = '1'";

                ResultSet rs = statement.executeQuery(sql);

                rs.next();

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

    public static boolean hasPendingCheckoutRequest(int userId, int bookEntryId) throws SQLException {
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

    public static BookEntry getBookEntryById(int id) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BookEntry bookEntry = null;
        Blob blobCover = null;
        byte[] byteCover = null;

        try {
            connection = connectToDB();

            if(connection != null) {
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

                    java.util.Date utilDate = new java.util.Date(rs.getDate(8).getTime());
                    DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
                    bookEntry.setPublishedDate(dateFormat.format(utilDate));

                    bookEntry.setGenre(rs.getString(9));
                }

                return bookEntry;
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

    public static List<BookCopy> getBookCopies(int bookEntryId) throws SQLException {
        error = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<BookCopy> bookCopies = new ArrayList<BookCopy>();

        try {
            connection = connectToDB();

            if(connection != null) {
                String sql = "SELECT id, current_checkout_record_id, checked_out, purchase_price, available\n" +
                    "FROM book_copies\n" +
                    "WHERE book_entry_id = ?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, bookEntryId);
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
