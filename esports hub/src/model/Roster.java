package model;

public class Roster {
    private final int teamID;
    private final String season;
    private final int year;
    private int wins;
    private int losses;

    public Roster(int teamID, String season, int year, int wins, int losses) {
        this.teamID = teamID;
        this.season = season;
        this.year = year;
        this.wins = wins;
        this.losses = losses;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
