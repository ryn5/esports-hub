package model;

public class Ticket {
    private final int ticketNum;
    private final int viewerID;
    private final int gameID;
    private final int arenaID;
    private final int seatNum;

    public Ticket(int ticketNum, int viewerID, int gameID, int arenaID, int seatNum) {
        this.ticketNum = ticketNum;
        this.viewerID = viewerID;
        this.gameID = gameID;
        this.arenaID = arenaID;
        this.seatNum = seatNum;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public int getViewerID() {
        return viewerID;
    }

    public int getGameID() {
        return gameID;
    }

    public int getArenaID() {
        return arenaID;
    }

    public int getSeatNum() {
        return seatNum;
    }
}
