package dao;

public class SystemeExigence {
    private int id;
    private int auditId;
    private int auditeurId;
    private int auditeurPrincipalId;
    private int autreexigenceId;
    private int clauseStandardId;
    private String constats;
    private String ecarts;
    private boolean exclu;
    private String motifExclusion;
    private String status;
    private int systememanagementId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public int getAuditeurId() {
        return auditeurId;
    }

    public void setAuditeurId(int auditeurId) {
        this.auditeurId = auditeurId;
    }

    public int getAuditeurPrincipalId() {
        return auditeurPrincipalId;
    }

    public void setAuditeurPrincipalId(int auditeurPrincipalId) {
        this.auditeurPrincipalId = auditeurPrincipalId;
    }

    public int getAutreexigenceId() {
        return autreexigenceId;
    }

    public void setAutreexigenceId(int autreexigenceId) {
        this.autreexigenceId = autreexigenceId;
    }

    public int getClauseStandardId() {
        return clauseStandardId;
    }

    public void setClauseStandardId(int clauseStandardId) {
        this.clauseStandardId = clauseStandardId;
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

    public boolean isExclu() {
        return exclu;
    }

    public void setExclu(boolean exclu) {
        this.exclu = exclu;
    }

    public String getMotifExclusion() {
        return motifExclusion;
    }

    public void setMotifExclusion(String motifExclusion) {
        this.motifExclusion = motifExclusion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSystememanagementId() {
        return systememanagementId;
    }

    public void setSystememanagementId(int systememanagementId) {
        this.systememanagementId = systememanagementId;
    }
}
