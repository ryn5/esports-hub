package popups;

import tabs.ViewerGamePanel;
import ui.ViewerScreen;
import utils.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuyTicketPopup extends Popup {
    JComboBox ticketsCB;
    private ArrayList<Integer> ticketNums;
    ViewerScreen parent;

    public BuyTicketPopup(ViewerGamePanel viewerGamePanel, int gameID, int viewerID, ViewerScreen parent) {
        super(viewerGamePanel, "Buy Ticket", gameID, viewerID);
        this.parent = parent;
        initializePrompts();
    }

    @Override
    protected void initializePrompts() {
        main.setLayout(new BorderLayout());
        ArrayList<String> ticketTexts = editor.getParent().getDbHandler().getAvailTickets(gameID);
        ticketNums = editor.getParent().getDbHandler().getAvailTicketNums(gameID);
        JPanel buttons = new JPanel(new GridLayout(0, 1));
        if (ticketNums.isEmpty()) {
            JLabel soldOutLabel = new JLabel("All sold out!");
            soldOutLabel.setForeground(Color.white);
            main.add(soldOutLabel, BorderLayout.CENTER);
        } else {
            ticketsCB = new JComboBox(ticketTexts.toArray());
            main.add(ticketsCB, BorderLayout.CENTER);
            JButton purchaseButton = new CustomButton("Purchase Ticket", "s");
            purchaseButton.addActionListener(this);
            buttons.add(purchaseButton);
        }
        JButton cancelButton = new CustomButton("Cancel", "s");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.getTicketsPanel().updateTickets();
                dispose();
            }
        });
        buttons.add(cancelButton);
        main.add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedTicketNum = ticketNums.get(ticketsCB.getSelectedIndex());
        editor.getParent().getDbHandler().bookTicket(selectedTicketNum, viewerID);
        parent.getTicketsPanel().updateTickets();
        dispose();
    }
}
