package project.app.model;

public class User extends Account {
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    
    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "User [contactNumber=" + contactNumber + ", email=" + email + ", firstName=" + firstName + ", lastName="
                + lastName + "]" + "\nFrom Parent Account [password=" + super.getPassword() + ", username=" + super.getUsername() + "]";
    }
}
