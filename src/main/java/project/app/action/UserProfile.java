package project.app.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.CheckoutRecordInnerJoinBookEntryLeftJoinAccount;
import project.app.model.OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount;
import project.app.service.DBService;

public class UserProfile extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkedOutBooks;
    private List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> pendingCheckoutRequests;
    private List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> onlineCheckoutRequests;
    private int checkoutRequestId;

    public String execute() {
        int userId = (int) userSession.get("id");
        try {
            checkedOutBooks = DBService.getCheckedOutBooksByUser(userId);
            pendingCheckoutRequests = DBService.getOnlineCheckoutRequestByUserAndStatus(userId, "Pending");
            onlineCheckoutRequests = DBService.getOnlineCheckoutRequestByUser(userId);
            return SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }
    
    public String cancelCheckoutRequest() {
        try {
            DBService.updateCheckoutRequestStatus(checkoutRequestId, "Canceled");
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

    public List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public void setCheckedOutBooks(List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkedOutBooks) {
        this.checkedOutBooks = checkedOutBooks;
    }

    public List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> getPendingCheckoutRequests() {
        return pendingCheckoutRequests;
    }

    public void setPendingCheckoutRequests(
            List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> pendingCheckoutRequests) {
        this.pendingCheckoutRequests = pendingCheckoutRequests;
    }

    public List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> getOnlineCheckoutRequests() {
        return onlineCheckoutRequests;
    }

    public void setOnlineCheckoutRequests(List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> onlineCheckoutRequests) {
        this.onlineCheckoutRequests = onlineCheckoutRequests;
    }

    public int getCheckoutRequestId() {
        return checkoutRequestId;
    }

    public void setCheckoutRequestId(int checkoutRequestId) {
        this.checkoutRequestId = checkoutRequestId;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
}
