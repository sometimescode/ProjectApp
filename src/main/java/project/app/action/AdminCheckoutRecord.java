package project.app.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import project.app.model.BookCopy;
import project.app.model.CheckoutRecord;
import project.app.model.OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount;
import project.app.service.DBService;

public class AdminCheckoutRecord extends ActionSupport implements SessionAware {
    private Map<String, Object> userSession;

    private String error;
    private int checkoutRequestId;
    private CheckoutRecord checkoutRecordBean;
    private List<String> bookCopySelectList;

    public String checkoutRecordForm() {
        try {
            OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount checkoutRequest = DBService.getCheckoutRequestById(checkoutRequestId);
            int bookEntryId = checkoutRequest.getJoinBookEntryId();

            checkoutRecordBean = new CheckoutRecord();
            checkoutRecordBean.setBookEntryId(bookEntryId);
            checkoutRecordBean.setBorrowerId(checkoutRequest.getRequesterId());
            checkoutRecordBean.setOnlineCheckoutRequestId(checkoutRequest.getDbId());
            
            List<BookCopy> availableBookCopies = new ArrayList<BookCopy>();
            availableBookCopies = DBService.getBookCopies(bookEntryId, true);

            bookCopySelectList = new ArrayList<String>();
            for (int i = 0; i < availableBookCopies.size(); i++) {
                bookCopySelectList.add(Integer.toString(availableBookCopies.get(i).getDbId()));
            }

            return SUCCESS;
        } catch (SQLException | ClassNotFoundException e) {
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


    public int getCheckoutRequestId() {
        return checkoutRequestId;
    }


    public void setCheckoutRequestId(int checkoutRequestId) {
        this.checkoutRequestId = checkoutRequestId;
    }


    public CheckoutRecord getCheckoutRecordBean() {
        return checkoutRecordBean;
    }


    public void setCheckoutRecordBean(CheckoutRecord checkoutRecordBean) {
        this.checkoutRecordBean = checkoutRecordBean;
    }


    public List<String> getBookCopySelectList() {
        return bookCopySelectList;
    }


    public void setBookCopySelectList(List<String> bookCopySelectList) {
        this.bookCopySelectList = bookCopySelectList;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        userSession = session;
        
    }
}
