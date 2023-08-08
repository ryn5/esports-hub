package model;

public class Viewer {
    private final int viewerID;
    private final String name;

    public Viewer(int viewerID, String name) {
        this.viewerID = viewerID;
        this.name = name;
    }

    public int getViewerID() {
        return viewerID;
    }

    public String getName() {
        return name;
    }
}
