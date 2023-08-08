package ui;

import tabs.AnalystSalesPanel;
import tabs.AnalystTeamsPanel;
import tabs.AnalystViewersPanel;

import javax.swing.*;
import java.awt.*;

public class AnalystScreen extends AbstractScreen {
    public static final Color SELECT_COLOR = new Color(200, 200, 200); // color for the view selection bar
    public static final int LIST_FONT_SIZE = 13; // font size for all text in lists
    public static final int HEADER_FONT_SIZE = 14; // font size for text in the view selection bar (at the top)
    public static final int SELECTION_FONT_SIZE = 12; // font size for selections in the view selection bar

    public AnalystScreen() {
        super();
        addTab("Sales", setupTicketsPanel());
        addTab("Team Performances", setupTeamsPanel());
        addTab("Top Viewers", setupViewersPanel());
    }

    private JPanel setupTicketsPanel() {
        return new AnalystSalesPanel(dbHandler);
    }


    private JPanel setupViewersPanel() {
        return new AnalystViewersPanel(dbHandler);
    }

    private JPanel setupTeamsPanel() {
        return new AnalystTeamsPanel(dbHandler);
    }

}
