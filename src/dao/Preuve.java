package dao;

public class Preuve {
    private int id;
    private String name;
    private int organisationId;
    private int systemexigenceId;
    private String url;

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

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public int getSystemexigenceId() {
        return systemexigenceId;
    }

    public void setSystemexigenceId(int systemexigenceId) {
        this.systemexigenceId = systemexigenceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
