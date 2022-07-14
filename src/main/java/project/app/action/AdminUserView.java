package project.app.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.CheckoutRecordInnerJoinBookEntryLeftJoinAccount;
import project.app.model.OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount;
import project.app.model.User;
import project.app.service.DBService;

public class AdminUserView extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private int userId;
    private User userBean;

    private List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords;
    private List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> onlineCheckoutRequests;
    
    public String execute() {
        try {
            userBean = DBService.getUserById(userId);
            checkoutRecords = DBService.getCheckoutRecordsByUser(userId);
            onlineCheckoutRequests = DBService.getOnlineCheckoutRequestByUser(userId);
            return SUCCESS;
        } catch (ClassNotFoundException | SQLException e) {
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


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public User getUserBean() {
        return userBean;
    }


    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    

    public List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckoutRecords() {
        return checkoutRecords;
    }


    public void setCheckoutRecords(List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords) {
        this.checkoutRecords = checkoutRecords;
    }


    public List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> getOnlineCheckoutRequests() {
        return onlineCheckoutRequests;
    }


    public void setOnlineCheckoutRequests(
            List<OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount> onlineCheckoutRequests) {
        this.onlineCheckoutRequests = onlineCheckoutRequests;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
    }

    
}
