package project.app.model;

public class BookCopy {
    private int dbId;
    private int bookEntryId;
    private int currentCheckoutRecordId;
    private boolean checkedOut;
    private int purchasePrice;
    private boolean available;
    
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
    public int getCurrentCheckoutRecordId() {
        return currentCheckoutRecordId;
    }
    public void setCurrentCheckoutRecordId(int currentCheckoutRecordId) {
        this.currentCheckoutRecordId = currentCheckoutRecordId;
    }
    public boolean isCheckedOut() {
        return checkedOut;
    }
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
    public int getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "BookCopy [available=" + available + ", bookEntryId=" + bookEntryId + ", checkedOut=" + checkedOut
                + ", currentCheckoutRecordId=" + currentCheckoutRecordId + ", dbId=" + dbId + ", purchasePrice="
                + purchasePrice + "]";
    }
}
