package project.app.model;

public class User extends Account {
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;

    public User() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String toString() {
        return "User [contactNumber=" + contactNumber + ", email=" + email + ", firstName=" + firstName + ", lastName="
                + lastName + "]";
    }    
}
