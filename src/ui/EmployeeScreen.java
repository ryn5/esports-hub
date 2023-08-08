package ui;

import tabs.EmployeeAchievementPanel;
import tabs.EmployeeArenaPanel;
import tabs.EmployeeGamePanel;
import tabs.EmployeeTeamPanel;

import javax.swing.*;

public class EmployeeScreen extends AbstractScreen {
    EmployeeTeamPanel teamPanel;


    public EmployeeScreen() {
        super();
        addTab("Games", setupGamesPanel());
        addTab("Teams", setupTeamsPanel());
        addTab("Achievements", setupAchievementsPanel());
        addTab("Arenas", setupArenasPanel());
        displayTab(0);
    }

    private JPanel setupAchievementsPanel() {
        EmployeeAchievementPanel panel = new EmployeeAchievementPanel(this, teamPanel);
        return panel.getPanel();
    }

    private JPanel setupArenasPanel() {
        EmployeeArenaPanel panel = new EmployeeArenaPanel(this);
        return panel.getPanel();
    }


    private JPanel setupGamesPanel() {
        EmployeeGamePanel panel = new EmployeeGamePanel(this);
        return panel.getPanel();
    }

    private JPanel setupTeamsPanel() {
        teamPanel = new EmployeeTeamPanel(this);
        return teamPanel.getPanel();
    }
}
