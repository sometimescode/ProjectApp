package project.app.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.CheckoutRecordInnerJoinBookEntryLeftJoinAccount;
import project.app.service.DBService;

public class AdminManageCheckoutRecords extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;
    
    private String error;

    private List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> expectedCheckins;
    private List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> lateExpectedCheckins;
    private List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkinsToday;
    private List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords;

    public String execute() {
        try {
            expectedCheckins = DBService.getExpectedCheckins();
            lateExpectedCheckins = DBService.getLateExpectedCheckins();
            checkoutRecords = DBService.getCheckoutRecords();
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


    public List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getExpectedCheckins() {
        return expectedCheckins;
    }


    public void setExpectedCheckins(List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> expectedCheckins) {
        this.expectedCheckins = expectedCheckins;
    }

    
    public List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getLateExpectedCheckins() {
        return lateExpectedCheckins;
    }


    public void setLateExpectedCheckins(List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> lateExpectedCheckins) {
        this.lateExpectedCheckins = lateExpectedCheckins;
    }

    


    public List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckinsToday() {
        return checkinsToday;
    }


    public void setCheckinsToday(List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkinsToday) {
        this.checkinsToday = checkinsToday;
    }


    public List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> getCheckoutRecords() {
        return checkoutRecords;
    }


    public void setCheckoutRecords(List<CheckoutRecordInnerJoinBookEntryLeftJoinAccount> checkoutRecords) {
        this.checkoutRecords = checkoutRecords;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
    }
    
}
