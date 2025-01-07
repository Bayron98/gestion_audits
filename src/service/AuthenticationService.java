package service;

import dao.User;
import java.util.List;

public class AuthenticationService {
    private GestionUsers gestionUsers = new GestionUsers();

    public User authenticate(String username, String password) {
        List<User> users = gestionUsers.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        List<User> users = gestionUsers.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
