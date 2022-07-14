package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.Account;
import project.app.service.DBService;

public class UserAccount extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private Account userBean;

    public String execute() {
        int id = (int) userSession.get("id");
        try {
            userBean = DBService.getUserById(id);
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


    public Account getUserBean() {
        return userBean;
    }


    public void setUserBean(Account userBean) {
        this.userBean = userBean;
    }


    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
}
