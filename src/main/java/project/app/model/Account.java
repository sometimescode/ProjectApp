package project.app.model;

public class Account {
    private int id;
    private String username;
    private String password;

    public Account() {

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", password=" + password + ", username=" + username + "]";
    }
}
