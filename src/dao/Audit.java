package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Audit implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Date date_debut;
    private Date date_fin;
    private String intitule;
    private String status; // Planifié, En cours, Suspendu, Terminé, Validé, Archivé
    private String type;
    private List<SystemeExigence> systeme_exigence;
    private User user; // avec un role auditeur
    private Standard standard;
    private Processus processus; // ajouter processus

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SystemeExigence> getSysteme_exigence() {
        return systeme_exigence;
    }

    public void setSysteme_exigence(List<SystemeExigence> systeme_exigence) {
        this.systeme_exigence = systeme_exigence;
    }
    public void addSystemeExigence(SystemeExigence systemeExigence) {
        this.systeme_exigence.add(systemeExigence);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user.getRole().equals("auditeur")) {
            this.user = user;
        } else {
            throw new IllegalArgumentException("User role must be 'auditeur'");
        }
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Processus getProcessus() {
        return processus;
    }

    public void setProcessus(Processus processus) {
        this.processus = processus;
    }
}
