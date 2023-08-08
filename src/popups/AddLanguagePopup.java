package popups;

import tabs.EmployeeGamePanel;
import tabs.Panel;
import utils.CustomButton;
import utils.CustomInputField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddLanguagePopup extends Popup {
    JTextField language;
    JComboBox casters;

    public AddLanguagePopup(Panel e) {
        super(e, "Add Language");
        initializePrompts();
    }

    @Override
    protected void initializePrompts() {
        main.setLayout(new BorderLayout());
        language = new CustomInputField("Language");
        language.setMaximumSize(new Dimension(POPUP_WIDTH/ 3, 20));
        main.add(language, BorderLayout.LINE_START);
        ArrayList<Integer> casterIds = editor.getParent().getDbHandler().getKeys("cid", "Caster");
        casters = new JComboBox(casterIds.toArray());
        main.add(casters, BorderLayout.LINE_END);
        JButton submit = new CustomButton("Add Cast", "s");
        submit.addActionListener(this);
        main.add(submit, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editor.getParent().getDbHandler().addCasts(((EmployeeGamePanel) editor).getSelectedG().getgID(),
                (Integer) casters.getSelectedItem(), language.getText());
        ((EmployeeGamePanel) editor).printGame();
        dispose();
    }
}
