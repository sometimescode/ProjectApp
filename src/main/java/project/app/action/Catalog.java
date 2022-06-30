package project.app.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class Catalog extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    public String execute() {
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
    
}
