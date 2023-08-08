package tabs;

import database.DatabaseConnectionHandler;
import model.SalesStruct;
import ui.AbstractScreen;
import ui.AnalystScreen;
import ui.HomeScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnalystSalesPanel extends JPanel {
    public static final int NO_SETTING = 0;
    public static final int GAME_SETTING = 1;
    public static final int TEAM_SETTING = 2;
    public static final int ARENA_SETTING = 3;

    private DatabaseConnectionHandler dbHandler;
    private int currentView;
    private JPanel listPanelContent;

    public AnalystSalesPanel(DatabaseConnectionHandler dbHandler) {
        currentView = NO_SETTING;
        this.dbHandler = dbHandler;
        setLayout(new BorderLayout());
        add(setupViewSelection(), BorderLayout.PAGE_START);
        add(setupListPanel(), BorderLayout.CENTER);
    }

    private JPanel setupListPanel() {
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setForeground(AbstractScreen.TEXT_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        listPanelContent = new JPanel(new GridBagLayout());
        listPanelContent.setBackground(AbstractScreen.SECOND_COLOR);
        listPanelContent.setForeground(AbstractScreen.TEXT_COLOR);

        switchView(GAME_SETTING);

        JScrollPane listPanelScroller = new JScrollPane(listPanelContent);
        listPanelScroller.setBorder(new EmptyBorder(0, 0, 0, 0));
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(listPanelScroller, gbc);

        JPanel listPanelFiller = new JPanel();
        listPanelFiller.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(listPanelFiller, gbc);

        return listPanel;
    }

    private void setupListPanelHeader(int setting) {
        JLabel firstLabel;
        JLabel secondLabel;

        if (setting == GAME_SETTING) {
            firstLabel = new JLabel("Game: ");
            secondLabel = new JLabel("Date: ");
        } else if (setting == TEAM_SETTING) {
            firstLabel = new JLabel("Team: ");
            secondLabel = new JLabel("Total Games: ");
        } else {
            firstLabel = new JLabel("Arena: ");
            secondLabel = new JLabel("Location: ");
        }

        GridBagConstraints gbc = new GridBagConstraints();

        AbstractScreen.setBoldFont(firstLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 15, 5, 5);
        listPanelContent.add(firstLabel, gbc);

        AbstractScreen.setBoldFont(secondLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        listPanelContent.add(secondLabel, gbc);

        JLabel totalViewers = new JLabel("Viewers: ");
        AbstractScreen.setBoldFont(totalViewers, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        listPanelContent.add(totalViewers, gbc);

        JLabel totalSales = new JLabel("Sales: ");
        AbstractScreen.setBoldFont(totalSales, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 3;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 15);
        listPanelContent.add(totalSales, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        listPanelContent.add(sep, gbc);
    }

    private JPanel setupViewSelection() {
        JPanel viewBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        viewBar.setBackground(AnalystScreen.SELECT_COLOR);

        JPanel viewInnerBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 8));
        viewInnerBar.setBackground(AnalystScreen.SELECT_COLOR);

        viewBar.add(viewInnerBar);

        JLabel viewText = new JLabel("View By:");
        viewText.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.HEADER_FONT_SIZE));
        viewText.setForeground(Color.BLACK);
        viewInnerBar.add(viewText);

        String[] views = {"Game", "Team", "Arena"};
        JComboBox<String> viewSelect = new JComboBox<>(views);
        viewSelect.setEditable(false);
        viewSelect.setForeground(Color.BLACK);
        viewSelect.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.SELECTION_FONT_SIZE));
        viewSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String view = (String) e.getItemSelectable().getSelectedObjects()[0];
                if (view.equals("Game")) {
                    switchView(GAME_SETTING);
                } else if (view.equals("Team")) {
                    switchView(TEAM_SETTING);
                } else {
                    switchView(ARENA_SETTING);
                }
            }
        });

        viewInnerBar.add(viewSelect);

        return viewBar;
    }

    private void switchView(int view) {
        if (currentView != view) {
            ArrayList<SalesStruct> salesList;
            listPanelContent.removeAll();
            GridBagConstraints gbc = new GridBagConstraints();

            if (view == GAME_SETTING) {
                salesList = dbHandler.getGameSales();
            } else if (view == TEAM_SETTING) {
                salesList = dbHandler.getTeamSales();
            } else {
                salesList = dbHandler.getArenaSales();
            }

            setupListPanelHeader(view);

            for (int i = 0; i < salesList.size(); i++) {
                SalesStruct sale = salesList.get(i);
                String stringOne;
                String stringTwo;

                if (view == GAME_SETTING) {
                    stringOne = ((GameSalesStruct) sale).getGameName();
                    stringTwo = ((GameSalesStruct) sale).getDay().toString();
                } else if (view == TEAM_SETTING) {
                    stringOne = ((TeamSalesStruct) sale).getTeamName();
                    stringTwo = String.valueOf(((TeamSalesStruct) sale).getTotalGames());
                } else {
                    stringOne = ((ArenaSalesStruct) sale).getArenaName();
                    stringTwo = ((ArenaSalesStruct) sale).getCity();
                }

                JLabel labelOne = new JLabel(stringOne);
                AbstractScreen.setDefaultFont(labelOne, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridy = i + 2;
                gbc.gridx = 0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;
                gbc.weightx = 0.8;
                gbc.anchor = GridBagConstraints.PAGE_START;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(10, 15, 10, 5);
                listPanelContent.add(labelOne, gbc);

                JLabel labelTwo = new JLabel(stringTwo);
                AbstractScreen.setDefaultFont(labelTwo, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 1;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(5, 5, 5, 5);
                listPanelContent.add(labelTwo, gbc);

                JLabel totalViewers = new JLabel(String.valueOf(sale.getTotalViewers()));
                AbstractScreen.setDefaultFont(totalViewers, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridy = i + 2;
                gbc.gridx = 2;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.BOTH;
                listPanelContent.add(totalViewers, gbc);

                DecimalFormat format = new DecimalFormat("#.00");
                String salesString = "$" + format.format(sale.getTotalSales());
                JLabel totalSales = new JLabel(salesString);
                AbstractScreen.setDefaultFont(totalSales, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 3;
                gbc.insets = new Insets(5, 5, 5, 15);
                listPanelContent.add(totalSales, gbc);
            }
        }

        revalidate();
        repaint();

        currentView = view;
    }


    // Start of SalesStruct implementations
    // SalesStruct is a class used for organizing query information for easier transport
    public static class GameSalesStruct extends SalesStruct {
        private final String gameName;
        private final Date day;

        public GameSalesStruct(String gameName, Date day, int totalViewers, float totalSales) {
            super(totalViewers, totalSales);
            this.gameName = gameName;
            this.day = day;
        }

        public String getGameName() {
            return gameName;
        }

        public Date getDay() {
            return day;
        }
    }

    public static class TeamSalesStruct extends SalesStruct {
        private final String teamName;
        private final int totalGames;

        public TeamSalesStruct(String gameName, int totalGames, int totalViewers, float totalSales) {
            super(totalViewers, totalSales);
            this.teamName = gameName;
            this.totalGames = totalGames;
        }

        public String getTeamName() {
            return teamName;
        }

        public int getTotalGames() {
            return totalGames;
        }
    }

    public static class ArenaSalesStruct extends SalesStruct {
        private final String arenaName;
        private final String city;

        public ArenaSalesStruct(String arenaName, String city, int totalViewers, float totalSales) {
            super(totalViewers, totalSales);
            this.arenaName = arenaName;
            this.city = city;
        }

        public String getArenaName() {
            return arenaName;
        }

        public String getCity() {
            return city;
        }
    }
}
