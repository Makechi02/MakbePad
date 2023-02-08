package com.makbe.pad;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class FontChooser extends JDialog {

    private final JPanel mainPanel = new JPanel();
    private final JList<String> fontsList = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    private final JList<String> stylesList = new JList<>();
    private final JList<String> sizesList = new JList<>();
    private final JLabel label = new JLabel("ABCDEFG abcdefg");
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private Font font;
    private int returnStatus = RET_CANCEL;

    public FontChooser(JFrame owner, Font font) {
        super(owner);
        this.font = font;
        initComponents();
        label.setFont(font);
        setVisible(true);
    }

    public FontChooser(JFrame owner) {
        super(owner);
        this.font = new Font("Dialog", Font.PLAIN, 12);
        initComponents();
        label.setFont(font);
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    public FontChooser(Font font) {
        super((JFrame) null);
        this.font = font;
        initComponents();
        label.setFont(font);
        setVisible(true);
    }

    public FontChooser() {
        super((JFrame) null);
        this.font = new Font("Dialog", Font.PLAIN, 12);
        initComponents();
        label.setFont(font);
        setVisible(true);
    }

    public Font getFont() { return font; }

    public int getReturnStatus() { return returnStatus; }

    private void initComponents() {
        setTitle("Select Font");
        setModal(true);
        setResizable(false);
        setUndecorated(true);
        setSize(640, 430);

        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(0, 0, 640, 240);
        fontPanel.setLayout(null);

        JLabel title = new JLabel("Font");
        title.setBounds(10, 5, 300, 30);
        fontPanel.add(title);

        title = new JLabel("Style");
        title.setBounds(320, 5, 120, 30);
        fontPanel.add(title);

        title = new JLabel("Size");
        title.setBounds(480, 5, 150, 30);
        fontPanel.add(title);

        fontsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fontsList.addListSelectionListener(this::fontListValueChanged);

        JScrollPane scrollPane = new JScrollPane(fontsList);
        scrollPane.setBounds(10, 40, 300, 200);
        fontPanel.add(scrollPane);

        stylesList.setModel(new AbstractListModel<>() {
            final String[] styles = {"Plain", "Bold", "Italic", "Bold Italic"};
            public int getSize() { return styles.length; }
            public String getElementAt(int i) { return styles[i]; }
        });
        stylesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stylesList.addListSelectionListener(this::styleListValueChanged);
        stylesList.setBounds(320, 40, 150, 200);
        fontPanel.add(stylesList);

        sizesList.setModel(new AbstractListModel<>() {
            final String[] sizes = {"8", "10", "11", "12", "14", "16", "20", "24", "28", "36", "48", "72", "96"};
            public int getSize() { return sizes.length; }
            public String getElementAt(int i) { return sizes[i]; }
        });
        sizesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sizesList.addListSelectionListener(this::sizeListValueChanged);

        scrollPane = new JScrollPane(sizesList);
        scrollPane.setBounds(480, 40, 150, 200);
        fontPanel.add(scrollPane);

        JPanel previewPanel = new JPanel();
        previewPanel.setLayout(new BorderLayout());
        previewPanel.setBounds(10, 250, 620, 120);
        previewPanel.setBorder(new TitledBorder(null, "Preview", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12)));
        label.setFont(new Font("Dialog", Font.PLAIN, 12));
        previewPanel.add(label, BorderLayout.CENTER);

        mainPanel.setLayout(null);
        mainPanel.add(fontPanel);
        mainPanel.add(previewPanel);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton ok_button = new JButton("Ok");
        ok_button.addActionListener(this::okButtonActionPerformed);
        buttonPanel.add(ok_button);

        JButton cancel_button = new JButton("Cancel");
        cancel_button.addActionListener(this::cancelButtonActionPerformed);
        buttonPanel.add(cancel_button);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
    }

    private void styleListValueChanged(ListSelectionEvent evt) {
        int style = -1;
        String selectedStyle = stylesList.getSelectedValue();
        if (Objects.equals(selectedStyle, "Plain"))
            style = Font.PLAIN;
        if (Objects.equals(selectedStyle, "Bold"))
            style = Font.BOLD;
        if (Objects.equals(selectedStyle, "Italic"))
            style = Font.ITALIC;
        if (Objects.equals(selectedStyle, "Bold Italic"))
            style = Font.BOLD + Font.ITALIC;

        font = new Font(font.getFamily(), style, font.getSize());
        label.setFont(font);
    }

    private void sizeListValueChanged(ListSelectionEvent evt) {
        int size = Integer.parseInt(sizesList.getSelectedValue());
        font = new Font(font.getFamily(), font.getStyle(), size);
        label.setFont(font);
    }

    private void fontListValueChanged(ListSelectionEvent evt) {
        font = new Font(fontsList.getSelectedValue(), font.getStyle(), font.getSize());
        label.setFont(font);
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        doClose(RET_OK);
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        doClose(RET_CANCEL);
    }

    private void closeDialog(WindowEvent evt) {
        doClose(RET_CANCEL);
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
    }

}
