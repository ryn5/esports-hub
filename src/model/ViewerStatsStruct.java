package model;

public class ViewerStatsStruct {
    private final String name;
    private final int gamesWatched;
    private final float moneySpent;

    public ViewerStatsStruct(String name, int gamesWatched, float moneySpent) {
        this.name = name;
        this.gamesWatched = gamesWatched;
        this.moneySpent = moneySpent;
    }

    public String getName() {
        return name;
    }

    public int getGamesWatched() {
        return gamesWatched;
    }

    public float getMoneySpent() {
        return moneySpent;
    }
}
