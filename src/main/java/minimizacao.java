package main.java;
import java.util.ArrayList;

public class minimizacao {

    ArrayList<ListaMinimizacao> list = new ArrayList<>();

    public void equivalente(){
       for (ListaMinimizacao lista : list) {
        if (lista.isEquivalente()) {
            
            for (Transicao transicao1 : lista.getTransicao_1()) {
                for (Transicao transicoes2 : lista.getTransicao_2()) {
                    if (transicao1.getSimbulo().equals(transicoes2.getSimbulo())) {
                        if (transicao1.getEstado_Final() != transicoes2.getEstado_Final() ) {
                            acharIguais(transicao1, transicoes2);
                        }
                    }
                }
            }   
        }
    }
}
    public  void acharIguais(Transicao transicao1, Transicao transicoes2){
        for (ListaMinimizacao lisat : list) {
            if (transicao1.getEstado_Final() == lisat.getId_1() && transicoes2.getEstado_Final() == lisat.getId_2()) {
                lisat.listadd(lisat);
            }
        }
    }
    public void lsi(){
        ArrayList<ListaMinimizacao> visitados = new ArrayList<>();
        for (ListaMinimizacao lista : list) {
            if (!lista.isEquivalente() && !visitados.contains(lista)) {
                visitados.add(lista); // Para não entrar na celula duas vezes

                 if (lista.getTransicao_revisao() != null) {
                    for (ListaMinimizacao listaMinimizacao : lista.getTransicao_revisao()) {
                        listaMinimizacao.setEquivalente(false);
                    }
                }
            }
        }
    }

    public void criarLista(Automato afd){
        for (Estado q1 : afd.getEstados()) {
            for (Estado q2 : afd.getEstados()) {
                if (q1.getId() > q2.getId()) {
                    
                    ArrayList<Transicao> transicoes1 = new ArrayList<>();
                    ArrayList<Transicao> transicaos2 = new ArrayList<>();
                    
                    if (afd.getTransicoes() != null) {
                        for (Transicao trans : afd.getTransicoes()) {
                            if (q1.getId() == trans.getEstado_Inicial()) {
                                transicoes1.add(trans);
                            }else if (q2.getId() == trans.getEstado_Inicial()) {
                                transicaos2.add(trans);
                            }
                        }
                    } 
                   list.add(new ListaMinimizacao(q1.getId(),q2.getId(),transicoes1,transicaos2,q1.isFinall(),q2.isFinall()));
                }
            }
        }
    }
}
