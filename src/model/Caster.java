package model;

public class Caster {
    private final int casterID;
    private final String name;

    public Caster(int casterID, String name) {
        this.casterID = casterID;
        this.name = name;
    }

    public int getCasterID() {
        return casterID;
    }

    public String getName() {
        return name;
    }
}
