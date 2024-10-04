package main.java;

import javax.swing.JFileChooser;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        minimizacao minimizacao = new minimizacao();

        // Abre o explorador de arquivos para selecionar um arquivo XML
        File arquivoSelecionado = abrirExploradorDeArquivos();

    }

    // Função para abrir o explorador de arquivos
    public static File abrirExploradorDeArquivos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo JFLAP (.xml)");

        // Filtrar apenas arquivos com extensão .xml
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos XML", "xml"));

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
