package dao;

import java.io.Serializable;

public class Processus implements Serializable {
    private int id;
    private String description;
    private String name;
    private Responsable responsable;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Responsable getResponsable() {
        return responsable;
    }
    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }
}
