package project.app.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookEntry;
import project.app.model.ISBNResponse;
import project.app.service.DBService;
import project.app.service.OpenLibraryAPIService;
import project.app.service.SessionService;

public class AdminManageBooks extends ActionSupport implements SessionAware {
    //noted problem = Requires you to SEARCH first to fill some fields
    //if you refresh the page or go back and the forms are auto-filled
    //without running SEARCH again, it will produce an error if you add a book
    private Map<String, Object> userSession;

    private String queryISBN;
    private BookEntry bookEntryBean;
    private ISBNResponse ISBNResponseBean;
    private String error;

    private List<String> authorList = new ArrayList<String>();
    private List<String> genreList = Arrays.asList(new String[] {"Fiction", "Non-Fiction"});

    public String execute() {
        String role = (String) userSession.get("role");

        if(SessionService.isAdmin(role)) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    private void populateFieldSuggestions() {
        int authorCount = ISBNResponseBean.getAuthors().length;
        String[] authorNames = new String[authorCount];

        //needs to be initialized because it is not a part of the form with searchBook action
        bookEntryBean = new BookEntry();

        bookEntryBean.setTitle(ISBNResponseBean.getTitle());

        for(int x = 0; x < authorCount; x++) {
            authorNames[x] = ISBNResponseBean.getAuthors()[x].getName();
        }
        authorList = Arrays.asList(authorNames);
        bookEntryBean.setAuthors(authorNames);

        bookEntryBean.setISBN(queryISBN);
        bookEntryBean.setPageCount(ISBNResponseBean.getNumber_of_pages());
        bookEntryBean.setPublisher(ISBNResponseBean.getPublishers()[0].getName());
        bookEntryBean.setPublishedDate(ISBNResponseBean.getPublish_date());
        bookEntryBean.setCover(ISBNResponseBean.getCover().getMedium());

    }

    public String searchBook() {
        try {
            ISBNResponseBean = OpenLibraryAPIService.searchISBNAPI(queryISBN);
            populateFieldSuggestions();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return ERROR;
        }
    }

    public String addBookEntry() {
        try {
            int bookEntryId = DBService.addBookEntryGetId(bookEntryBean);

            if(bookEntryId != -1) {
                userSession.put("bookEntryId", bookEntryId);
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

    public String getQueryISBN() {
        return queryISBN;
    }

    public void setQueryISBN(String queryISBN) {
        this.queryISBN = queryISBN;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<String> genreList) {
        this.genreList = genreList;
    }

    public BookEntry getBookEntryBean() {
        return bookEntryBean;
    }

    public void setBookEntryBean(BookEntry bookEntryBean) {
        this.bookEntryBean = bookEntryBean;
    }

    public ISBNResponse getISBNResponseBean() {
        return ISBNResponseBean;
    }

    public void setISBNResponseBean(ISBNResponse iSBNResponseBean) {
        ISBNResponseBean = iSBNResponseBean;
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
