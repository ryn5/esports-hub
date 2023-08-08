package model;

public class Arena {
    private final int arenaID;
    private final String name;
    private final String city;
    private final int capacity;

    public Arena(int arenaID, String name, String city, int capacity) {
        this.arenaID = arenaID;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
    }

    public int getCapacity() {return capacity;}
    public int getArenaID() {
        return arenaID;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
