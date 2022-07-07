package project.app.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookEntry;
import project.app.service.DBService;
import project.app.service.SessionService;

public class AdminEditBook extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String queryTitle;
    private List<BookEntry> resultBooks;

    private String error;

    private String ISBN;
    private BookEntry bookEntryBean;
    private List<String> authorList = new ArrayList<String>();
    private List<String> genreList = Arrays.asList(new String[] {"Fiction", "Non-Fiction"});

    public String execute() {
        String role = (String) userSession.get("role");

        if(SessionService.isAdmin(role)) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String editBookEntry() {
        try {
            bookEntryBean.setDbId((int) userSession.get("bookEntryId"));
            if(DBService.editBookEntry(bookEntryBean)) {
                System.out.println("ERROR 1");
                return SUCCESS;
            } else {
                System.out.println("ERROR 2");
                return ERROR;
            }
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String searchBookFromDB() {
        try {
            resultBooks = DBService.searchBookEntriesByTitle(queryTitle);
            System.out.println("BOOKSEARCH");
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getBookEntryByISBN() {
        System.out.println("BOOKGET");
        try {
            bookEntryBean = DBService.getBookEntryByISBN(ISBN);
            authorList = Arrays.asList(bookEntryBean.getAuthors());
            // bookCopies = DBService.getBookCopies(bookEntryBean.getDbId());
            userSession.put("bookEntryId", bookEntryBean.getDbId());
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getBookEntryById() {
        System.out.println("BOOKGET");
        try {
            bookEntryBean = DBService.getBookEntryById((int) userSession.get("bookEntryId"));
            authorList = Arrays.asList(bookEntryBean.getAuthors());
            // bookCopies = DBService.getBookCopies(bookEntryBean.getDbId());
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getQueryTitle() {
        return queryTitle;
    }

    public void setQueryTitle(String queryTitle) {
        this.queryTitle = queryTitle;
    }

    public List<BookEntry> getResultBooks() {
        return resultBooks;
    }

    public void setResultBooks(List<BookEntry> resultBooks) {
        this.resultBooks = resultBooks;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public BookEntry getBookEntryBean() {
        return bookEntryBean;
    }

    public void setBookEntryBean(BookEntry bookEntryBean) {
        this.bookEntryBean = bookEntryBean;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
    }
}
