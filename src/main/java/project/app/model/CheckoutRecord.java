package project.app.model;

import java.util.Date;

public class CheckoutRecord {
    private int dbId;
    private int bookEntryId;
    private int bookCopyId;
    private int borrowerId;
    private int onlineCheckoutRequestId;
    private Date checkoutDate;
    private Date expectedReturnDate;
    private Date actualReturnDate;
    private String status;
    private int fine;
    private boolean finePaid;
    public int getDbId() {
        return dbId;
    }
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }
    public int getBookEntryId() {
        return bookEntryId;
    }
    public void setBookEntryId(int bookEntryId) {
        this.bookEntryId = bookEntryId;
    }
    public int getBookCopyId() {
        return bookCopyId;
    }
    public void setBookCopyId(int bookCopyId) {
        this.bookCopyId = bookCopyId;
    }
    public int getBorrowerId() {
        return borrowerId;
    }
    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }
    public int getOnlineCheckoutRequestId() {
        return onlineCheckoutRequestId;
    }
    public void setOnlineCheckoutRequestId(int onlineCheckoutRequestId) {
        this.onlineCheckoutRequestId = onlineCheckoutRequestId;
    }
    public Date getCheckoutDate() {
        return checkoutDate;
    }
    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }
    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }
    public Date getActualReturnDate() {
        return actualReturnDate;
    }
    public void setActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getFine() {
        return fine;
    }
    public void setFine(int fine) {
        this.fine = fine;
    }
    public boolean isFinePaid() {
        return finePaid;
    }
    public void setFinePaid(boolean finePaid) {
        this.finePaid = finePaid;
    }
    @Override
    public String toString() {
        return "CheckoutRecord [actualReturnDate=" + actualReturnDate + ", bookCopyId=" + bookCopyId + ", bookEntryId="
                + bookEntryId + ", borrowerId=" + borrowerId + ", checkoutDate=" + checkoutDate + ", dbId=" + dbId
                + ", expectedReturnDate=" + expectedReturnDate + ", fine=" + fine + ", finePaid=" + finePaid
                + ", onlineCheckoutRequestId=" + onlineCheckoutRequestId + ", status=" + status + "]";
    }

    
}
