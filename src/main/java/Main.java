package main.java;
public class Main {
    public static void main(String[] args) {

       // SeletorDeArquivo seletorDeArquivo = new SeletorDeArquivo();
    
        ArquivoAFD afd = new ArquivoAFD();
        Automato afds = afd.LerAFD();
        Minimizacao_Conjunto minimizacao_Teste = new Minimizacao_Conjunto(afds.getEstados(), afd.getAlfabeto(), afds.getTransicoes(), afd.getEstadoInical(), afd.getEstadosFinais());

        Automato automatoMinimizado = minimizacao_Teste.minimizar();

        automatoMinimizado.salvarEmArquivo("teste.jff");


    }
}
