package com.makechi.makbepad;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UI extends JFrame {
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screensize = kit.getScreenSize();
    int width = screensize.width / 2;
    int height = screensize.height / 2;

    JMenuBar mb = new JMenuBar();
    JMenu filemenu = new JMenu("File");
    JMenuItem newItem = new JMenuItem("New");
    JMenuItem newWindow = new JMenuItem("New Window");
    JMenuItem open = new JMenuItem("Open...");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem saveAs = new JMenuItem("Save As...");
    JMenuItem pageSetup = new JMenuItem("Page Setup...");
    JMenuItem print = new JMenuItem("Print...");
    JMenuItem quit = new JMenuItem("Quit", 'Q');
    JMenu editMenu = new JMenu("Edit");
    JMenuItem undo = new JMenuItem("Undo");
    JMenuItem cut = new JMenuItem("Cut");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem paste = new JMenuItem("Paste");
    JMenuItem delete = new JMenuItem("Delete");
    JMenuItem bing = new JMenuItem("Search with Bing...");
    JMenuItem find = new JMenuItem("Find");
    JMenuItem nxt = new JMenuItem("Find Next");
    JMenuItem prev = new JMenuItem("Find Previous");
    JMenuItem replace = new JMenuItem("Replace...");
    JMenuItem goTo = new JMenuItem("Go To...");
    JMenuItem select = new JMenuItem("Select All");
    JMenuItem tme = new JMenuItem("Time/Date");
    JMenu formatMenu = new JMenu("Format");
    JCheckBoxMenuItem wrap = new JCheckBoxMenuItem("Word Wrap");
    JMenuItem fnt = new JMenuItem("Font...");
    JMenuItem txtColor = new JMenuItem("Set Text Color...");
    JMenuItem bgColor = new JMenuItem("Set Background...");
    JMenu viewMenu = new JMenu("View");
    JMenu zoom = new JMenu("Zoom");
    JMenuItem in = new JMenuItem("Zoom In");
    JMenuItem out = new JMenuItem("Zoom Out");
    JMenuItem restore = new JMenuItem("Restore Default Zoom");
    JCheckBoxMenuItem statusBar = new JCheckBoxMenuItem("Status Bar");
    ButtonGroup group = new ButtonGroup();
    JMenu look = new JMenu("Change Look and Feel");
    JMenu helpMenu = new JMenu("Help");
    JMenuItem viewHelp = new JMenuItem("View Help");
    JMenuItem send = new JMenuItem("Send Feedback");
    JMenuItem about = new JMenuItem("About Makbepad");

    JTextArea ta = new JTextArea();
    JScrollPane scroll = new JScrollPane(ta);
    JPanel bottomBar = new JPanel();
    JLabel bottom = new JLabel("Ln 1, Col 1");

    JPopupMenu popup = new JPopupMenu();
    JMenuItem undoPopup = new JMenuItem("Undo");
    JMenuItem cutPopup = new JMenuItem("Cut");
    JMenuItem copyPopup = new JMenuItem("Copy");
    JMenuItem pastePopup = new JMenuItem("Paste");
    JMenuItem deletePopup = new JMenuItem("Delete");
    JMenuItem selectPopup = new JMenuItem("Select All");
    JMenuItem rtl = new JMenuItem("Right to Left Reading order");

    UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
    String[] lookNames = new String[looks.length];
    JRadioButtonMenuItem[] radio = new JRadioButtonMenuItem[looks.length];

    public UI() {
        super("MAKBEPAD");
        setSize(width + 180, height + 130);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < looks.length; i++) {
            lookNames[i] = looks[i].getName();
        }
        for (int count = 0; count < radio.length; count++) {
            radio[count] = new JRadioButtonMenuItem(lookNames[count]);
            look.add(radio[count]);

            radio[count].addItemListener(e -> {
                for (int i = 0; i < radio.length; i++) {
                    if (radio[i].isSelected()) { changeTheLookAndFeel(i); }
                }
            });
            group.add(radio[count]);
        }

        radio[1].setSelected(true);

        filemenu.add(newItem);
        filemenu.add(newWindow);
        filemenu.add(open);
        filemenu.add(save);
        filemenu.add(saveAs);
        filemenu.addSeparator();
        filemenu.add(pageSetup);
        filemenu.add(print);
        filemenu.addSeparator();
        filemenu.add(quit);
        mb.add(filemenu);
        editMenu.add(undo);
        editMenu.addSeparator();
        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(delete);
        editMenu.addSeparator();
        editMenu.add(bing);
        editMenu.add(find);
        editMenu.add(nxt);
        editMenu.add(prev);
        editMenu.add(replace);
        editMenu.add(goTo);
        editMenu.addSeparator();
        editMenu.add(select);
        editMenu.add(tme);
        mb.add(editMenu);
        formatMenu.add(wrap);
        formatMenu.add(fnt);
        formatMenu.addSeparator();
        formatMenu.add(txtColor);
        formatMenu.add(bgColor);
        mb.add(formatMenu);
        zoom.add(in);
        zoom.add(out);
        zoom.add(restore);
        viewMenu.add(zoom);
        viewMenu.add(statusBar);
        viewMenu.add(look);
        mb.add(viewMenu);
        helpMenu.add(viewHelp);
        helpMenu.add(send);
        helpMenu.addSeparator();
        helpMenu.add(about);
        mb.add(helpMenu);

        setJMenuBar(mb);

        bottomBar.add(bottom);
        add(scroll);
        add(bottomBar, BorderLayout.SOUTH);
        add(new JLabel("  "), BorderLayout.EAST);
        add(new JLabel("  "), BorderLayout.WEST);

        ta.setFont(new Font("Calibri", Font.PLAIN, 16));
        ta.setComponentPopupMenu(popup);
        popup.add(undoPopup);
        popup.addSeparator();
        popup.add(cutPopup);
        popup.add(copyPopup);
        popup.add(pastePopup);
        popup.add(deletePopup);
        popup.addSeparator();
        popup.add(selectPopup);
        popup.addSeparator();
        popup.add(rtl);

        newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        newWindow.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
        open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        saveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl shift S"));
        print.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        quit.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_DOWN_MASK));
        out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK));
        restore.setAccelerator(KeyStroke.getKeyStroke("ctrl 0"));
        undo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        cut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        copy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        paste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        bing.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        find.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
        nxt.setAccelerator(KeyStroke.getKeyStroke("F3"));
        prev.setAccelerator(KeyStroke.getKeyStroke("shift F3"));
        replace.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        goTo.setAccelerator(KeyStroke.getKeyStroke("ctrl G"));
        select.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
        tme.setAccelerator(KeyStroke.getKeyStroke("F5"));

        quit.addActionListener(e -> System.exit(0));
        statusBar.setState(true);
        statusBar.addActionListener(e -> bottomBar.setVisible(statusBar.getState()));
        wrap.addActionListener(e -> ta.setLineWrap(wrap.getState()));
        tme.addActionListener(e -> ta.append(getDate()));
        txtColor.addActionListener(e -> ta.setForeground(JColorChooser.showDialog(this, "Choose text color: ", null)));
        bgColor.addActionListener(e -> ta.setBackground(JColorChooser.showDialog(this, "Choose Background color: ", null)));
        goTo.addActionListener(e -> goTo());
        select.addActionListener(e -> ta.selectAll());
        cut.addActionListener(e -> ta.cut());
        copy.addActionListener(e -> ta.copy());
        paste.addActionListener(e -> ta.paste());

        MenuListener menuListener = new MenuListener() {
            public void menuSelected(MenuEvent evvvv) {
                if (ta.getText().length() == 0) {
                    find.setEnabled(false);
                    nxt.setEnabled(false);
                    replace.setEnabled(false);
                    select.setEnabled(false);
                    undo.setEnabled(false);
                    bing.setEnabled(false);
                    goTo.setEnabled(false);
                } else {
                    find.setEnabled(true);
                    nxt.setEnabled(true);
                    replace.setEnabled(true);
                    select.setEnabled(true);
                    undo.setEnabled(true);
                    bing.setEnabled(true);
                    goTo.setEnabled(true);
                }

                if (ta.getSelectionStart() == ta.getSelectionEnd()) {
                    cut.setEnabled(false);
                    copy.setEnabled(false);
                    delete.setEnabled(false);
                } else {
                    cut.setEnabled(true);
                    copy.setEnabled(true);
                    delete.setEnabled(true);
                }

                goTo.setEnabled(!ta.getLineWrap());
            }

            public void menuDeselected(MenuEvent evvvv) {}

            public void menuCanceled(MenuEvent evvvv) {}
        };
        editMenu.addMenuListener(menuListener);

        ta.addCaretListener(e -> {
            int lineNumber = 0, column = 0, pos;
            try {
                pos = ta.getCaretPosition();
                lineNumber = ta.getLineOfOffset(pos);
                column = pos - ta.getLineStartOffset(lineNumber);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (ta.getText().length() == 0) {
                lineNumber = 0;
                column = 0;
            }
            bottom.setText("Ln " + (lineNumber + 1) + ", Col " + (column + 1));
        });

        try {
            UIManager.setLookAndFeel(looks[1].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);

    }

    void goTo() {
        int lineNumber;
        try {
            lineNumber = ta.getLineOfOffset(ta.getCaretPosition()) + 1;
            String tempStr = JOptionPane.showInputDialog(this, "Enter Line Number:", "" + lineNumber);
            if (tempStr == null) {return; }
            lineNumber = Integer.parseInt(tempStr);
            ta.setCaretPosition(ta.getLineStartOffset(lineNumber - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getDate() {
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateObj.format(formatter);
    }

    private void changeTheLookAndFeel(int value) {
        try {
            UIManager.setLookAndFeel(looks[value].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
