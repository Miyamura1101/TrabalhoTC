package main.java;
public class Main {

    public static void main(String[] args) {
        ConversorAFNparaAFD conversorAFNparaAFD = new ConversorAFNparaAFD();
        
        Automato afd = conversorAFNparaAFD.getAFD();

        afd.salvarEmArquivo("automato.jff");
    }
}
