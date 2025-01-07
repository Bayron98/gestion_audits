package dao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Standard implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static int idCounter = 1;
    private int id;
    private String description;
    private String reference;
    private List<Clause> clauses;

    public Standard() {
        this.id = idCounter++;
        this.clauses = new ArrayList<>();
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

    public List<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(List<Clause> clauses) {
        this.clauses = clauses;
    }

    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }
}
