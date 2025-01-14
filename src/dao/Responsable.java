package dao;

import java.io.Serializable;

public class Responsable implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String role;

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role.equals("responsable système") || role.equals("responsable processus")) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Le rôle doit être soit 'responsable système' soit 'responsable processus'");
        }
    }
}
