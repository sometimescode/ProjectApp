package project.app.action;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.User;

public class Register extends ActionSupport {
    private User userBean;
    private String test;

    public String execute() {
        System.out.println("I'M CALLED");
        return SUCCESS;
    }

    
    public String getTest() {
        return test;
    }


    public void setTest(String test) {
        this.test = test;
    }


    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }
}
