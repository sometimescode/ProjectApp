package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.service.DBService;

public class AdminEditCheckoutRecord extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private int checkoutRecordId;
    private int bookCopyId;

    public String checkinCheckoutRecord() {
        try {
            DBService.checkinCheckoutRecord(checkoutRecordId);
            DBService.checkinBookCopy(bookCopyId);
            return SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCheckoutRecordId() {
        return checkoutRecordId;
    }

    public void setCheckoutRecordId(int checkoutRecordId) {
        this.checkoutRecordId = checkoutRecordId;
    }

    public int getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
}
