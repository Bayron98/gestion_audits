package dao;

import java.util.Date;

public class Audit {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private String intitule;
    private String status;
    private int systemexigenceId;
    private String type;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSystemexigenceId() {
        return systemexigenceId;
    }

    public void setSystemexigenceId(int systemexigenceId) {
        this.systemexigenceId = systemexigenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
