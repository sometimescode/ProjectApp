package project.app.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookCopy;
import project.app.service.DBService;

public class AdminBookCopy extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private BookCopy bookCopyBean;
    private String error;

    public String addBookCopy() {
        bookCopyBean.setBookEntryId((int) userSession.get("bookEntryId"));
        try {
            if(DBService.addBookCopy(bookCopyBean)) {
                return SUCCESS;
            } else {
                return ERROR;
            }
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public BookCopy getBookCopyBean() {
        return bookCopyBean;
    }

    public void setBookCopyBean(BookCopy bookCopyBean) {
        this.bookCopyBean = bookCopyBean;
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
