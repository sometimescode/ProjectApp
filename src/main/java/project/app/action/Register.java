package project.app.action;

import java.sql.SQLException;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.User;
import project.app.service.DBService;

public class Register extends ActionSupport {
    private User userBean;
    private String error;

    public String execute() {
        return SUCCESS;
    }

    public String registerUser() {
        try {
            if(DBService.registerUser(userBean)) {
                return SUCCESS;
            }
            else {
                error = DBService.getError();
                return ERROR;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error = e.toString();
            return ERROR;
        }
    }

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
