package main.java;

import java.io.File;

import javax.swing.JFileChooser;

public class SeletorDeArquivo {

    public File selecionarArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }
}
