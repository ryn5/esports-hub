package model;

public class TeamMember {
    private final int teamMemberID;
    private final String name;
    private final int age;

    public TeamMember(int teamMemberID, String name, int age) {
        this.teamMemberID = teamMemberID;
        this.name = name;
        this.age = age;
    }

    public int getTeamMemberID() {
        return teamMemberID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
