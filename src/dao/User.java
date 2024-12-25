package dao;
import service.PasswordUtils;

public class User {
    private static int idCounter = 1;
    private int id;
    private String name;
    private String username;
    private String password;
    private String role;

    public User(String name, String username, String password, String role) {
        this.id = idCounter++;
        this.name = name;
        this.username = username;
        this.password = PasswordUtils.hashPassword(password);
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}