package dao;

import java.io.Serializable;
import java.util.Date;

public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nom;
    private String description;
    private Date date_debut_prevue;
    private Date date_fin_prevue;
    private Date date_fin_reelle;
    private SystemeExigence systeme_exigence;
    private Responsable responsable;

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_debut_prevue() {
        return date_debut_prevue;
    }

    public void setDate_debut_prevue(Date date_debut_prevue) {
        this.date_debut_prevue = date_debut_prevue;
    }

    public Date getDate_fin_prevue() {
        return date_fin_prevue;
    }

    public void setDate_fin_prevue(Date date_fin_prevue) {
        this.date_fin_prevue = date_fin_prevue;
    }

    public Date getDate_fin_reelle() {
        return date_fin_reelle;
    }

    public void setDate_fin_reelle(Date date_fin_reelle) {
        this.date_fin_reelle = date_fin_reelle;
    }

    public SystemeExigence getSysteme_exigence() {
        return systeme_exigence;
    }

    public void setSysteme_exigence(SystemeExigence systeme_exigence) {
        this.systeme_exigence = systeme_exigence;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }
}
