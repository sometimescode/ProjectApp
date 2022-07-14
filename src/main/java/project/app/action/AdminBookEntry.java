package project.app.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookCopy;
import project.app.model.BookEntry;
import project.app.service.DBService;

public class AdminBookEntry extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private BookEntry bookEntryBean;
    private List<BookCopy> bookCopies;
    private String ISBN;
    private String error;

    public String getBookEntryById() {
        int bookEntryId = (int) userSession.get("bookEntryId");
        try {
            bookEntryBean = DBService.getBookEntryById(bookEntryId);
            bookCopies = DBService.getBookCopies(bookEntryId, false);
            return SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getBookEntryByISBN() {
        try {
            bookEntryBean = DBService.getBookEntryByISBN(ISBN);
            bookCopies = DBService.getBookCopies(bookEntryBean.getDbId(), false);
            userSession.put("bookEntryId", bookEntryBean.getDbId());
            return SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public BookEntry getBookEntryBean() {
        return bookEntryBean;
    }

    public void setBookEntryBean(BookEntry bookEntryBean) {
        this.bookEntryBean = bookEntryBean;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
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
