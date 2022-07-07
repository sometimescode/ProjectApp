package project.app.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookEntry;
import project.app.service.DBService;
import project.app.service.SessionService;

public class CatalogSearch extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String queryTitle;
    private List<BookEntry> resultBooks;
    private String error;

    public String execute() {
        try {
            resultBooks = DBService.searchBookEntriesByTitle(queryTitle);
            return SUCCESS;
        } catch (SQLException e) {
            error = e.toString();
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getQueryTitle() {
        return queryTitle;
    }

    public void setQueryTitle(String queryTitle) {
        this.queryTitle = queryTitle;
    }

    public List<BookEntry> getResultBooks() {
        return resultBooks;
    }

    public void setResultBooks(List<BookEntry> resultBooks) {
        this.resultBooks = resultBooks;
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
