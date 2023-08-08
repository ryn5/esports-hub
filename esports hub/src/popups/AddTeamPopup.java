package popups;

import model.Team;
import tabs.EmployeeTeamPanel;
import tabs.Panel;
import ui.AbstractScreen;
import utils.CustomButton;
import utils.CustomInputField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddTeamPopup extends Popup{
    JTextField nameInput;
    public AddTeamPopup(Panel e) {
        super(e, "Add Team");
        initializePrompts();

    }

    @Override
    protected void initializePrompts() {
        JLabel nameLabel = new JLabel("Team Name");
        nameLabel.setForeground(AbstractScreen.TEXT_COLOR);
        nameInput = new CustomInputField("");
        nameInput.setPreferredSize(new Dimension(main.getWidth() - 20, 30));
        main.add(nameLabel);
        main.add(nameInput);
        JButton create = new CustomButton("Create Team", "s");
        create.addActionListener(this);
        main.add(create);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int newKey = editor.updateMaxKey("tID", "Team") + 1;
        editor.getParent().getDbHandler().addTeam(newKey, nameInput.getText());
        Team newTeam = editor.getParent().getDbHandler().getTeam(newKey);
        ((EmployeeTeamPanel) editor).addTab(newTeam);
        dispose();
    }
}
