package popups;

import tabs.Panel;
import ui.AbstractScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Represent frames who edit a restaurant in a MainFrame
public abstract class Popup extends JFrame implements ActionListener {
    protected static final int POPUP_WIDTH = 250;
    protected static final int POPUP_HEIGHT = 200;
    protected tabs.Panel editor;
    protected AbstractScreen otherEditor;
    protected JPanel main;
    protected int sequence;
    protected int gameID;
    protected int viewerID;

    // REQUIRES: editor.getSelectedR != null
    // EFFECTS: constructs a RestaurantEditor gui with a given MainFrame editor that operates on editor's mainList
    public Popup(Panel e, String name) {
        super(name);
        this.editor = e;
        sequence = 0;
        initializeGraphics();
    }

    public Popup(AbstractScreen e, String name) {
        super(name);
        otherEditor = e;
        sequence = 0;
        initializeGraphics();
    }
    // used by BuyTicketPopup
    public Popup(Panel e, String name, int gameID, int viewerID) {
        super(name);
        this.editor = e;
        sequence = 0;
        this.gameID = gameID;
        this.viewerID = viewerID;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes the gui
    protected void initializeGraphics() {
        main = new JPanel();
        add(main, BorderLayout.CENTER);
        AbstractScreen.setColors(main, "m");
        setSize(new Dimension(POPUP_WIDTH, POPUP_HEIGHT));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(Popup.DISPOSE_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: increases sequence by one and returns it
    protected int advanceSequence(String text) {
        return sequence++;
    }

    protected abstract void initializePrompts();
}
