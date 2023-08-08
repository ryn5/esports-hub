package model;

public abstract class SalesStruct {
    protected int totalViewers;
    protected float totalSales;

    public SalesStruct(int totalViewers, float totalSales) {
        this.totalViewers = totalViewers;
        this.totalSales = totalSales;
    }

    public int getTotalViewers() {
        return totalViewers;
    }

    public float getTotalSales() {
        return totalSales;
    }
}