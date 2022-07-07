package project.app.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.service.SessionService;

public class AdminManageBooks extends ActionSupport implements SessionAware {
    //noted problem = Requires you to SEARCH first to fill some fields
    //if you refresh the page or go back and the forms are auto-filled
    //without running SEARCH again, it will produce an error if you add a book
    private Map<String, Object> userSession;

    private String error;

    public String execute() {
        String role = (String) userSession.get("role");

        if(SessionService.isAdmin(role)) {
            return SUCCESS;
        } else {
            return ERROR;
        }
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
