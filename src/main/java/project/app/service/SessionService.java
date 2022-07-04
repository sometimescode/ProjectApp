package project.app.service;

public class SessionService {
    
    public static boolean isAdmin(String role) {
        if(role != null && role.equals("Admin"))
         {
            return true;   
        } else {
            return false;
        }
    }
}
