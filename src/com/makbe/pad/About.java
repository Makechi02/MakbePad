package com.makbe.pad;

import javax.swing.*;

public class About extends JDialog {

    public About(JFrame owner) {
        super(owner, "About Makbepad", true);
        setSize(320, 400);
        setResizable(false);
        setLocationRelativeTo(owner);
        setUndecorated(true);
        setLayout(null);

        String name = "<html>" +
                "<p><big>MAKBEPAD<big></p>" +
                "<p>Version: 1.0.0</p>" +
                "<p>Copyright 2023</p><br>" +
                "</html>";
        JLabel label = new JLabel(name);
        label.setBounds(20, 20, 290, 100);
        add(label);

        String details = "<html>" +
                "<p>A huge <strong>Thanks</strong> to you for using this app</p>" +
                "<p>Your feedback and comments will be highly appreciated<br>and so will bug reports</p>" +
                "<p>Email: <em>makechieric9@gmail.com</em></p>" +
                "</html>";
        label = new JLabel(details);
        label.setBounds(20, 100, 300, 140);
        add(label);

        JButton ok_button = new JButton("OK");
        ok_button.setBounds(240, 350, 60, 30);
        ok_button.addActionListener(e -> setVisible(false));
        ok_button.setFocusable(false);
        add(ok_button);

        setVisible(true);
    }

}