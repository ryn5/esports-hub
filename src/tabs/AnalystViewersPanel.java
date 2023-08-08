package tabs;

import database.DatabaseConnectionHandler;
import model.Team;
import model.ViewerStatsStruct;
import ui.AbstractScreen;
import ui.AnalystScreen;
import ui.HomeScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnalystViewersPanel extends JPanel {
    public static final int NO_SETTING = 0;
    public static final int OVERALL_SETTING = 1;
    public static final int TEAM_SETTING = 2;
    public static final int ARENA_SETTING = 3;
    public static final int SUPERFAN_SETTING = 4;


    private DatabaseConnectionHandler dbHandler;
    private JPanel listPanelContent;
    private JLabel secondSelectionText;
    private JComboBox<String> secondSelectionBox;
    private JComboBox<String> viewSelectionBox;

    public AnalystViewersPanel(DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;
        setLayout(new BorderLayout());
        add(setupViewSelection(), BorderLayout.PAGE_START);
        add(setupListPanel(), BorderLayout.CENTER);
    }

    private JPanel setupViewSelection() {
        JPanel viewSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        viewSelectionPanel.setBackground(AnalystScreen.SELECT_COLOR);

        JPanel viewSelectionContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 8));
        viewSelectionContent.setBackground(AnalystScreen.SELECT_COLOR);

        JLabel viewSelectionText = new JLabel("View By:");
        viewSelectionText.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.HEADER_FONT_SIZE));
        viewSelectionContent.add(viewSelectionText);

        String[] selectList = {"Overall", "Team", "Arena", "Superfans"};
        viewSelectionBox = new JComboBox<>(selectList);
        viewSelectionBox.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.SELECTION_FONT_SIZE));
        viewSelectionBox.addItemListener(getViewSelectionBoxListener());
        viewSelectionContent.add(viewSelectionBox);

        viewSelectionPanel.add(viewSelectionContent);
        setupSecondSelection(viewSelectionPanel);

        return viewSelectionPanel;
    }

    private ItemListener getViewSelectionBoxListener() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selectedView = (String) viewSelectionBox.getSelectedItem();

                if (selectedView.equals("Overall")) {
                    secondSelectionText.setVisible(false);
                    secondSelectionBox.setVisible(false);
                    setView(OVERALL_SETTING, null);
                } else if (selectedView.equals("Arena")) {
                    secondSelectionText.setText("Arena: ");
                    secondSelectionText.setVisible(true);
                    secondSelectionBox.removeAllItems();
                    secondSelectionBox.setModel(new DefaultComboBoxModel<>(dbHandler.getArenaNames()));
                    secondSelectionBox.setVisible(true);
                    setView(ARENA_SETTING, (String) secondSelectionBox.getSelectedItem());
                } else {
                    ArrayList<Team> teams = dbHandler.getTeams();
                    ArrayList<String> teamNames = new ArrayList<>();
                    for (Team t : teams) {
                        teamNames.add(t.getName());
                    }
                    secondSelectionText.setText("Team: ");
                    secondSelectionText.setVisible(true);
                    secondSelectionBox.removeAllItems();
                    secondSelectionBox.setModel(new DefaultComboBoxModel<>(teamNames.toArray(new String[0])));
                    secondSelectionBox.setVisible(true);

                    if (selectedView.equals("Team")) {
                        setView(TEAM_SETTING, (String) secondSelectionBox.getSelectedItem());
                    } else {
                        setView(SUPERFAN_SETTING, (String) secondSelectionBox.getSelectedItem());
                    }
                }
            }
        };
    }

    private void setupSecondSelection(JPanel viewSelectionPanel) {
        JPanel secondSelectionContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 8));
        secondSelectionContent.setBackground(AnalystScreen.SELECT_COLOR);

        secondSelectionText = new JLabel("Name: ");
        secondSelectionText.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.HEADER_FONT_SIZE));
        secondSelectionText.setVisible(false);
        secondSelectionContent.add(secondSelectionText);

        secondSelectionBox = new JComboBox<>();
        secondSelectionBox.setVisible(false);
        secondSelectionBox.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.SELECTION_FONT_SIZE));
        secondSelectionBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selectedView = (String) viewSelectionBox.getSelectedItem();
                String selectedConstraint = (String) secondSelectionBox.getSelectedItem();
                switch (selectedView) {
                    case "Overall":
                        setView(OVERALL_SETTING, null);
                        break;
                    case "Team":
                        setView(TEAM_SETTING, selectedConstraint);
                        break;
                    case "Arena":
                        setView(ARENA_SETTING, selectedConstraint);
                        break;
                    case "Superfans":
                        setView(SUPERFAN_SETTING, selectedConstraint);
                        break;
                }
            }
        });
        secondSelectionContent.add(secondSelectionBox);

        viewSelectionPanel.add(secondSelectionContent);
    }


    private JPanel setupListPanel() {
        JPanel listPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        listPanelContent = new JPanel(new GridBagLayout());
        listPanelContent.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        listPanel.add(listPanelContent, gbc);

        setView(OVERALL_SETTING, null);

        JPanel listPanelFiller = new JPanel();
        listPanelFiller.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        listPanel.add(listPanelFiller, gbc);
        return listPanel;
    }

    private void setView(int setting, String constraint) {
        listPanelContent.removeAll();
        setViewHeader(setting);
        ArrayList<ViewerStatsStruct> viewerStats = dbHandler.getViewerStats(setting, constraint);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;

        for (int i = 0; i < viewerStats.size(); i++) {
            ViewerStatsStruct viewer = viewerStats.get(i);
            gbc.gridy = i + 2;

            JLabel nameLabel = new JLabel(viewer.getName());
            AnalystScreen.setDefaultFont(nameLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridx = 0;
            gbc.weightx = 0.7;
            gbc.insets = new Insets(10, 15, 10, 5);
            listPanelContent.add(nameLabel, gbc);

            if (setting != SUPERFAN_SETTING) {
                JLabel gamesLabel = new JLabel(String.valueOf(viewer.getGamesWatched()));
                AnalystScreen.setDefaultFont(gamesLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 1;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(10, 5, 10, 5);
                listPanelContent.add(gamesLabel, gbc);

                DecimalFormat format = new DecimalFormat("$0.00");
                String moneyText = format.format(viewer.getMoneySpent());

                JLabel moneyLabel = new JLabel(moneyText);
                AnalystScreen.setDefaultFont(moneyLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 2;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(10, 5, 10, 15);
                listPanelContent.add(moneyLabel, gbc);
            }
        }


        revalidate();
        repaint();
    }

    private void setViewHeader(int setting) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;

        JLabel nameLabel = new JLabel("Name: ");
        AnalystScreen.setBoldFont(nameLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 0;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(15, 15, 5, 5);
        listPanelContent.add(nameLabel, gbc);

        if (setting != SUPERFAN_SETTING) {
            JLabel gamesLabel = new JLabel("Games Watched: ");
            AnalystScreen.setBoldFont(gamesLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridx = 1;
            gbc.weightx = 0.2;
            gbc.insets = new Insets(15, 5, 5, 5);
            listPanelContent.add(gamesLabel, gbc);

            JLabel moneyLabel = new JLabel("Money Spent: ");
            AnalystScreen.setBoldFont(moneyLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridx = 2;
            gbc.weightx = 0.2;
            gbc.insets = new Insets(15, 5, 5, 15);
            listPanelContent.add(moneyLabel, gbc);
        }

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        listPanelContent.add(new JSeparator(), gbc);
    }

}
