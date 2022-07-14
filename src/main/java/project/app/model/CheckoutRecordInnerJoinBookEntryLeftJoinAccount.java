package project.app.model;

import java.util.Date;

public class CheckoutRecordInnerJoinBookEntryLeftJoinAccount {
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
    private String joinBookEntryISBN;
    private String joinBookEntryTitle;
    private String joinBookEntryCover;
    private String joinAccountFirstName;
    private String joinAccountLastName;
    
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
    public String getJoinAccountFirstName() {
        return joinAccountFirstName;
    }
    public void setJoinAccountFirstName(String joinAccountFirstName) {
        this.joinAccountFirstName = joinAccountFirstName;
    }
    public String getJoinAccountLastName() {
        return joinAccountLastName;
    }
    public void setJoinAccountLastName(String joinAccountLastName) {
        this.joinAccountLastName = joinAccountLastName;
    }
    public String getJoinBookEntryISBN() {
        return joinBookEntryISBN;
    }
    public void setJoinBookEntryISBN(String joinBookEntryISBN) {
        this.joinBookEntryISBN = joinBookEntryISBN;
    }
    public String getJoinBookEntryTitle() {
        return joinBookEntryTitle;
    }
    public void setJoinBookEntryTitle(String joinBookEntryTitle) {
        this.joinBookEntryTitle = joinBookEntryTitle;
    }
    public String getJoinBookEntryCover() {
        return joinBookEntryCover;
    }
    public void setJoinBookEntryCover(String joinBookEntryCover) {
        this.joinBookEntryCover = joinBookEntryCover;
    }
    @Override
    public String toString() {
        return "CheckoutRecordInnerJoinBookEntryLeftJoinAccount [actualReturnDate=" + actualReturnDate + ", bookCopyId="
                + bookCopyId + ", bookEntryId=" + bookEntryId + ", borrowerId=" + borrowerId + ", checkoutDate="
                + checkoutDate + ", dbId=" + dbId + ", expectedReturnDate=" + expectedReturnDate + ", fine=" + fine
                + ", finePaid=" + finePaid + ", joinAccountFirstName=" + joinAccountFirstName + ", joinAccountLastName="
                + joinAccountLastName + ", joinBookEntryCover=" + joinBookEntryCover + ", joinBookEntryISBN="
                + joinBookEntryISBN + ", joinBookEntryTitle=" + joinBookEntryTitle + ", onlineCheckoutRequestId="
                + onlineCheckoutRequestId + ", status=" + status + "]";
    }
}
