package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileEncoderApp app = new FileEncoderApp();
            app.setVisible(true);
        });
    }
}