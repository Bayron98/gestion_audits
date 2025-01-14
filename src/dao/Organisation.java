package dao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Organisation implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String adresse;
    private List<Site> sites; // Ajouter une liste des sites

    public Organisation() {
        this.sites = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public void addSite(Site site) {
        this.sites.add(site);
    }
}
