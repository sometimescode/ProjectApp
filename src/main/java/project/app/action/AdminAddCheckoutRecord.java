package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.CheckoutRecord;
import project.app.service.DBService;

public class AdminAddCheckoutRecord extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private CheckoutRecord checkoutRecordBean;

    public String addCheckoutRecord() {
        try {
            int checkoutRecordId = DBService.addCheckoutRecordGetId(checkoutRecordBean);
            System.out.println("CHECKOUTRECORDID = " + checkoutRecordId);
            if(checkoutRecordId != -1) {
                DBService.updateCheckoutRequestCheckoutRecordId(checkoutRecordBean.getOnlineCheckoutRequestId(), checkoutRecordId);
                DBService.checkoutBookCopy(checkoutRecordBean.getBookCopyId(), checkoutRecordId);
                return SUCCESS;
            } else {
                return ERROR;
            }
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

    public CheckoutRecord getCheckoutRecordBean() {
        return checkoutRecordBean;
    }

    public void setCheckoutRecordBean(CheckoutRecord checkoutRecordBean) {
        this.checkoutRecordBean = checkoutRecordBean;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
}
