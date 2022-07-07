package project.app.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookEntry;
import project.app.service.DBService;

public class Book extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;
    
    private String error;
    private String ISBN;
    private BookEntry bookEntryBean;
    private int availableCopies;
    private boolean submittedPendingRequest;

    public String execute() {
        try {
            if(ISBN == null) {
                int bookEntryId = (int) userSession.get("bookEntryId");
                bookEntryBean = DBService.getBookEntryById(bookEntryId);
            } else {
                bookEntryBean = DBService.getBookEntryByISBN(ISBN);
                userSession.put("bookEntryId", bookEntryBean.getDbId());
            }
            availableCopies = DBService.getAvailableCopiesCount(bookEntryBean.getDbId());

            int userId = (int) userSession.get("id");
            submittedPendingRequest = DBService.hasPendingCheckoutRequest(userId, bookEntryBean.getDbId());
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String submitCheckoutRequest() {
        int userId = (int) userSession.get("id");
        int bookEntryId = (int) userSession.get("bookEntryId");
        try {
            DBService.addCheckoutRequest(userId, bookEntryId);
            return SUCCESS;
        } catch (SQLException e) {
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

    
    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public boolean isSubmittedPendingRequest() {
        return submittedPendingRequest;
    }

    public void setSubmittedPendingRequest(boolean submittedPendingRequest) {
        this.submittedPendingRequest = submittedPendingRequest;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
    
}
