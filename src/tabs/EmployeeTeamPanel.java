package tabs;

import model.*;
import popups.AddTeamPopup;
import ui.AbstractScreen;
import ui.HomeScreen;
import utils.CustomButton;
import utils.CustomInputField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class EmployeeTeamPanel extends Panel {
    ArrayList<JPanel> teamPanels;
    JList teamBar;

    DefaultListModel<String> teamNames;
    int visibleTabIndex;
    JPanel contentPanel;

    public EmployeeTeamPanel(AbstractScreen parent) {
        super(parent);
        teamPanels = new ArrayList<>();
        panel.add(setupTeamBar(), BorderLayout.LINE_START);
        panel.add(setupContentPanel(), BorderLayout.CENTER);
        addAllTeams();
    }

    public DefaultListModel<String> getTeamNames() {
        return teamNames;
    }

    private void addAllTeams() {
        ArrayList<Team> teamNames =  parent.getDbHandler().getTeams();
        for (Team team : teamNames) {
            addTab(team);
            System.out.println(team.getTeamID());
        }
    }

    private JPanel setupTeamPanel(Team team) {
        JPanel panel = new JPanel(new BorderLayout());
        JButton checkAchievements = new JButton();
        panel.add(checkAchievements);
        checkAchievements.setVisible(false);
        panel.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, AbstractScreen.SCREEN_HEIGHT-9));
        JPanel titleContainer = initTitle(team, panel);
        panel.add(titleContainer, BorderLayout.NORTH);
        JPanel mainInfo = new JPanel(new BorderLayout());
        DefaultListModel<JPanel> achievements = new DefaultListModel<>();
        initAchievements(team, achievements);
        ArrayList<String> t = new ArrayList<>();
        for (Achievement a : parent.getDbHandler().getTeamAchievements(team.getTeamID())) {
            t.add(String.valueOf(a.getPlacement()));
        }

        JList<JPanel> tempList = new JList(achievements);
        checkAchievements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pressed");
                initAchievements(team, achievements);
            }
        });
        tempList.setFixedCellHeight(80);
        tempList.setFixedCellWidth(100);
        tempList.setSelectedIndex(-1);
        tempList.setVisibleRowCount(1);
        tempList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        tempList.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 120));
        tempList.setBorder(new MatteBorder(20,0,20,0, AbstractScreen.MAIN_COLOR));
        tempList.setCellRenderer(new ListCellRenderer<JPanel>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
                return value;
            }
        });
        JScrollPane achievementList = new JScrollPane(tempList, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tempList.setBackground(new Color(52, 52, 52));
        achievementList.setBorder(new LineBorder(AbstractScreen.MAIN_COLOR));
        mainInfo.add(achievementList, BorderLayout.NORTH);
        ArrayList<Roster> rosters = parent.getDbHandler().getRosters(team.getTeamID());
        mainInfo.add(addRosters(rosters, team), BorderLayout.CENTER);
        panel.add(mainInfo, BorderLayout.CENTER);
        return panel;
    }

    private DefaultListModel<JPanel> initAchievements(Team team, DefaultListModel<JPanel> achievements) {
        ArrayList<Achievement> tempA = parent.getDbHandler().getTeamAchievements(team.getTeamID());
        achievements.clear();
        for (Achievement a : tempA) {
            JPanel temp = new JPanel(new BorderLayout());
            AbstractScreen.setColors(temp, "s");
            JPanel tempT = new JPanel();
            JLabel season = new JLabel(a.getSeason() + " " + a.getYear());
            season.setHorizontalAlignment(SwingConstants.CENTER);
            season.setForeground(AbstractScreen.TEXT_COLOR);
            tempT.add(season);
            AbstractScreen.setColors(tempT, "m");
            temp.add(tempT, BorderLayout.NORTH);
            JLabel placement = new JLabel("Placement: " + a.getPlacement());
            placement.setHorizontalAlignment(SwingConstants.CENTER);
            placement.setVerticalAlignment(SwingConstants.CENTER);
            placement.setForeground(AbstractScreen.TEXT_COLOR);
            temp.add(placement, BorderLayout.CENTER);
            achievements.addElement(temp);
        }
        return achievements;
    }

    private Component addRosters(ArrayList<Roster> rosters, Team team) {
        JPanel rosterContainer = new JPanel(new BorderLayout());
        JPanel addRosterPanel = new JPanel(new BorderLayout());
        addRosterPanel.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 3 + 30, 170));
        addRosterPanel.setBorder(new MatteBorder(10, 0,0,10,AbstractScreen.MAIN_COLOR));
        JLabel addRosterTitle = new JLabel("Add Roster");
        addRosterTitle.setPreferredSize(new Dimension( AbstractScreen.SCREEN_WIDTH / 3, 40));
        addRosterTitle.setForeground(AbstractScreen.TEXT_COLOR);
        addRosterTitle.setHorizontalAlignment(SwingConstants.CENTER);
        addRosterPanel.add(addRosterTitle, BorderLayout.NORTH);
        AbstractScreen.setColors(addRosterPanel, "m");
        JPanel addRosterMain = new JPanel(new BorderLayout());
        JPanel addRosterInputs = new JPanel(new GridLayout(0, 1));
        JPanel addRosterLabels = new JPanel(new GridLayout(0, 1));
        addRosterLabels.setPreferredSize(new Dimension(80, 60));
        AbstractScreen.setColors(addRosterInputs, "m");
        AbstractScreen.setColors(addRosterLabels, "m");
        String[] inputs = {"Season", "Year", "Member IDs"};
        for (String i : inputs) {
            JLabel label = new JLabel(i);
            label.setForeground(AbstractScreen.TEXT_COLOR);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            label.setPreferredSize(new Dimension(100, 20));
            addRosterLabels.add(label);
            JTextField input;
            if (i.equals("Member IDs"))
                input = new CustomInputField("id1 id2 ...");
            else
                input = new CustomInputField("");
            input.setBorder(new MatteBorder(2, 10, 2, 20, AbstractScreen.MAIN_COLOR));
            input.setPreferredSize(new Dimension(addRosterInputs.getWidth()-20, 20));
            addRosterInputs.add(input);
        }
        addRosterMain.add(addRosterLabels, BorderLayout.LINE_START);
        addRosterMain.add(addRosterInputs, BorderLayout.CENTER);
        addRosterPanel.add(addRosterMain, BorderLayout.CENTER);
        JPanel rosterPanel = printRosters(rosters);
        JScrollPane rosterScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rosterScroll.setViewportView(rosterPanel);
        rosterScroll.setBorder(new EmptyBorder(0,0,0,0));
        JButton submitRoster = new CustomButton("Add");
        submitRoster.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 3, 40));
        submitRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Roster newR = new Roster(team.getTeamID(), ((JTextField) addRosterInputs.getComponent(1)).getText(),
                        Integer.parseInt(((JTextField) addRosterInputs.getComponent(3)).getText()), 0, 0);
                parent.getDbHandler().addRoster(newR, ((JTextField) addRosterInputs.getComponent(5)).getText().split(" "));
                JPanel newRosterPanel = printRosters(parent.getDbHandler().getRosters(team.getTeamID()));
                rosterScroll.setViewportView(newRosterPanel);
                rosterScroll.revalidate();
                rosterScroll.repaint();
            }
        });
        addRosterPanel.add(submitRoster, BorderLayout.SOUTH);
        rosterContainer.add(addRosterPanel, BorderLayout.NORTH);
        rosterContainer.add(rosterScroll, BorderLayout.CENTER);
        return rosterContainer;
    }

    private JPanel printRosters(ArrayList<Roster> rosters) {
        JPanel rosterList = new JPanel();
        rosterList.setLayout(new BoxLayout(rosterList,BoxLayout.Y_AXIS));
        AbstractScreen.setColors(rosterList, "m");
        rosterList.setSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, AbstractScreen.SCREEN_HEIGHT));

        for (Roster r: rosters) {
            JPanel memberPanel = initRosterDefault(rosterList, r);
            initRosterMembers(r, memberPanel);
        }
        return rosterList;
    }

    private void initRosterMembers(Roster r, JPanel memberPanel) {
        memberPanel.removeAll();
        String[] labels = {"ID", "Name/Alias", "Role"};
        for (String l : labels) {
            JLabel temp = new JLabel(l);
            memberPanel.add(temp);
        }
        ArrayList<Player> players = parent.getDbHandler().getRosterPlayers(r.getTeamID(), r.getSeason(), r.getYear());
        for (Player p : players) {
            JLabel tempI = new JLabel(String.valueOf(p.getTeamMemberID()));
            memberPanel.add(tempI);
            JLabel tempA = new JLabel(p.getAlias());
            memberPanel.add(tempA);
            JLabel tempP = new JLabel(p.getPosition());
            memberPanel.add(tempP);
        }
        ArrayList<Staff> staff = parent.getDbHandler().getRosterStaff(r.getTeamID(), r.getSeason(), r.getYear());
        for (Staff s : staff) {
            JLabel tempI = new JLabel(String.valueOf(s.getTeamMemberID()));
            memberPanel.add(tempI);
            JLabel tempN = new JLabel(s.getName());
            memberPanel.add(tempN);
            JLabel tempR = new JLabel(s.getRole());
            memberPanel.add(tempR);
        }
        int totalHeight = players.size()*30 + staff.size()*30 + 30*2;
        memberPanel.setMaximumSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2, totalHeight));
        memberPanel.revalidate();
        memberPanel.repaint();
    }

    private JPanel initRosterDefault(JPanel rosterList, Roster r) {
        JPanel rosterPanel = new JPanel(new BorderLayout());
        JPanel memberPanel = new JPanel(new GridLayout(0, 3));
        String[] labels = {"ID", "Name/Alias", "Role"};
        for (String l : labels) {
            JLabel temp = new JLabel(l);
            memberPanel.add(temp);
        }
        rosterPanel.add(memberPanel, BorderLayout.CENTER);
        rosterPanel.setBorder(new LineBorder(AbstractScreen.MAIN_COLOR, 10));
        JLabel title = new JLabel(r.getSeason() + " " + r.getYear());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 3, 20));
        rosterList.add(rosterPanel);
        JLabel wins = new JLabel("Wins: " + r.getWins());
        AbstractScreen.setColors(wins, "s");
        JPanel winsContainer = new JPanel();
        winsContainer.add(wins);
        winsContainer.setBackground(new Color(0, 105, 0));
        JLabel losses = new JLabel("Losses: " + r.getLosses());
        AbstractScreen.setColors(losses, "s");
        JPanel lossContainer = new JPanel();
        lossContainer.add(losses);
        lossContainer.setBackground(new Color(134, 8, 8));
        JPanel wl = new JPanel(new GridLayout(0, 4));
        wl.add(winsContainer);
        JButton addWin = new CustomButton("+", "s");
        addWin.setPreferredSize(new Dimension(20, 20));
        addWin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r.setWins(r.getWins() + 1);
                parent.getDbHandler().updateRoster("wins", r.getTeamID(), r.getSeason(), r.getYear(), r.getWins());
                wins.setText("Wins: " + r.getWins());
            }
        });
        wl.add(addWin);
        JButton addLoss = new CustomButton("+", "s");
        addLoss.setPreferredSize(new Dimension(20, 20));
        addLoss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r.setLosses(r.getLosses() + 1);
                parent.getDbHandler().updateRoster("losses", r.getTeamID(), r.getSeason(), r.getYear(), r.getLosses());
                losses.setText("Losses: " + r.getWins());
            }
        });

        wl.add(lossContainer);
        wl.add(addLoss);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(wl, BorderLayout.SOUTH);
        rosterPanel.add(topPanel, BorderLayout.NORTH);
        JPanel addTM = new JPanel(new BorderLayout());
        JTextField addTMInput = new CustomInputField("Team Member ID");
        JButton addTMButton = new CustomButton("+", "s");
        addTMButton.setBorder(new LineBorder(Color.WHITE, 1));
        addTMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getDbHandler().addTMRoster(Integer.parseInt(addTMInput.getText()), r);
                initRosterMembers(r, memberPanel);
            }
        });
        addTM.add(addTMInput, BorderLayout.NORTH);
        addTM.add(addTMButton, BorderLayout.SOUTH);
        rosterPanel.add(addTM, BorderLayout.SOUTH);
        return memberPanel;
    }

    private JPanel initTitle(Team team, JPanel panel) {
        JPanel titleContainer = new JPanel(new BorderLayout());
        AbstractScreen.setColors(titleContainer, "m");
        JLabel title = new JLabel(team.getName());
        title.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 31));
        title.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        AbstractScreen.setColors(title, "m");
        AbstractScreen.setColors(panel, "m");
        JPanel ownerContainer = new JPanel(new BorderLayout());
        ownerContainer.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, 50));
        JPanel leftPanel = new JPanel(new BorderLayout());
        AbstractScreen.setColors(leftPanel, "m");
        JLabel owner = new JLabel("Owner:");
        owner.setHorizontalAlignment(SwingConstants.RIGHT);
        owner.setPreferredSize(new Dimension(100, 50));
        owner.setForeground(AbstractScreen.TEXT_COLOR);
        JLabel tid = new JLabel("   ID: " + team.getTeamID());
        tid.setForeground(AbstractScreen.TEXT_COLOR);
        leftPanel.add(tid, BorderLayout.LINE_START);
        leftPanel.add(owner, BorderLayout.LINE_END);
        AbstractScreen.setColors(ownerContainer, "m");
        JTextField ownerName = new JTextField(team.getOwner());
        ownerName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    parent.getDbHandler().updateTeamOwner(ownerName.getText(), team.getTeamID());
                    team.setOwner(ownerName.getText());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        AbstractScreen.setColors(ownerName, "s");
        ownerContainer.add(leftPanel, BorderLayout.LINE_START);
        ownerContainer.add(ownerName, BorderLayout.CENTER);
        titleContainer.add(title, BorderLayout.NORTH);
        titleContainer.add(ownerContainer, BorderLayout.SOUTH);
        return titleContainer;
    }

    private JPanel setupContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 2 + 30, AbstractScreen.SCREEN_HEIGHT));
        AbstractScreen.setColors(contentPanel, "m");
        return contentPanel;
    }

    private JPanel setupTeamBar() {
        JPanel sideBarParent = new JPanel(new BorderLayout());
        initTeamBar();
        panel.setBackground(AbstractScreen.TAB_COLOR);
        JScrollPane scroll = new JScrollPane(teamBar, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder( new LineBorder(AbstractScreen.MAIN_COLOR) );
        scroll.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4 - 30, AbstractScreen.SCREEN_HEIGHT - 50));
        JPanel filler = new JPanel();
        AbstractScreen.setColors(filler, "m");
        filler.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4 - 30, 9));
        sideBarParent.add(filler, BorderLayout.NORTH);
        sideBarParent.add(scroll, BorderLayout.LINE_START);
        JButton addTeam = new CustomButton("Add");
        addTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTeamPopup(getThis());
            }
        });
        AbstractScreen.setColors(addTeam, "m");
        addTeam.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH / 4 - 30, 50));
        sideBarParent.add(addTeam, BorderLayout.SOUTH);
        AbstractScreen.setColors(sideBarParent, "m");
        return sideBarParent;
    }

    private void initTeamBar() {
        teamNames = new DefaultListModel<>();
        teamBar = new JList(teamNames);
        teamBar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teamBar.setSelectedIndex(0);
        teamBar.setVisibleRowCount(10);
        teamBar.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        teamBar.setFixedCellHeight(55);
        teamBar.setSelectionBackground(AbstractScreen.TAB_HIGHLIGHTED);
        AbstractScreen.setColors(teamBar, "m");
        teamBar.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Integer selection = teamBar.getSelectedIndex();
                displayTab(selection);
            }
        });
    }

    public void addTab(Team t) {
        if (!teamNames.contains(t.getName())) {
            teamNames.addElement(t.getName());
            JPanel tabPanel = setupTeamPanel(t);
//        HomeScreen.createBorder(tabPanel, Color.RED);
            if (teamPanels.size() != 0) {
                tabPanel.setVisible(false);
            }

            teamPanels.add(tabPanel);
            contentPanel.add(tabPanel);
        }
    }

    protected void displayTab(int index) {
        if (visibleTabIndex == -1) {
            teamPanels.get(index).setVisible(true);
            visibleTabIndex = index;
        } else if (visibleTabIndex != index) {
            teamPanels.get(visibleTabIndex).setVisible(false);
            teamPanels.get(index).setVisible(true);
            ((JButton) teamPanels.get(index).getComponent(0)).doClick();
            visibleTabIndex = index;
        }
    }
}
