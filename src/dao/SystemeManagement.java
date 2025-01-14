package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemeManagement implements Serializable {
    private int id;
    private String description;
    private String nom;
    private Responsable responsable;
    private List<Processus> processus = new ArrayList<>();

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public List<Processus> getProcessus() {
        return processus;
    }

    public void setProcessus(List<Processus> processus) {
        this.processus = processus;
    }

    public void addProcessus(Processus processus) {
        this.processus.add(processus);
    }
}
