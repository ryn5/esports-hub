package tabs;

import model.Achievement;
import ui.AbstractScreen;
import ui.HomeScreen;
import utils.CustomButton;
import utils.CustomInputField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class EmployeeAchievementPanel extends Panel {
    JTextField seasonInput;
    JTextField yearInput;
    ArrayList<JTextField> teams;
    EmployeeTeamPanel teamPanel;
    int index;
    public EmployeeAchievementPanel(AbstractScreen employeeScreen, EmployeeTeamPanel teamPanel) {
        super(employeeScreen);
        index = 1;
        this.teamPanel = teamPanel;
        panel.setLayout(new BorderLayout());
        AbstractScreen.setColors(panel, "m");
        teams = new ArrayList<>();
        initSeasonYear();
        initMain();
        initSubmit();
    }

    private void initSubmit() {
        JButton submit = new CustomButton("Submit");
        submit.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3/4, 50));
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String season = seasonInput.getText();
                Integer year = Integer.valueOf(yearInput.getText());
                for (int i = 1; i <= teams.size(); i++) {
                    Integer teamID = Integer.valueOf(teams.get(i - 1).getText());
                    Achievement a = new Achievement(season, year, i, teamID);
                    parent.getDbHandler().addAchievement(a);
                }
                clearInputs();
            }
        });
        panel.add(submit, BorderLayout.SOUTH);
    }

    private void clearInputs() {
    }

    private void initSeasonYear() {
        JPanel upper = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Add Season Achievments");
        title.setForeground(AbstractScreen.TEXT_COLOR);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3/4, 30));
        upper.add(title, BorderLayout.NORTH);
        upper.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3/4, 80));
        seasonInput = new CustomInputField("Season");
        seasonInput.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3/8 - 10, 30));
        upper.add(seasonInput, BorderLayout.LINE_START);
        yearInput = new CustomInputField("Year");
        yearInput.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3/8 - 10, 30));
        upper.add(yearInput, BorderLayout.LINE_END);
        AbstractScreen.setColors(upper, "m");
        JLabel controls = new JLabel("RETURN = new place | DELETE = remove place");
        controls.setForeground(AbstractScreen.TEXT_COLOR);
        upper.add(controls, BorderLayout.SOUTH);
        panel.add(upper, BorderLayout.NORTH);
    }

    private void initMain() {

        JPanel teamPanel = new JPanel(new GridBagLayout());

        AbstractScreen.setColors(teamPanel, "m");
        addPlace(teamPanel);
        panel.add(teamPanel, BorderLayout.CENTER);
    }

    private void addPlace(JPanel teamPanel) {
        int teamInd = index;
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel place = new JPanel();
        AbstractScreen.setColors(place, "m");
        JLabel placeLabel = new JLabel(String.valueOf(index++));
        placeLabel.setFont(new Font(HomeScreen.DEFAULT_FONT_NAME, Font.PLAIN, 14));
        placeLabel.setForeground(AbstractScreen.TEXT_COLOR);
        placeLabel.setPreferredSize(new Dimension(50, 50));
        place.add(placeLabel);
        JTextField teamInput = new CustomInputField("Team ID");
        teams.add(teamInput);
        teamInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER && teamInd == teams.size()) {
                    addPlace(teamPanel);
                    teamPanel.revalidate();
                    teamPanel.repaint();
                    teams.get(teamInd).requestFocusInWindow();
                }
                if (key == KeyEvent.VK_BACK_SPACE  && teamInd == teams.size() && teamInput.getText().isEmpty() && teamInd != 1) {
                    teamPanel.remove(place);
                    index--;
                    teamPanel.revalidate();
                    teamPanel.repaint();
                    teams.remove(teamInput);
                    teams.get(index - 2).requestFocusInWindow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        teamInput.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH * 3/4 - 100, 50));
        place.add(teamInput);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        teamPanel.add(place, gbc);
    }
}
