package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount;
import project.app.service.DBService;

public class AdminCheckoutRequestAction extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount checkoutRequest;
    private int checkoutRequestId;

    public String execute() {
        //currently unused. We've temp decided to only use the approve and reject. checkoutRequest.jsp also unused
        // try {
        //     checkoutRequest = DBService.getCheckoutRequestById(checkoutRequestId);
        //     return SUCCESS;
        // } catch (SQLException e) {
        //     error = e.toString();
        //     e.printStackTrace();
        //     return ERROR;
        // }
        return ERROR;
    }

    public String approveCheckoutRequest() {
        try {
            DBService.updateCheckoutRequestStatus(checkoutRequestId, "Approved");
            return SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String rejectCheckoutRequest() {
        try {
            DBService.updateCheckoutRequestStatus(checkoutRequestId, "Rejected");
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

    public OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount getCheckoutRequest() {
        return checkoutRequest;
    }

    public void setCheckoutRequest(OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount checkoutRequest) {
        this.checkoutRequest = checkoutRequest;
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
