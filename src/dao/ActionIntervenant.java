package dao;

public class ActionIntervenant {
    private int id;
    private int actionId;
    private int intervenantId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getIntervenantId() {
        return intervenantId;
    }

    public void setIntervenantId(int intervenantId) {
        this.intervenantId = intervenantId;
    }
}
