package main.java;

import javax.swing.JFileChooser;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        
        ArquivoAFD afd = new ArquivoAFD();
        Automato afds = afd.LerAFD();
        Minimizacao_Teste minimizacao_Teste = new Minimizacao_Teste(afds.getEstados(), afd.getAlfabeto(), afds.getTransicoes(), afd.getEstadoInical(), afd.getEstadosFinais());

        Automato automatoMinimizado = minimizacao_Teste.minimizar();

        automatoMinimizado.salvarEmArquivo("teste.jff");
        
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
