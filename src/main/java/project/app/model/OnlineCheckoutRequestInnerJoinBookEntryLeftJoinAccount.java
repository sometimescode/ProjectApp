package project.app.model;

import java.util.Date;

public class OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount {
    private int dbId;
    private int requesterId;
    private int bookToCheckoutId;
    private String status;
    private Date requestDate;
    private Date statusUpdateDate;
    private int checkoutRecordId;
    private int joinBookEntryId;
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
    public int getRequesterId() {
        return requesterId;
    }
    public void setRequesterId(int requesterId) {
        this.requesterId = requesterId;
    }
    public int getBookToCheckoutId() {
        return bookToCheckoutId;
    }
    public void setBookToCheckoutId(int bookToCheckoutId) {
        this.bookToCheckoutId = bookToCheckoutId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    public Date getStatusUpdateDate() {
        return statusUpdateDate;
    }
    public void setStatusUpdateDate(Date statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }
    public int getCheckoutRecordId() {
        return checkoutRecordId;
    }
    public void setCheckoutRecordId(int checkoutRecordId) {
        this.checkoutRecordId = checkoutRecordId;
    }
    public int getJoinBookEntryId() {
        return joinBookEntryId;
    }
    public void setJoinBookEntryId(int joinBookEntryId) {
        this.joinBookEntryId = joinBookEntryId;
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
    
    @Override
    public String toString() {
        return "OnlineCheckoutRequestInnerJoinBookEntryLeftJoinAccount [bookToCheckoutId=" + bookToCheckoutId
                + ", checkoutRecordId=" + checkoutRecordId + ", dbId=" + dbId + ", joinAccountFirstName="
                + joinAccountFirstName + ", joinAccountLastName=" + joinAccountLastName + ", joinBookEntryCover="
                + joinBookEntryCover + ", joinBookEntryISBN=" + joinBookEntryISBN + ", joinBookEntryId="
                + joinBookEntryId + ", joinBookEntryTitle=" + joinBookEntryTitle + ", requestDate=" + requestDate
                + ", requesterId=" + requesterId + ", status=" + status + ", statusUpdateDate=" + statusUpdateDate
                + "]";
    }


}