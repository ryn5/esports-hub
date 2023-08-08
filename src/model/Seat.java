package model;

public class Seat {
    private final int arenaID;
    private final int seatNum;
    private final double price;

    public Seat(int arenaID, int seatNum, double price) {
        this.arenaID = arenaID;
        this.seatNum = seatNum;
        this.price = price;
    }

    public int getArenaID() {
        return arenaID;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public double getPrice() {
        return price;
    }
}
