package tabs;

import model.Game;
import model.Player;
import model.Team;
import popups.AddGamePopup;
import popups.AddLanguagePopup;
import ui.AbstractScreen;
import ui.EmployeeScreen;
import utils.CustomButton;
import utils.CustomInputField;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import static ui.AbstractScreen.*;

public class EmployeeGamePanel extends Panel {
    public static final int DEF_ITEMS = 10;
    private DefaultListModel<String> glistModel;
    private HashMap<String, Game> glist;
    private JPanel main;
    JLabel title;
    JPanel redTeam;
    JPanel blueTeam;
    JButton arena;
    DefaultListModel<String> languages;

    private Game selectedG;

    private String filter;
    public EmployeeGamePanel(EmployeeScreen parent) {
        super(parent);
        selectedG = null;
        glistModel = new DefaultListModel<>();
        glist = new HashMap<>();
        updateMaxKey("gID", "Game");
        addSearchBar();
        addMainPanel();
        addBottom();
    }

    public Game getSelectedG() {
        return selectedG;
    }

    private void addBottom() {
        JPanel bottom = new JPanel(new BorderLayout());
        AbstractScreen.setColors(bottom, "s");
        JPanel tools = new JPanel(new GridLayout(0, 1));
        AbstractScreen.setColors(tools, "s");
        JButton deleteGame = new CustomButton("Delete Game", "s");
        deleteGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteGame();
            }
        });
        deleteGame.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(deleteGame, "s");
        JButton addGameButton = new CustomButton("Add Game", "s");
        addGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGame();
            }
        });
        addGameButton.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(addGameButton, "s");
        bottom.add(displayGames(), BorderLayout.NORTH);
        tools.add(deleteGame);
        tools.add(addGameButton);
        bottom.add(tools, BorderLayout.SOUTH);
        panel.add(bottom, BorderLayout.SOUTH);
    }

    private void addSearchBar() {
        JPanel searchBar = new JPanel(new BorderLayout());
        AbstractScreen.setColors(searchBar, "m");
        searchBar.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 70));
        JTextField search = new CustomInputField("Search");
        AbstractScreen.setColors(search, "s");
        search.setText("Search");
        String[] filters = { "game ID", "arena", "team"};
        JPanel leftPanel = new JPanel(new BorderLayout());
        AbstractScreen.setColors(leftPanel, "m");
        JButton all = new CustomButton("View all");
        all.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getGames("", "");
            }
        });
        JComboBox filterList = new JComboBox(filters);

        filterList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter = (String) filterList.getSelectedItem();
            }
        });
        filterList.setSelectedIndex(0);
        leftPanel.add(all, BorderLayout.WEST);
        leftPanel.add(filterList, BorderLayout.EAST);
        searchBar.add(leftPanel, BorderLayout.WEST);
        searchBar.add(search, BorderLayout.CENTER);
        JButton submit = new JButton("Search");
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                searchFilter(search.getText(), filter);
            }
        });
        searchBar.add(submit, BorderLayout.EAST);
        panel.add(searchBar, BorderLayout.NORTH);
        ArrayList<String> winningteam = parent.getDbHandler().getWinningTeam();
        String winningText = "Best Team of All Time: " + winningteam.get(0) + "  |  Wins: " + winningteam.get(1);
        JLabel winningLabel = new JLabel(winningText);
        winningLabel.setForeground(Color.ORANGE);
        winningLabel.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 20));
        winningLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchBar.add(winningLabel, BorderLayout.SOUTH);
    }



    private void searchFilter(String searchTerm, String attribute) {
        switch (attribute) {
            case "arena":
                getGames("aID", searchTerm);
                break;
            case "team":
                getGames("team", searchTerm);
                break;
            case "game ID":
                getGames("gID", searchTerm);
                break;
        }
    }


    private JScrollPane displayGames() {
        JList rlist = new JList(glistModel);
        getGames("", "");
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        AbstractScreen.setColors(rlist, "s");
        JScrollPane listScrollPane = new JScrollPane(rlist);
        AbstractScreen.setColors(listScrollPane, "s");
        listScrollPane.setPreferredSize(new Dimension( SCREEN_WIDTH * 3/4, SCREEN_HEIGHT/4));
        rlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selection = (String) rlist.getSelectedValue();
                setGame(glist.get(selection));
            }
        });
        return listScrollPane;
    }
    private void addMainPanel() {
        initializeMain();
        JScrollPane p = new JScrollPane(main);
        AbstractScreen.setColors(p, "m");
        printGame();
        panel.add(p, BorderLayout.CENTER);
    }

    private void initializeMain() {
        main = new JPanel(new BorderLayout(5, 5));
        main.setBackground(MAIN_COLOR);
        title = new JLabel("No Game Selected");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        main.add(title, BorderLayout.NORTH);
        JPanel centreScreen = new JPanel(new BorderLayout());
        redTeam = new JPanel(new GridLayout(0, 1));
        blueTeam = new JPanel(new GridLayout(0, 1));
        initTeam(redTeam);
        initTeam(blueTeam);
        centreScreen.add(redTeam, BorderLayout.LINE_START);
        centreScreen.add(blueTeam, BorderLayout.LINE_END);
        JPanel lanes = new JPanel(new GridLayout(0,1));
        main.add(centreScreen, BorderLayout.CENTER);
        JPanel arenaContainer = new JPanel(new BorderLayout());
        JLabel arenaLabel = new JLabel("Arena");
        setColors(arenaLabel, "m");
        arenaLabel.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 50));
        arena = new CustomButton("", "s");
        setColors(arena, "s");
        JPanel arenaPanel = new JPanel(new BorderLayout());
        arenaPanel.add(arena, BorderLayout.CENTER);
        setColors(arenaPanel, "s");
        arena.setHorizontalAlignment(SwingConstants.LEFT);
        arenaContainer.add(arenaLabel, BorderLayout.NORTH);
        arenaContainer.add(arenaPanel, BorderLayout.CENTER);
        arenaContainer.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 75));
        JPanel languageContainer = new JPanel(new BorderLayout());
        languages = new DefaultListModel<>();
        JList languagelist = new JList(languages);
        JScrollPane languageScroll = new JScrollPane(languagelist);
        languageScroll.setBorder(BorderFactory.createEmptyBorder());
        setColors(languagelist, "s");
        languageContainer.add(languageScroll, BorderLayout.CENTER);
        JLabel languageLabel = new JLabel("Languages");
        setColors(languageLabel, "m");
        languageLabel.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 50));
        languageLabel.setMaximumSize(new Dimension(SCREEN_WIDTH / 4, 50));
        languageContainer.add(languageLabel, BorderLayout.NORTH);
        languageContainer.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 125));
        JButton addLanguage = new CustomButton("Add Language", "m");
        addLanguage.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 50));
        addLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddLanguagePopup(getThis());
                printGame();
            }
        });
        languageContainer.add(addLanguage, BorderLayout.SOUTH);
        JPanel rightContainer = new JPanel(new BorderLayout(5, 5));
        rightContainer.add(arenaContainer, BorderLayout.NORTH);
        rightContainer.add(languageContainer, BorderLayout.CENTER);
        rightContainer.setPreferredSize(new Dimension(SCREEN_WIDTH / 4, 200));
        main.add(rightContainer, BorderLayout.LINE_END);
        AbstractScreen.setColors(lanes, "m");
        AbstractScreen.setColors(redTeam, "s");
        AbstractScreen.setColors(blueTeam, "s");
        AbstractScreen.setColors(title, "m");
        AbstractScreen.setColors(arenaContainer, "m");
        AbstractScreen.setColors(languageContainer, "m");
        AbstractScreen.setColors(rightContainer, "m");
        arenaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        arena.setHorizontalAlignment(SwingConstants.CENTER);
        languageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setColors(main, "m");


    }

    private void initTeam(JPanel team) {
        team.setPreferredSize(new Dimension( SCREEN_WIDTH / 4, 200 ));
        for (int i = 0; i < 6; i++) {
            JButton temp;
            if (i == 0) {
                temp = new CustomButton("");
                temp.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int teamind = 0;
                        ListModel teamList = ((JList) ((JViewport) ((JScrollPane) ((JPanel) parent.getTabPanels().get(1).getComponent(0))
                                .getComponent(1)).getComponent(0)).getComponent(0)).getModel();
                        for (int i = 0; i < teamList.getSize(); i++) {
                            if (temp.getText().equals(teamList.getElementAt(i)))
                                teamind = i;
                        }
                        parent.displayTab(1);
                        ((JList) ((JViewport) ((JScrollPane) ((JPanel) parent.getTabPanels().get(1).getComponent(0))
                                .getComponent(1)).getComponent(0)).getComponent(0)).setSelectedIndex(teamind);


                    }
                });
            } else {
                temp = new CustomButton("", "s");
            }

            temp.setHorizontalTextPosition(SwingConstants.CENTER);
            team.add(temp);
        }
    }

    public void getGames(String attr, String query) {
        glistModel.clear();
        ArrayList<Game> games = parent.getDbHandler().getGames(DEF_ITEMS, attr, query);
        for (Game g : games) {
            System.out.println(g.getgID());
            if (!glist.containsKey(g.getDescription())) {
                glist.put(g.getDescription(),g);
            }
            glistModel.addElement(g.getDescription());
        }
    }

    public void printGame() {
        clearGame();
        if (selectedG == null) {
        } else {
            title.setText("----------|   " + selectedG.getSeason() + " " + selectedG.getDay() + "   |----------");
            Team rtemp = parent.getDbHandler().getTeam(selectedG.getRtID());
            getPlayers(rtemp, redTeam);
            Team btemp = parent.getDbHandler().getTeam(selectedG.getBtID());
            getPlayers(btemp, blueTeam);
            arena.setText(parent.getDbHandler().getArena(selectedG.getaID()).getName());
            for (String lang : parent.getDbHandler().getGameCasts(selectedG.getgID())) {
                languages.addElement(lang);
            }
        }
    }

    private void clearGame() {
        title.setText("No Game Selected :(");
        arena.setText("");
        languages.clear();
        for (int i = 0; i < 6; i++) {
            ((JButton) blueTeam.getComponent(i)).setText("");
            ((JButton) redTeam.getComponent(i)).setText("");
        }
    }

    private void getPlayers(Team temp, JPanel team) {
        ArrayList<Player> players = parent.getDbHandler().getRosterPlayers(temp.getTeamID(), selectedG.getSeason(),selectedG.getDay().getYear() + 1900);
        ((JButton) team.getComponent(0)).setText(temp.getName());
        for (Player p : players) {
            ((JButton) team.getComponent(p.getRoleNum() + 1)).setText(p.getAlias());
        }
    }


    public void setGame(Game g) {
        clearGame();
        selectedG = g;
        printGame();
    }

    public void deleteGame() {
        parent.getDbHandler().deleteGame(selectedG.getgID());
        selectedG = null;
        printGame();
        getGames("", "");
    }

    public void addGame() {
        new AddGamePopup(this);
    }


}
