package project.app.model;

public class UserSession {
    private int id;
    private String firstName;
    private String role;

    private int bookEntryId;
    private int checkoutRequestId;
    
    public UserSession() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getBookEntryId() {
        return bookEntryId;
    }

    public void setBookEntryId(int bookEntryId) {
        this.bookEntryId = bookEntryId;
    }

    public int getCheckoutRequestId() {
        return checkoutRequestId;
    }

    public void setCheckoutRequestId(int checkoutRequestId) {
        this.checkoutRequestId = checkoutRequestId;
    }
}
