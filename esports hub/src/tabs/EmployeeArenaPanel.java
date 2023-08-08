package tabs;

import model.Arena;
import ui.AbstractScreen;
import ui.AnalystScreen;
import utils.CustomButton;
import utils.CustomInputField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmployeeArenaPanel extends Panel {

    JScrollPane arenaSP;
    JPanel arenaPanelContent;

    public EmployeeArenaPanel(AbstractScreen parent) {
        super(parent);
        arenaSP = new JScrollPane();
        panel.add(setupTopBar(), BorderLayout.PAGE_START);
        panel.add(displayArenas(), BorderLayout.CENTER);
    }

    public JPanel setupTopBar() {
        JPanel topBar = new JPanel(new FlowLayout());
        JTextField nameTF = new CustomInputField("Name");
        JTextField cityTF = new CustomInputField("City");
        JTextField capacityTF = new CustomInputField("Capacity");
        JButton addArenaButton = new CustomButton("Add");
        addArenaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newKey = getThis().updateMaxKey("aID", "Arena") + 1;
                Arena arena = new Arena(newKey, nameTF.getText(), cityTF.getText(), Integer.parseInt(capacityTF.getText()));
                parent.getDbHandler().addArena(arena);
                updateArenaPanel();
                nameTF.setText("");
                cityTF.setText("");
                capacityTF.setText("");
            }
        });
        topBar.add(nameTF);
        topBar.add(cityTF);
        topBar.add(capacityTF);
        topBar.add(addArenaButton);
        AbstractScreen.setColors(topBar, "m");
        topBar.setPreferredSize(new Dimension(AbstractScreen.SCREEN_WIDTH, 50));
        return topBar;
    }

    private JPanel displayArenas() {
        JPanel arenaPanel = new JPanel(new GridBagLayout());
        arenaPanel.setBackground(AbstractScreen.SECOND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        arenaPanelContent = new JPanel(new GridBagLayout());
        arenaPanelContent.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        arenaPanel.add(arenaPanelContent, gbc);

        JPanel panelFiller = new JPanel();
        panelFiller.setBackground(AbstractScreen.SECOND_COLOR);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        arenaPanel.add(panelFiller, gbc);

        updateArenaPanel();

        return arenaPanel;
    }

    private void setupHeader() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;

        JLabel rankLabel = new JLabel("ID");
        AnalystScreen.setBoldFont(rankLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 15, 5, 5);
        arenaPanelContent.add(rankLabel, gbc);

        JLabel nameLabel = new JLabel("Name");
        AnalystScreen.setBoldFont(nameLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.insets = new Insets(15, 5, 5, 5);
        arenaPanelContent.add(nameLabel, gbc);

        JLabel winsLabel = new JLabel("City");
        AnalystScreen.setBoldFont(winsLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        arenaPanelContent.add(winsLabel, gbc);

        JLabel lossesLabel = new JLabel("Capacity");
        AnalystScreen.setBoldFont(lossesLabel, AnalystScreen.LIST_FONT_SIZE);
        gbc.gridx = 3;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(15, 5, 5, 5);
        arenaPanelContent.add(lossesLabel, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        arenaPanelContent.add(sep, gbc);
    }


    private void updateArenaPanel() {
        setupHeader();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;

        ArrayList<Arena> arenas = parent.getDbHandler().getArenas();
        for (int i = 0; i < arenas.size(); i++) {
            Arena a = arenas.get(i);
            JLabel idLabel = new JLabel(String.valueOf(a.getArenaID()));
            AnalystScreen.setDefaultFont(idLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridy = i + 2;
            gbc.gridx = 0;
            gbc.weightx = 0.2;
            gbc.insets = new Insets(10, 15, 10, 5);
            arenaPanelContent.add(idLabel, gbc);

            JLabel nameLabel = new JLabel(a.getName());
            AnalystScreen.setDefaultFont(nameLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridx = 1;
            gbc.weightx = 0.8;
            gbc.insets = new Insets(10, 5, 10, 5);
            arenaPanelContent.add(nameLabel, gbc);

            JLabel winsLabel = new JLabel(String.valueOf(a.getCity()));
            AnalystScreen.setDefaultFont(winsLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridx = 2;
            gbc.weightx = 0.2;
            gbc.insets = new Insets(10, 5, 10, 5);
            arenaPanelContent.add(winsLabel, gbc);

            JLabel lossesLabel = new JLabel(String.valueOf(a.getCapacity()));
            AnalystScreen.setDefaultFont(lossesLabel, AnalystScreen.LIST_FONT_SIZE);
            gbc.gridx = 3;
            gbc.weightx = 0.2;
            gbc.insets = new Insets(10, 5, 10, 5);
            arenaPanelContent.add(lossesLabel, gbc);
        }

        panel.revalidate();
        panel.repaint();

    }

}
