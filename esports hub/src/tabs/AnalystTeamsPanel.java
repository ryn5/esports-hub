package tabs;

import database.DatabaseConnectionHandler;
import model.RosterStruct;
import ui.AbstractScreen;
import ui.AnalystScreen;
import ui.HomeScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class AnalystTeamsPanel extends JPanel {
    private DatabaseConnectionHandler dbHandler;

    private JComboBox<String> seasonSelectBox;
    private JComboBox<Integer> yearSelectBox;
    private JPanel teamPanelContent;
    private Integer selectedYear;
    private String selectedSeason;

    public AnalystTeamsPanel(DatabaseConnectionHandler dbHandler) {
        this.dbHandler = dbHandler;
        setLayout(new BorderLayout());
        add(setupSelectionPanel(), BorderLayout.PAGE_START);
        add(setupTeamPanel(), BorderLayout.CENTER);
    }

    private JPanel setupSelectionPanel() {
        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        selectPanel.setBackground(AnalystScreen.SELECT_COLOR);

        JPanel yearSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 8));
        yearSelectPanel.setBackground(AnalystScreen.SELECT_COLOR);

        JLabel yearSelectText = new JLabel("Year: ");
        yearSelectText.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.HEADER_FONT_SIZE));
        yearSelectPanel.add(yearSelectText);

        yearSelectBox = new JComboBox<>(dbHandler.getYears());
        yearSelectBox.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.SELECTION_FONT_SIZE));
        yearSelectBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Integer selectedYear = (Integer) yearSelectBox.getSelectedItem();
                String selectedSeason = (String) seasonSelectBox.getSelectedItem();
                seasonSelectBox.removeAllItems();
                String[] newSeasons = dbHandler.getSeasons(selectedYear);
                seasonSelectBox.setModel(new DefaultComboBoxModel<>(newSeasons));

                if (Arrays.asList(newSeasons).contains(selectedSeason)) {
                    seasonSelectBox.setSelectedItem(selectedSeason);
                }

                updateTeamPanel(selectedYear, (String) seasonSelectBox.getSelectedItem());
            }
        });
        yearSelectPanel.add(yearSelectBox);
        selectPanel.add(yearSelectPanel);

        JPanel seasonSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        seasonSelectPanel.setBackground(AnalystScreen.SELECT_COLOR);

        JLabel seasonSelectText = new JLabel("Season: ");
        seasonSelectText.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.HEADER_FONT_SIZE));
        seasonSelectPanel.add(seasonSelectText);

        seasonSelectBox = new JComboBox<>(dbHandler.getSeasons((Integer) yearSelectBox.getSelectedItem()));
        seasonSelectBox.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, AnalystScreen.SELECTION_FONT_SIZE));
        seasonSelectBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Integer selectedYear = (Integer) yearSelectBox.getSelectedItem();
                String selectedSeason = (String) seasonSelectBox.getSelectedItem();
                updateTeamPanel(selectedYear, selectedSeason);
            }
        });
        seasonSelectPanel.add(seasonSelectBox);
        selectPanel.add(seasonSelectPanel);

        return selectPanel;
    }

    private JPanel setupTeamPanel() {
        JPanel teamPanel = new JPanel(new GridBagLayout());
        teamPanel.setBackground(AbstractScreen.SECOND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        teamPanelContent = new JPanel(new GridBagLayout());
        teamPanelContent.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        teamPanel.add(teamPanelContent, gbc);

        JPanel teamPanelFiller = new JPanel();
        teamPanelFiller.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        teamPanel.add(teamPanelFiller, gbc);

        updateTeamPanel(
                (Integer) yearSelectBox.getSelectedItem(),
                (String) seasonSelectBox.getSelectedItem()
        );

        return teamPanel;
    }

    private void setupTeamPanelHeader() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;

        JLabel rankLabel = new JLabel("Rank");
        AnalystScreen.setBoldFont(rankLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 15, 5, 5);
        teamPanelContent.add(rankLabel, gbc);

        JLabel nameLabel = new JLabel("Name");
        AnalystScreen.setBoldFont(nameLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new Insets(15, 5, 5, 5);
        teamPanelContent.add(nameLabel, gbc);

        JLabel winsLabel = new JLabel("Wins");
        AnalystScreen.setBoldFont(winsLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        teamPanelContent.add(winsLabel, gbc);

        JLabel lossesLabel = new JLabel("Losses");
        AnalystScreen.setBoldFont(lossesLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 3;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        teamPanelContent.add(lossesLabel, gbc);

        JLabel winRateLabel = new JLabel("Win Rate");
        AnalystScreen.setBoldFont(winRateLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 4;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 15);
        teamPanelContent.add(winRateLabel, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        teamPanelContent.add(sep, gbc);
    }


    private void updateTeamPanel(Integer year, String season) {
        boolean isInitialized = (year != null) && (season != null);
        if (isInitialized && (!year.equals(selectedYear) || !season.equals(selectedSeason))) {
            teamPanelContent.removeAll();
            setupTeamPanelHeader();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.PAGE_START;

            ArrayList<RosterStruct> rosters = dbHandler.getRostersBySeasonYear(year, season);
            for (int i = 0; i < rosters.size(); i++) {
                RosterStruct roster = rosters.get(i);
                JLabel rankLabel = new JLabel(String.valueOf(i+1));
                AnalystScreen.setDefaultFont(rankLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridy = i + 2;
                gbc.gridx = 0;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(10, 15, 10, 5);
                teamPanelContent.add(rankLabel, gbc);

                JLabel nameLabel = new JLabel(roster.getTeamName());
                AnalystScreen.setDefaultFont(nameLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 1;
                gbc.weightx = 0.8;
                gbc.insets = new Insets(10, 5, 10, 5);
                teamPanelContent.add(nameLabel, gbc);

                JLabel winsLabel = new JLabel(String.valueOf(roster.getWins()));
                AnalystScreen.setDefaultFont(winsLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 2;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(10, 5, 10, 5);
                teamPanelContent.add(winsLabel, gbc);

                JLabel lossesLabel = new JLabel(String.valueOf(roster.getLosses()));
                AnalystScreen.setDefaultFont(lossesLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 3;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(10, 5, 10, 5);
                teamPanelContent.add(lossesLabel, gbc);

                DecimalFormat format = new DecimalFormat("0.0%");
                String winRate = format.format(roster.getWinRate());

                JLabel winRateLabel = new JLabel(winRate);
                AnalystScreen.setDefaultFont(winRateLabel, AnalystScreen.LIST_FONT_SIZE);
                gbc.gridx = 4;
                gbc.weightx = 0.2;
                gbc.insets = new Insets(10, 5, 10, 15);
                teamPanelContent.add(winRateLabel, gbc);
            }

            revalidate();
            repaint();

            selectedYear = year;
            selectedSeason = season;
        }
    }



}
