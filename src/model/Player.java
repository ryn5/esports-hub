package model;

public class Player {
    private final int teamMemberID;
    private final String position;
    private final String alias;
    private int roleNum;

    public Player(int teamMemberID, String position, String alias) {
        this.teamMemberID = teamMemberID;
        this.position = position.substring(0, 3);
        this.alias = alias;
        roleNum = -1;
        calculateRoleNum();
    }

    private void calculateRoleNum() {
        if (position.equals("TOP"))
            roleNum = 0;
        if (position.equals("JNG"))
            roleNum = 1;
        if (position.equals("MID"))
            roleNum = 2;
        if (position.equals("ADC"))
            roleNum = 3;
        if (position.equals("SUP"))
            roleNum = 4;
        System.out.println(roleNum);
    }

    public int getRoleNum() {
        return roleNum;
    }

    public int getTeamMemberID() {
        return teamMemberID;
    }

    public String getPosition() {
        return position;
    }

    public String getAlias() {
        return alias;
    }
}
