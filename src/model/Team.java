package model;

public class Team {
    private final int teamID;
    private final String name;
    private String owner;

    public Team(int teamID, String name, String owner) {
        this.teamID = teamID;
        this.name = name;
        this.owner = owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
