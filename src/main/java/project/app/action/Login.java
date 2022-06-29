package project.app.action;

import java.nio.channels.AcceptPendingException;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.Account;

public class Login extends ActionSupport {
    private Account accountBean;
    
    public String execute() {
        return SUCCESS;
    }

    public Account getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(Account accountBean) {
        this.accountBean = accountBean;
    }
}
