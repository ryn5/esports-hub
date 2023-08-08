package model;

public class RosterStruct {
    private final String teamName;
    private final String season;
    private final int year;
    private final int wins;
    private final int losses;
    private final float winRate;

    public RosterStruct(String teamName, String season, int year, int wins, int losses) {
        this.teamName = teamName;
        this.season = season;
        this.year = year;
        this.wins = wins;
        this.losses = losses;
        if (losses != 0) {
            this.winRate = (float) wins / (wins + losses);
        } else {
            this.winRate = 0;
        }
    }

    public String getTeamName() {
        return teamName;
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

    public float getWinRate() {
        return winRate;
    }

}
