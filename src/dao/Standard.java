package dao;

import java.util.List;
import java.util.ArrayList;

public class Standard {
    private int id;
    private String description;
    private String reference;
    private List<Clause> clauses;

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
        if (this.clauses == null) {
            this.clauses = clauses;
        } else {
            this.clauses.addAll(clauses);
        }
    }

    public void addClause(Clause clause) {
        if (this.clauses == null) {
            this.clauses = new ArrayList<>();
        }
        this.clauses.add(clause);
    }
}
