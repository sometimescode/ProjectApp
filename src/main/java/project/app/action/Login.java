package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.Account;
import project.app.model.UserSession;
import project.app.service.DBService;
import project.app.service.SessionService;

public class Login extends ActionSupport implements SessionAware {
    private Account accountBean;
    private String validationString;
    private String error;

    private Map<String, Object> userSession;
    
    private void updateSession(UserSession userSessionData) {
        userSession.put("id", userSessionData.getId());
        userSession.put("firstName", userSessionData.getFirstName());
        userSession.put("role", userSessionData.getRole());
    }

    public String execute() {
        return SUCCESS;
    }

    public String loginAccount() {
        UserSession userSessionData;

        try {
            userSessionData = DBService.verifyCredentials(accountBean);
            if(userSessionData != null) {
                updateSession(userSessionData);
                
                String role = (String) userSession.get("role");

                if(SessionService.isAdmin(role)) {
                    return "admin";
                } else {
                    return "regular";
                }
            } else {
                validationString = "Invalid username or password. Please try again.";
                return INPUT;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error = e.toString();
            return ERROR;
        }
    }

    public Account getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(Account accountBean) {
        this.accountBean = accountBean;
    }

    public String getValidationString() {
        return validationString;
    }

    public void setValidationString(String validationString) {
        this.validationString = validationString;
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
