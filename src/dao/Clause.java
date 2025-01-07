package dao;

import java.util.List;
import java.util.ArrayList;

public class Clause {
    private int id;
    private String description;
    private String reference;
    private List<Standard> standards;

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

    public void addStandard(Standard standard) {
        if (this.standards == null) {
            this.standards = new ArrayList<>();
        }
        this.standards.add(standard);
    }
}
