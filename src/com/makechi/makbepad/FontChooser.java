package com.makechi.makbepad;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class FontChooser extends JDialog {

    private JList<String>  lstSize;
    private JList<String> lstFont;
    private JList<String>  lstStyle;
    private JLabel lblPreview;
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private Font font;
    private int returnStatus = RET_CANCEL;

    public FontChooser(JFrame owner, Font font) {
        super(owner);
        this.font = font;
        initComponents();
        lblPreview.setFont(font);
    }

    public FontChooser(JFrame owner) {
        super(owner);
        this.font = new Font("Dialog",Font.PLAIN,12);
        initComponents();
        lblPreview.setFont(font);
    }

    public FontChooser(Font font) {
        super((JFrame) null);
        this.font = font;
        initComponents();
        lblPreview.setFont(font);
    }

    public FontChooser() {
        super((JFrame)null);
        this.font = new Font("Dialog",Font.PLAIN,12);
        initComponents();
        lblPreview.setFont(font);
    }

    public Font getFont(){ return font; }

    public int getReturnStatus() { return returnStatus; }

    private void initComponents() {
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(1, 1, 1, 1);
        cons.weightx = 2.0;

        JPanel mainPanel = new JPanel();
        JPanel fontPanel = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        lstFont = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        JScrollPane jScrollPane2 = new JScrollPane();
        lstStyle = new JList<>();
        JScrollPane jScrollPane3 = new JScrollPane();
        lstSize = new JList<>();
        JPanel previewPanel = new JPanel();
        lblPreview = new JLabel();
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton();
        JButton cancelButton = new JButton();

        setTitle("Select Font");
        setModal(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });

        mainPanel.setLayout(new GridLayout(2, 1));

        fontPanel.setLayout(new GridBagLayout());

        jLabel1.setText("Font");
        fontPanel.add(jLabel1, cons);

        jLabel2.setText("Style");
        fontPanel.add(jLabel2, cons);

        jLabel3.setText("Size");
        fontPanel.add(jLabel3, cons);

        lstFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstFont.addListSelectionListener(evt -> lstFontValueChanged(evt));

        jScrollPane1.setViewportView(lstFont);

        cons.gridx = 0;
        cons.gridy = 1;
        cons.ipadx = 1;
        fontPanel.add(jScrollPane1, cons);

        lstStyle.setModel(new AbstractListModel<>() {
            String[] strings = { "Plain", "Bold", "Italic", "Bold Italic" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstStyle.addListSelectionListener(this::lstStyleValueChanged);

        jScrollPane2.setViewportView(lstStyle);

        cons.gridx = 1;
        cons.gridy = 1;
        fontPanel.add(jScrollPane2, cons);

        lstSize.setModel(new AbstractListModel<>() {
            final String[] strings = { "8", "10", "11", "12", "14", "16", "20", "24", "28", "36", "48", "72", "96" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstSize.addListSelectionListener(this::lstSizeValueChanged);

        jScrollPane3.setViewportView(lstSize);

        cons.gridx = 2;
        cons.gridy = 1;
        cons.weightx = 0.2;
        fontPanel.add(jScrollPane3, cons);

        mainPanel.add(fontPanel);

        previewPanel.setLayout(new BorderLayout());

        previewPanel.setBorder(new TitledBorder(null, "Preview", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", 0, 12)));
        lblPreview.setFont(new Font("Dialog", 0, 12));
        lblPreview.setText("ABCDEFG abcdefg");
        previewPanel.add(lblPreview, BorderLayout.CENTER);

        mainPanel.add(previewPanel);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        okButton.setText("OK");
        okButton.addActionListener(this::okButtonActionPerformed);

        buttonPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this::cancelButtonActionPerformed);

        buttonPanel.add(cancelButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension(443, 429));
        setLocation((screenSize.width-443)/2,(screenSize.height-429)/2);
    }

    private void lstStyleValueChanged(ListSelectionEvent evt) {
        int style = -1;
        String selStyle = lstStyle.getSelectedValue();
        if(Objects.equals(selStyle, "Plain"))
            style = Font.PLAIN;
        if(Objects.equals(selStyle, "Bold"))
            style = Font.BOLD;
        if(Objects.equals(selStyle, "Italic"))
            style = Font.ITALIC;
        if(Objects.equals(selStyle, "Bold Italic"))
            style = Font.BOLD + Font.ITALIC;

        font = new Font(font.getFamily(),style,font.getSize());
        lblPreview.setFont(font);
    }

    private void lstSizeValueChanged(ListSelectionEvent evt) {
        int size = Integer.parseInt(lstSize.getSelectedValue());
        font = new Font(font.getFamily(),font.getStyle(),size);
        lblPreview.setFont(font);
    }

    private void lstFontValueChanged(ListSelectionEvent evt) {
        font = new Font(lstFont.getSelectedValue(),font.getStyle(),font.getSize());
        lblPreview.setFont(font);
    }

    private void okButtonActionPerformed(ActionEvent evt) { doClose(RET_OK); }

    private void cancelButtonActionPerformed(ActionEvent evt) { doClose(RET_CANCEL); }

    private void closeDialog(WindowEvent evt) { doClose(RET_CANCEL); }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
    }

}
