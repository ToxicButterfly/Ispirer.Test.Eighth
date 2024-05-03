package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

public class FileEncoderApp extends JFrame {

    private JLabel inputLabel;
    private JLabel outputLabel;
    private JComboBox<String> inputEncodingComboBox;
    private JComboBox<String> outputEncodingComboBox;
    private JButton convertButton;

    public FileEncoderApp() {
        setTitle("Перекодировщик файлов");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inputLabel = new JLabel("Кодировка входного файла:");
        outputLabel = new JLabel("Кодировка выходного файла:");

        inputEncodingComboBox = new JComboBox<>(Charset.availableCharsets().keySet().toArray(new String[0]));
        outputEncodingComboBox = new JComboBox<>(Charset.availableCharsets().keySet().toArray(new String[0]));

        convertButton = new JButton("Перекодировать");
        convertButton.addActionListener(e -> encodeFile());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(inputLabel);
        panel.add(inputEncodingComboBox);
        panel.add(outputLabel);
        panel.add(outputEncodingComboBox);
        panel.add(convertButton);

        getContentPane().add(panel);
    }

    private void encodeFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            Charset inputCharset = Charset.forName((String) Objects.requireNonNull(inputEncodingComboBox.getSelectedItem()));
            Charset outputCharset = Charset.forName((String) Objects.requireNonNull(outputEncodingComboBox.getSelectedItem()));
            File outputFile = new File(inputFile.getParent(), "output_" + inputFile.getName());

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), inputCharset));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), outputCharset))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Файл успешно перекодирован. Результат сохранен в файле: " + outputFile.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при перекодировании файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}