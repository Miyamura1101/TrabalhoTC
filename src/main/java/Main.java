package main.java;
public class Main {
    public static void main(String[] args) {
        
        minimizacao minimizacao = new minimizacao();
        Automato afd = new LerArquivoAFD().LerAFD();
        
        minimizacao.criarLista(afd);
    }       
}
