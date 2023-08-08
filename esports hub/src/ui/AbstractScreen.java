package ui;

import database.DatabaseConnectionHandler;
import popups.PlayerPopup;
import utils.CustomInputField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// A screen GUI template that facilitates the addition of tabs and tab content
public class AbstractScreen extends JPanel implements KeyListener {
    protected static DatabaseConnectionHandler dbHandler;
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final Color TAB_COLOR = new Color(30, 30, 30);
    public static final Color TAB_HIGHLIGHTED = new Color(70, 70, 70);

    public static final Color MAIN_COLOR = new Color(30, 30, 30);
    public static final Color SECOND_COLOR = new Color(70, 70, 70);
    public static final Color TEXT_COLOR = Color.WHITE;

    JPanel tabBar;
    ArrayList<JPanel> tabPanels;
    int visibleTabIndex;
    JPanel contentPanel;
    private JComboBox dropDown;
    private JComboBox attributeDrop;
    private CustomInputField input;

    public AbstractScreen() {
        dbHandler = new DatabaseConnectionHandler();
        visibleTabIndex = -1;
        tabPanels = new ArrayList<>();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(MAIN_COLOR);
        setLayout(new BorderLayout());
        add(setupTabBar(), BorderLayout.LINE_START);
        add(setupContentPanel(), BorderLayout.CENTER);
    }

    public ArrayList<JPanel> getTabPanels() {
        return tabPanels;
    }

    public DatabaseConnectionHandler getDbHandler() {
        return dbHandler;
    }

    public static void setColors(JComponent comp, String s) {
        comp.setForeground(TEXT_COLOR);
        if (s.equals("m"))
            comp.setBackground(MAIN_COLOR);
        else if (s.equals("s"))
            comp.setBackground(SECOND_COLOR);
    }

    private JPanel setupContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new OverlayLayout(contentPanel));
        contentPanel.setPreferredSize(new Dimension(SCREEN_WIDTH * 3 / 4, SCREEN_HEIGHT));
        contentPanel.setBackground(new Color(150, 150, 150));
        contentPanel.setAlignmentX(Box.RIGHT_ALIGNMENT);
        return contentPanel;
    }

    private JPanel setupTabBar() {
        JPanel sideBarParent = new JPanel(new BorderLayout());
        JPanel sideBar = new JPanel(new GridBagLayout());
        sideBarParent.add(sideBar, BorderLayout.CENTER);
        sideBar.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT));
        sideBar.setBackground(TAB_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        tabBar = new JPanel();
        tabBar.setLayout(new GridBagLayout());
        tabBar.setBackground(TAB_COLOR);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        sideBar.add(tabBar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        JPanel panel = new JPanel();
        sideBar.add(panel, gbc);
        panel.setBackground(TAB_COLOR);
        sideBarParent.add(memberSearch(), BorderLayout.SOUTH);

        return sideBarParent;
    }

    protected void addTab(String tabName, JPanel tabPanel) {
        JPanel tab = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel text = new JLabel(tabName);
        text.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        text.setForeground(Color.WHITE);
        tab.setBackground(TAB_COLOR);

        int currentSize = tabPanels.size();
        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayTab(currentSize);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(20, 20, 20, 0);
        tab.add(text, gbc);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tabBar.add(tab, gbc);

//        HomeScreen.createBorder(tabPanel, Color.RED);
        if (tabPanels.size() != 0) {
            tabPanel.setVisible(false);
        } else {
            tab.setBackground(TAB_HIGHLIGHTED);
            visibleTabIndex = 0;
        }

        tabPanels.add(tabPanel);
        contentPanel.add(tabPanel);
    }

    public void displayTab(int index) {
        if (visibleTabIndex != index) {
            tabPanels.get(visibleTabIndex).setVisible(false);
            tabBar.getComponent(visibleTabIndex).setBackground(TAB_COLOR);
            tabPanels.get(index).setVisible(true);
            tabBar.getComponent(index).setBackground(TAB_HIGHLIGHTED);
            visibleTabIndex = index;
        }
    }

    public static void setDefaultFont(JComponent comp, int fontSize) {
        comp.setForeground(AbstractScreen.TEXT_COLOR);
        comp.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, fontSize));
    }

    public static void setBoldFont(JComponent comp, int fontSize) {
        comp.setForeground(AbstractScreen.TEXT_COLOR);
        comp.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.BOLD, fontSize));
    }

    public JPanel memberSearch() {
        String[] options = {"Player", "Staff"};
        dropDown = new JComboBox(options);
        String[] attributes = {"t.tmid", "name","age"};
        attributeDrop = new JComboBox(attributes);
        input = new CustomInputField("");
        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Search Team Member");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(TEXT_COLOR);
        top.add(title, BorderLayout.NORTH);
        top.add(dropDown, BorderLayout.LINE_START);
        top.add(attributeDrop, BorderLayout.LINE_END);
        setColors(top, "m");
        JPanel memberSearch = new JPanel(new BorderLayout());
        memberSearch.add(top, BorderLayout.NORTH);
        memberSearch.add(input, BorderLayout.CENTER);
        input.addKeyListener(this);
        memberSearch.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH/4, 90));
        setColors(memberSearch, "m");
        return memberSearch;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            ArrayList<Integer> ids = dbHandler.getTeamMemberAttr((String)
                    dropDown.getSelectedItem(),(String) attributeDrop.getSelectedItem(), Integer.parseInt(input.getText()));
            new PlayerPopup(this, ids, (String)
                    dropDown.getSelectedItem());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
