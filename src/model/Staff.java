package model;

public class Staff {
    private final int teamMemberID;
    private final String role;
    private String name;

    public Staff(int teamMemberID, String name, String role) {
        this.teamMemberID = teamMemberID;
        this.name = name;
        this.role = role;
    }

    public String getName() {return name;}

    public int getTeamMemberID() {
        return teamMemberID;
    }

    public String getRole() {
        return role;
    }
}
