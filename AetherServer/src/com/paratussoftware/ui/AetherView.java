package com.paratussoftware.ui;

import javax.swing.*;
import java.awt.*;

public class AetherView extends JFrame {

    private final JPanel clientPanel;
    private final JPanel clientList;

    public AetherView(){
        super("Aether");
        setSize(1366, 768);
        setLayout(new BorderLayout());
        clientPanel = new JPanel();
        clientList = new JPanel();
        add(clientList, BorderLayout.WEST);
        add(clientPanel, BorderLayout.CENTER);

    }

    public static AetherView showView(){
        final AetherView aetherView = new AetherView();
        aetherView.setVisible(true);
        return aetherView;
    }

}
