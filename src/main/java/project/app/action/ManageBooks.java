package project.app.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookEntry;
import project.app.model.ISBNResponse;
import project.app.service.OpenLibraryAPIService;
import project.app.service.SessionService;

public class ManageBooks extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String queryISBN;
    private BookEntry bookEntryBean;
    private ISBNResponse ISBNResponseBean;
    private String error;

    private List<String> authorList;

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

    public String addBook() {
        return SUCCESS;
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
