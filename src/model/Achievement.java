package model;

public class Achievement {
    private final String season;
    private final int year;
    private final int placement;
    private final int teamID;

    public Achievement(String season, int year, int placement, int teamID) {
        this.season = season;
        this.year = year;
        this.placement = placement;
        this.teamID = teamID;
    }

    public String getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public int getPlacement() {
        return placement;
    }

    public int getTeamID() {
        return teamID;
    }
}
