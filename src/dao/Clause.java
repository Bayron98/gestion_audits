package dao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Clause implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static int idCounter = 1;
    private int id;
    private String description;
    private String reference;
    private List<Standard> standards;

    public Clause() {
        this.id = idCounter++;
        this.standards = new ArrayList<>();
    }

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public void addStandard(Standard standard) {
        this.standards.add(standard);
    }
}
