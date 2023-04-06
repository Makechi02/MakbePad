    package com.makbe.pad;

    import javax.swing.*;
    import javax.swing.event.DocumentEvent;
    import javax.swing.event.DocumentListener;
    import javax.swing.event.MenuEvent;
    import javax.swing.event.MenuListener;
    import java.awt.*;
    import java.awt.event.*;
    import java.awt.print.PrinterException;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;

    public class UI extends JFrame implements MenuListener, DocumentListener {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width / 2;
        int height = screen.height / 2;

        private final FileOperation fileHandler;

        private final JMenuItem undo = new JMenuItem("Undo", 'U');
        private final JMenuItem cut = new JMenuItem("Cut", 't');
        private final JMenuItem copy = new JMenuItem("Copy", 'C');
        private final JMenuItem delete = new JMenuItem("Delete");
        private final JMenuItem bing = new JMenuItem("Search with Bing...");
        private final JMenuItem find = new JMenuItem("Find", 'F');
        private final JMenuItem nxt = new JMenuItem("Find Next", 'n');
        private final JMenuItem replace = new JMenuItem("Replace...", 'R');
        private final JMenuItem goTo = new JMenuItem("Go To...", 'G');
        private final JMenuItem select = new JMenuItem("Select All", 'A');
        private final JCheckBoxMenuItem wrap = new JCheckBoxMenuItem("Word Wrap");
        private final JCheckBoxMenuItem statusBar = new JCheckBoxMenuItem("Status Bar");

        JTextArea textarea = new JTextArea();
        private final JPanel bottom_bar = new JPanel();
        JLabel bottom = new JLabel("Ln 1, Col 1");

        private final UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        private final JRadioButtonMenuItem[] radio = new JRadioButtonMenuItem[looks.length];

        public UI() {
            String fileName = "Untitled";
            setTitle(fileName + " - Makbepad");
            setIconImage(new ImageIcon("images\\logo2.png").getImage());
            setSize(width + 180, height + 130);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            fileHandler = new FileOperation(this);

            String[] lookNames = new String[looks.length];
            for (int i = 0; i < looks.length; i++) {
                lookNames[i] = looks[i].getName();
            }

            JMenu look = new JMenu("Change Look and Feel");
            for (int count = 0; count < radio.length; count++) {
                radio[count] = new JRadioButtonMenuItem(lookNames[count]);
                look.add(radio[count]);

                radio[count].addItemListener(e -> {
                    for (int i = 0; i < radio.length; i++) {
                        if (radio[i].isSelected()) { changeTheLookAndFeel(i); }
                    }
                });
                ButtonGroup group = new ButtonGroup();
                group.add(radio[count]);
            }

            radio[3].setSelected(true);

            JMenuBar menu_bar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");
            JMenuItem newItem = new JMenuItem("New", 'N');
            fileMenu.add(newItem);
            JMenuItem newWindow = new JMenuItem("New Window", 'W');
            fileMenu.add(newWindow);
            JMenuItem open = new JMenuItem("Open...", 'O');
            fileMenu.add(open);
            JMenuItem save = new JMenuItem("Save", 'S');
            fileMenu.add(save);
            JMenuItem saveAs = new JMenuItem("Save As...", 'a');
            fileMenu.add(saveAs);
            fileMenu.addSeparator();
            JMenuItem pageSetup = new JMenuItem("Page Setup...");
            fileMenu.add(pageSetup);
            JMenuItem print = new JMenuItem("Print...", 'P');
            fileMenu.add(print);
            fileMenu.addSeparator();
            JMenuItem quit = new JMenuItem("Quit", 'Q');
            fileMenu.add(quit);
            menu_bar.add(fileMenu);

            JMenu editMenu = new JMenu("Edit");
            editMenu.add(undo);
            editMenu.addSeparator();
            editMenu.add(cut);
            editMenu.add(copy);
            JMenuItem paste = new JMenuItem("Paste", 'P');
            editMenu.add(paste);
            editMenu.add(delete);
            editMenu.addSeparator();
            editMenu.add(bing);
            editMenu.add(find);
            editMenu.add(nxt);
            JMenuItem prev = new JMenuItem("Find Previous");
            editMenu.add(prev);
            editMenu.add(replace);
            editMenu.add(goTo);
            editMenu.addSeparator();
            editMenu.add(select);
            JMenuItem tme = new JMenuItem("Time/Date", 'D');
            editMenu.add(tme);
            menu_bar.add(editMenu);

            JMenu formatMenu = new JMenu("Format");
            formatMenu.add(wrap);
            JMenuItem fnt = new JMenuItem("Font...", 'F');
            formatMenu.add(fnt);
            formatMenu.addSeparator();
            JMenuItem txtColor = new JMenuItem("Set Text Color...", 'T');
            formatMenu.add(txtColor);
            JMenuItem bgColor = new JMenuItem("Set Background...", 'B');
            formatMenu.add(bgColor);
            menu_bar.add(formatMenu);

            JMenu zoom = new JMenu("Zoom");
            JMenuItem in = new JMenuItem("Zoom In");
            zoom.add(in);
            JMenuItem out = new JMenuItem("Zoom Out");
            zoom.add(out);
            JMenuItem restore = new JMenuItem("Restore Default Zoom");
            zoom.add(restore);
            JMenu viewMenu = new JMenu("View");
            viewMenu.add(zoom);
            viewMenu.add(statusBar);
            viewMenu.add(look);
            menu_bar.add(viewMenu);
            JMenu helpMenu = new JMenu("Help");
            JMenuItem viewHelp = new JMenuItem("View Help", 'H');
            helpMenu.add(viewHelp);
            JMenuItem send = new JMenuItem("Send Feedback", 'F');
            helpMenu.add(send);
            helpMenu.addSeparator();
            JMenuItem about = new JMenuItem("About Makbepad", 'A');
            helpMenu.add(about);
            menu_bar.add(helpMenu);

            setJMenuBar(menu_bar);

            bottom_bar.add(bottom);
            JScrollPane scrollPane = new JScrollPane(textarea);
            add(scrollPane);
            add(bottom_bar, BorderLayout.SOUTH);
            add(new JLabel("  "), BorderLayout.EAST);
            add(new JLabel("  "), BorderLayout.WEST);

            textarea.setFont(new Font("Choco cooky", Font.PLAIN, 16));
            JPopupMenu popup = new JPopupMenu();
            textarea.setComponentPopupMenu(popup);
            JMenuItem undoPopup = new JMenuItem("Undo");
            popup.add(undoPopup);
            popup.addSeparator();
            JMenuItem cutPopup = new JMenuItem("Cut");
            popup.add(cutPopup);
            JMenuItem copyPopup = new JMenuItem("Copy");
            popup.add(copyPopup);
            JMenuItem pastePopup = new JMenuItem("Paste");
            popup.add(pastePopup);
            JMenuItem deletePopup = new JMenuItem("Delete");
            popup.add(deletePopup);
            popup.addSeparator();
            JMenuItem selectPopup = new JMenuItem("Select All");
            popup.add(selectPopup);
            popup.addSeparator();
            JMenuItem rtl = new JMenuItem("Right to Left Reading order");
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
            paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
            delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
            bing.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
            find.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
            nxt.setAccelerator(KeyStroke.getKeyStroke("F3"));
            prev.setAccelerator(KeyStroke.getKeyStroke("shift F3"));
            replace.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
            goTo.setAccelerator(KeyStroke.getKeyStroke("ctrl G"));
            select.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
            tme.setAccelerator(KeyStroke.getKeyStroke("F5"));

            statusBar.setMnemonic('S');
            look.setMnemonic('C');
            zoom.setMnemonic('Z');
            wrap.setMnemonic('W');
            txtColor.setDisplayedMnemonicIndex(4);
            fileMenu.setMnemonic('F');
            editMenu.setMnemonic('E');
            formatMenu.setMnemonic('o');
            viewMenu.setMnemonic('V');
            helpMenu.setMnemonic('H');
            newWindow.setDisplayedMnemonicIndex(4);

            quit.addActionListener(e -> {
                if (fileHandler.confirmSave())
                    System.exit(0);
            });

            statusBar.setState(true);
            statusBar.addActionListener(e -> bottom_bar.setVisible(statusBar.getState()));
            wrap.addActionListener(e -> textarea.setLineWrap(wrap.getState()));
            tme.addActionListener(e -> textarea.append(getDate()));
            txtColor.addActionListener(e -> setTextColor());
            bgColor.addActionListener(e -> setBackgroundColor());
            goTo.addActionListener(e -> goTo());
            select.addActionListener(e -> textarea.selectAll());
            cut.addActionListener(e -> textarea.cut());
            copy.addActionListener(e -> textarea.copy());
            paste.addActionListener(e -> textarea.paste());
            about.addActionListener(e -> new About(this));
            open.addActionListener(e -> fileHandler.openFile());
            saveAs.addActionListener(e -> fileHandler.saveAsFile());
            save.addActionListener(e -> fileHandler.saveFile());
            newItem.addActionListener(e -> fileHandler.newFile());
            newWindow.addActionListener(e -> new UI());
            fnt.addActionListener(e -> changeFont());
            print.addActionListener(e -> printAction());

            cutPopup.addActionListener(e -> textarea.cut());
            copyPopup.addActionListener(e -> textarea.copy());
            pastePopup.addActionListener(e -> textarea.paste());
            selectPopup.addActionListener(e -> textarea.selectAll());

            quit.setIcon(new ImageIcon("images\\exit.png"));
            about.setIcon(new ImageIcon("images\\help.png"));
            delete.setIcon(new ImageIcon("images\\delete.png"));
            print.setIcon(new ImageIcon("images\\print.png"));
            save.setIcon(new ImageIcon("images\\save.png"));
            open.setIcon(new ImageIcon("images\\open.png"));
            saveAs.setIcon(new ImageIcon("images\\saveas.png"));
            newItem.setIcon(new ImageIcon("images\\addnew.png"));
            bing.setIcon(new ImageIcon("images\\search.png"));

            editMenu.addMenuListener(this);

            textarea.addCaretListener(e -> caretListener());
            textarea.setCaretColor(Color.WHITE);
            textarea.getDocument().addDocumentListener(this);

            WindowListener frameClose = new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    if (fileHandler.confirmSave())
                        System.exit(0);
                }
            };
            addWindowListener(frameClose);

            changeTheLookAndFeel(3);

            setVisible(true);

        }

        void setTextColor() {
            textarea.setForeground(JColorChooser.showDialog(this, "Choose text color: ", null));
        }

        void setBackgroundColor() {
            textarea.setBackground(JColorChooser.showDialog(this, "Choose Background color: ", null));
        }

        void goTo() {
            try {
                int lineNumber = textarea.getLineOfOffset(textarea.getCaretPosition()) + 1;
                String line = JOptionPane.showInputDialog(this, "Enter Line Number:", String.valueOf(lineNumber));
                if (line == null) {return; }
                lineNumber = Integer.parseInt(line);
                textarea.setCaretPosition(textarea.getLineStartOffset(lineNumber - 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String getDate() {
            LocalDateTime dateObj = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return dateObj.format(formatter);
        }

        void changeTheLookAndFeel(int value) {
            try {
                UIManager.setLookAndFeel(looks[value].getClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        @Override
        public void menuSelected(MenuEvent e) {
            if (textarea.getText().length() == 0) {
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

            if (textarea.getSelectionStart() == textarea.getSelectionEnd()) {
                cut.setEnabled(false);
                copy.setEnabled(false);
                delete.setEnabled(false);
            } else {
                cut.setEnabled(true);
                copy.setEnabled(true);
                delete.setEnabled(true);
            }

            goTo.setEnabled(!textarea.getLineWrap());
        }

        @Override
        public void menuDeselected(MenuEvent e) {}
        @Override
        public void menuCanceled(MenuEvent e) {}

        void printAction() {
            try {
                textarea.print();
            } catch (PrinterException e) {
                throw new RuntimeException(e);
            }
        }

        void caretListener() {
            int lineNumber = 0, column = 0, position;
            try {
                position = textarea.getCaretPosition();
                lineNumber = textarea.getLineOfOffset(position);
                column = position - textarea.getLineStartOffset(lineNumber);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (textarea.getText().length() == 0) {
                lineNumber = 0;
                column = 0;
            }
            bottom.setText("Ln " + (lineNumber + 1) + ", Col " + (column + 1));
        }

        void changeFont() {
            FontChooser fontChooser = new FontChooser(this);
            if (fontChooser.getReturnStatus() == FontChooser.RET_OK)
                textarea.setFont(fontChooser.getFont());
        }

        @Override
        public void insertUpdate(DocumentEvent e) { fileHandler.saved = false; }
        @Override
        public void removeUpdate(DocumentEvent e) { fileHandler.saved = false; }
        @Override
        public void changedUpdate(DocumentEvent e) { fileHandler.saved = false; }

    }
