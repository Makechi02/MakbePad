package com.makbe.pad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.KeyEvent;
import java.io.*;

public class FileOperation {
    UI npd;
    boolean saved;
    boolean newFileFlag;
    String fileName;
    String appTitle = "Makbepad";
    File fileRef;
    JFileChooser chooser;

    FileOperation(UI npd) {
        this.npd = npd;
        saved = true;
        newFileFlag = true;
        fileName = "Untitled";
        fileRef = new File(fileName);
        this.npd.setTitle(fileName + " - " + appTitle);

        chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Source Files(*.java)", ".java"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files(*.txt)", ".txt"));
        chooser.setCurrentDirectory(new File("."));

    }

    boolean saveFile(File temp) {
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(npd.textarea.getText());
        } catch (IOException ioe) {
            updateStatus(temp, false);
            return false;
        }
        updateStatus(temp, true);
        return true;
    }

    void saveFile() {
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
                this.npd.textarea.append(str + "\n");
            }
        } catch (IOException ioe) {
            updateStatus(temp, false);
            return false;
        }
        updateStatus(temp, true);
        this.npd.textarea.setCaretPosition(0);
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

        this.npd.textarea.setText("");

        if (!openFile(temp)) {
            fileName = "Untitled";
            saved = true;
            this.npd.setTitle(fileName + " - " + appTitle);
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
            npd.setTitle(fileName + " - " + appTitle);
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
            int x = JOptionPane.showConfirmDialog(this.npd, strMsg, appTitle,
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (x == JOptionPane.CANCEL_OPTION)
                return false;
            return x != JOptionPane.YES_OPTION || saveAsFile();
        }
        return true;
    }

    void newFile() {
        if (!confirmSave()) return;
        this.npd.textarea.setText("");
        fileName = "Untitled";
        fileRef = new File(fileName);
        saved = true;
        newFileFlag = true;
        this.npd.setTitle(fileName + " - " + appTitle);
    }
}
