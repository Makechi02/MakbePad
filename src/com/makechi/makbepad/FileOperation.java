package com.makechi.makbepad;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;

class FileOperation {
    UI npd;

    boolean saved;
    boolean newFileFlag;
    String fileName;
    String applicationTitle = "Makbepad";

    File fileRef;
    JFileChooser chooser;

    FileOperation(UI npd) {
        this.npd = npd;

        saved = true;
        newFileFlag = true;
        fileName = "Untitled";
        fileRef = new File(fileName);
        this.npd.setTitle(fileName + " - " + applicationTitle);

        chooser = new JFileChooser();
        //chooser.addChoosableFileFilter(new MyFileFilter(".java", "Java Source Files(*.java)"));
        //chooser.addChoosableFileFilter(new MyFileFilter(".txt", "Text Files(*.txt)"));
        chooser.setCurrentDirectory(new File("."));
    }

    boolean saveFile(File temp) {
        try (FileWriter fout = new FileWriter(temp)) {
            fout.write(npd.ta.getText());
        } catch (IOException ioe) {
            updateStatus(temp, false);
            return false;
        }
        updateStatus(temp, true);
        return true;
    }

    void saveThisFile() {
        if (!newFileFlag) {
            saveFile(fileRef);
            return;
        }
        saveAsFile();
    }

    boolean saveAsFile() {
        File temp;
        chooser.setDialogTitle("Save As...");
        chooser.setApproveButtonText("Save Now");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
        chooser.setApproveButtonToolTipText("Click me to save!");

        do {
            if (chooser.showSaveDialog(this.npd) != JFileChooser.APPROVE_OPTION)
                return false;
            temp = chooser.getSelectedFile();
            if (!temp.exists())
                break;
            if (JOptionPane.showConfirmDialog(
                    this.npd, "<html>" + temp.getPath() + " already exists.<br>Do you want to replace it?<html>",
                    "Save As", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                break;
        } while (true);

        return saveFile(temp);
    }

    boolean openFile(File temp) {
        try (FileInputStream fin = new FileInputStream(temp); BufferedReader din = new BufferedReader(new InputStreamReader(fin))) {
            String str;
            while (true) {
                str = din.readLine();
                if (str == null)
                    break;
                this.npd.ta.append(str + "\n");
            }
        } catch (IOException ioe) {
            updateStatus(temp, false);
            return false;
        }
        updateStatus(temp, true);
        this.npd.ta.setCaretPosition(0);
        return true;
    }

    void openFile() {
        if (!confirmSave())
            return;
        chooser.setDialogTitle("Choose file:...");
        chooser.setApproveButtonText("Open");
        chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
        chooser.setApproveButtonToolTipText("Click me to open the selected file.!");

        File temp;
        do {
            if (chooser.showOpenDialog(this.npd) != JFileChooser.APPROVE_OPTION)
                return;
            temp = chooser.getSelectedFile();
            if (temp.exists())
                break;
            JOptionPane.showMessageDialog(this.npd,
                    "<html>" + temp.getName() + "<br>file not found.<br>" +
                            "Please verify the correct file name was given.<html>",
                    "Open", JOptionPane.INFORMATION_MESSAGE);

        } while (true);

        this.npd.ta.setText("");

        if (!openFile(temp)) {
            fileName = "Untitled";
            saved = true;
            this.npd.setTitle(fileName + " - " + applicationTitle);
        }
        if (!temp.canWrite())
            newFileFlag = true;
    }

    void updateStatus(File temp, boolean saved) {
        if (saved) {
            this.saved = true;
            fileName = temp.getName();
            if (!temp.canWrite()) {
                fileName += "(Read only)";
                newFileFlag = true;
            }
            fileRef = temp;
            npd.setTitle(fileName + " - " + applicationTitle);
            npd.bottom.setText("File : " + temp.getPath() + " saved/opened successfully.");
            newFileFlag = false;
        } else {
            npd.bottom.setText("Failed to save/open : " + temp.getPath());
        }
    }

    boolean confirmSave() {
        String strMsg = "<html>The text in the " + fileName + " file has been changed.<br>" +
                "Do you want to save the changes?<html>";
        if (!saved) {
            int x = JOptionPane.showConfirmDialog(this.npd, strMsg, applicationTitle,
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (x == JOptionPane.CANCEL_OPTION)
                return false;
            return x != JOptionPane.YES_OPTION || saveAsFile();
        }
        return true;
    }

    void newFile() {
        if (!confirmSave())
            return;
        this.npd.ta.setText("");
        fileName = "Untitled";
        fileRef = new File(fileName);
        saved = true;
        newFileFlag = true;
        this.npd.setTitle(fileName + " - " + applicationTitle);
    }
}
