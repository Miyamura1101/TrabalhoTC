package main.java;
import java.util.ArrayList;
import java.util.List;

public class Automato {

    private List<Estado> estados;
    private List<Transicao> transicoes;

    public Automato() {
        this.estados = new ArrayList<>();
        this.transicoes = new ArrayList<>();
    }

    public void addEstado(Estado estado){
        this.estados.add(estado);
    }

    public void addTransicoe(Transicao transicao){
        this.transicoes.add(transicao);
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    
    public String toXml(){
        StringBuilder construtor = new StringBuilder();
        construtor.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        construtor.append("<!-- Created with JFLAP 7.1.-->");
        construtor.append("<structure>\n");
        construtor.append("\t<type>fa</type>\n");// fa --> Não sei para que serve essa variavel;
        construtor.append("\t<automaton>\n");

        construtor.append("\t<!--The list of state.-->\n");
        for (Estado estado : estados) {
            construtor.append(estado.toXml());
        }

        construtor.append("\t<!--The list of transitions.-->\n");
        for (Transicao transicao : transicoes) {
            construtor.append(transicao.toXml());
        }

        construtor.append("\t</automaton>\n");
        construtor.append("</structure>");

        return construtor.toString();
    }

    public void salvarEmArquivo(String nomeArquivo){
        try(java.io.FileWriter escrever = new java.io.FileWriter(nomeArquivo)) {
            escrever.write(toXml());
            System.out.println("Salvo com sucesso");
        } catch (Exception e) {
            System.out.println("Deu algum problema, tente novamente");
        }
    }

}
