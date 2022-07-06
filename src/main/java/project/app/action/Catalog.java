package project.app.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookEntry;
import project.app.service.DBService;

public class Catalog extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private List<BookEntry> fictionBooks;
    private List<BookEntry> nonfictionBooks;
    private String error;
    
    public String execute() {
        try {
            fictionBooks = DBService.getBookEntriesForCatalog("Fiction");
            nonfictionBooks = DBService.getBookEntriesForCatalog("Non-Fiction");
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<BookEntry> getFictionBooks() {
        return fictionBooks;
    }

    public void setFictionBooks(List<BookEntry> fictionBooks) {
        this.fictionBooks = fictionBooks;
    }

    public List<BookEntry> getNonfictionBooks() {
        return nonfictionBooks;
    }

    public void setNonfictionBooks(List<BookEntry> nonfictionBooks) {
        this.nonfictionBooks = nonfictionBooks;
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
