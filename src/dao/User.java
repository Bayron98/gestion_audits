package dao;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
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
        this.password = password;
        setRole(role);
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
        if (role.equals("admin") || role.equals("auditeur")) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Role must be either 'admin' or 'auditeur'");
        }
    }
}