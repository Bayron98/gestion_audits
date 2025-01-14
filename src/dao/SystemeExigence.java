package dao;

import java.io.Serializable;

public class SystemeExigence implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String constats;
    private String ecarts;
    private String status; // Non évalué, Conforme, Non conforme, Partiellement conforme, Exclusion, En cours de traitement, Clôturé
    private Clause clause;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstats() {
        return constats;
    }

    public void setConstats(String constats) {
        this.constats = constats;
    }

    public String getEcarts() {
        return ecarts;
    }

    public void setEcarts(String ecarts) {
        this.ecarts = ecarts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Clause getClause() {
        return clause;
    }

    public void setClause(Clause clause) {
        this.clause = clause;
    }
}
