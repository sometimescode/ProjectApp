package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookCopy;
import project.app.service.DBService;
import project.app.service.SessionService;

public class AdminEditBookCopy extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private int id;
    private BookCopy bookCopyBean;

    public String execute() {
        String role = (String) userSession.get("role");

        if(SessionService.isAdmin(role)) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String getBookCopyById() {
        try {
            bookCopyBean = DBService.getBookCopyById(id);
            return SUCCESS;
        } catch (ClassNotFoundException | SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String editBookCopy() {
        try {
            bookCopyBean.setDbId(id);
            DBService.editBookCopy(bookCopyBean);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookCopy getBookCopyBean() {
        return bookCopyBean;
    }

    public void setBookCopyBean(BookCopy bookCopyBean) {
        this.bookCopyBean = bookCopyBean;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }

    
}
