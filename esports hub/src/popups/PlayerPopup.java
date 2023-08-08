package popups;

import ui.AbstractScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class PlayerPopup extends Popup{
    DefaultListModel res;
    String setting;
    ArrayList<String> attrs;
    ArrayList<Integer> ids;

    public PlayerPopup(AbstractScreen e, ArrayList<Integer> ids, String setting) {
        super(e, "Filter Players");
        this.setting = setting;
        this.ids = ids;
        setSize(new Dimension(POPUP_WIDTH + 100, POPUP_HEIGHT + 200));
        initializePrompts();
    }

    @Override
    protected void initializePrompts() {
        attrs = new ArrayList<>();
        res = new DefaultListModel();
        main.setLayout(new BorderLayout());
        JList list = new JList(res);
        AbstractScreen.setColors(list, "s");
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        main.add(scrollPane, BorderLayout.CENTER);
        JPanel checks = new JPanel();
        checks.setLayout(new BoxLayout(checks, BoxLayout.LINE_AXIS));
        String[] attr;
        if (setting.equals("Player")) {
            attr = new String[]{"tmid", "name", "age", "position", "alias"};
        } else
            attr = new String[]{"tmid", "name", "age", "role"};
        for (int i = 0; i < attr.length; i++) {
            Checkbox temp = new Checkbox(attr[i]);
            int finalI = i;
            temp.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    String temp = attr[finalI];
                    res.clear();
                    if (e.getStateChange() == 1) {
                        attrs.add(temp);
                    } else attrs.remove(temp);
                    for (int id : ids) {
                        ArrayList<String> tm = otherEditor.getDbHandler().getTeamMemberDescrip(id, attrs, setting);
                        String des = "";
                        for (int i = 0; i < tm.size(); i++) {
                             des += "  |  " + attrs.get(i) + ": " + tm.get(i);
                        }
                        res.addElement(des);
                    }
                }
            });
            AbstractScreen.setColors(checks, "m");
            checks.add(temp);
        }
        main.add(checks, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
