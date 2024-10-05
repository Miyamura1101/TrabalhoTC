package main.java;

import javax.swing.JFileChooser;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        minimizacao minimizacao = new minimizacao();
            abrirExploradorDeArquivos();
        }
    
        private static void abrirExploradorDeArquivos() {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }

    }
}
