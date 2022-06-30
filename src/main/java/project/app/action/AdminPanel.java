package project.app.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class AdminPanel extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    public String execute() {
        String role = (String) userSession.get("role");

        System.out.println("role = " + role);
        if(role != "Admin")
         {
            return ERROR;
        } else {
            return SUCCESS;   
        }
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
    }

    
    
}
