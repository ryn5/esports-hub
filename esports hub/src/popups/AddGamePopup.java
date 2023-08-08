package popups;

import model.Arena;
import model.Game;
import tabs.EmployeeGamePanel;
import tabs.Panel;
import ui.AbstractScreen;
import utils.CustomInputField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class AddGamePopup extends Popup {
    private JTextField[] fields;
    public AddGamePopup(Panel e) {
        super(e, "Add Game");
        initializePrompts();
    }

    @Override
    protected void initializePrompts() {
        String[] labels = {"Date: ", "Blue Team ID: ", "Red Team ID: ", "Arena ID: ", "Season: "};
        int numPairs = labels.length;

        fields = new JTextField[numPairs];
        SpringLayout layout = new SpringLayout();
        main.setLayout(layout);
        JComponent aboveLabel = null;
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            main.add(l);
            AbstractScreen.setColors(l, "s");
            JTextField textField;
            if (labels[i].equals("Date: "))
                textField  = new CustomInputField("YYYY-MM-DD");
            else
                textField  = new CustomInputField("");
            l.setLabelFor(textField);
            main.add(textField);
            fields[i] = textField;
            if (i == 0) {
                layout.putConstraint(SpringLayout.NORTH, l, 10, SpringLayout.NORTH, main);
                layout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, l);
                layout.putConstraint(SpringLayout.WEST, l, 10, SpringLayout.WEST, main);
                layout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, l);
            } else {
                layout.putConstraint(SpringLayout.NORTH, l, 10, SpringLayout.SOUTH, aboveLabel);
                layout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, l);
                layout.putConstraint(SpringLayout.WEST, l, 10, SpringLayout.WEST, main);
                layout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, l);

            }
            aboveLabel = l;

        }
        JButton create = new JButton("Create Game");
        create.addActionListener(this);
        AbstractScreen.setColors(create, "s");
        main.add(create);
        layout.putConstraint(SpringLayout.NORTH, create, 10, SpringLayout.SOUTH, aboveLabel);
        layout.putConstraint(SpringLayout.WEST, create, 10, SpringLayout.WEST, main);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int aId = Integer.parseInt(fields[3].getText());
        Game game = new Game(editor.updateMaxKey("gID", "Game") + 1,Integer.parseInt(fields[1].getText()),
                aId, Date.valueOf(fields[0].getText()),Integer.parseInt(fields[3].getText()), fields[4].getText(),Integer.parseInt(fields[0].getText().substring(0, 2)));
        editor.getParent().getDbHandler().insertGame(game);
        ((EmployeeGamePanel) editor).getGames("","");
        Arena a = editor.getParent().getDbHandler().getArena(aId);
        int minTicket = editor.updateMaxKey("ticketnum", "ticket");
        for (int i = 1; i < a.getCapacity() + 1; i++) {
            editor.getParent().getDbHandler().addTicket(minTicket + i, -1, game.getgID(), aId, i);
        }
        dispose();
    }

}
