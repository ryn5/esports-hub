package tabs;

import model.Ticket;
import ui.AbstractScreen;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static ui.AbstractScreen.SCREEN_HEIGHT;
import static ui.AbstractScreen.SCREEN_WIDTH;

public class ViewerTicketsPanel extends Panel {

    private int selectedTicketNum;
    private DefaultListModel<String> tListModel;
    private JList<Ticket> ticketList;
    private int viewerID;
    private ArrayList<Integer> ticketNums;
    private JScrollPane ticketSP;

    public ViewerTicketsPanel(AbstractScreen parent, int viewerID) {
        super(parent);
        this.viewerID = viewerID;
        getTickets();
        displayTickets();
        addToolbar();
    }

    private void displayTickets() {
        ticketList = new JList(tListModel);
        ticketList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketList.setSelectedIndex(0);
        ticketList.setVisibleRowCount(10);
        AbstractScreen.setColors(ticketList, "s");
        ticketSP = new JScrollPane(ticketList);
        AbstractScreen.setColors(ticketSP, "s");
        ticketSP.setPreferredSize(new Dimension( SCREEN_WIDTH * 3/4, SCREEN_HEIGHT/4));
        ticketList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateSelection();
                System.out.println(selectedTicketNum);
            }
        });
        panel.add(ticketSP);
    }

    public void getTickets() {
        tListModel = new DefaultListModel<>();
        ArrayList<String> tickets = parent.getDbHandler().getViewerTickets(viewerID);
        for (String t : tickets) {
            tListModel.addElement(t);
        }
        ticketNums = parent.getDbHandler().getViewerTicketNums(viewerID);
    }

    private void addToolbar() {
        JPanel toolbar = new JPanel(new GridLayout(0, 1));
        AbstractScreen.setColors(toolbar, "s");

        JButton refundTicketButton = new JButton("Refund Ticket");
        refundTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refundTicket();
            }
        });
        refundTicketButton.setPreferredSize(new Dimension(SCREEN_WIDTH * 3/4, 50));
        AbstractScreen.setColors(refundTicketButton, "s");
        toolbar.add(refundTicketButton);
        panel.add(toolbar, BorderLayout.SOUTH);
    }

    public void refundTicket() {
        parent.getDbHandler().unbookTicket(selectedTicketNum);
        updateTickets();
    }

    public void updateTickets() {
        panel.remove(ticketSP);
        getTickets();
        displayTickets();
        panel.repaint();
        panel.revalidate();
        ticketList.setSelectedIndex(0);
        if (!ticketNums.isEmpty()) updateSelection();
    }

    public void updateSelection() {
        selectedTicketNum = ticketNums.get(ticketList.getSelectedIndex());
    }

}
