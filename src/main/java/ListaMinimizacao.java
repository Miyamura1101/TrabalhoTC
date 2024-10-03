package main.java;

import java.util.ArrayList;

public class ListaMinimizacao {

    private int id_1;
    private int id_2;
    private ArrayList<Transicao> transicao_1 = new ArrayList<>();
    private ArrayList<Transicao> transicao_2 = new ArrayList<>();
    private ArrayList<ListaMinimizacao> transicao_revisao = new ArrayList<>();
    private boolean finall_1;
    private boolean finall_2;
    private boolean equivalente;
    
    public int getId_1() {
        return id_1;
    }
    public void setId_1(int id_1) {
        this.id_1 = id_1;
    }
    public int getId_2() {
        return id_2;
    }
    public void setId_2(int id_2) {
        this.id_2 = id_2;
    }
    public ArrayList<Transicao> getTransicao_1() {
        return transicao_1;
    }
    public void setTransicao_1(ArrayList<Transicao> transicao_1) {
        this.transicao_1 = transicao_1;
    }
    public ArrayList<Transicao> getTransicao_2() {
        return transicao_2;
    }
    public void setTransicao_2(ArrayList<Transicao> transicao_2) {
        this.transicao_2 = transicao_2;
    }
    public ArrayList<ListaMinimizacao> getTransicao_revisao() {
        return transicao_revisao;
    }
    public void setTransicao_revisao(ArrayList<ListaMinimizacao> transicao_revisao) {
        this.transicao_revisao = transicao_revisao;
    }
    public void listadd(ListaMinimizacao lista){
        transicao_revisao.add(lista);
    }
    public boolean isFinall_1() {
        return finall_1;
    }
    public void setFinall_1(boolean finall_1) {
        this.finall_1 = finall_1;
    }
    public boolean isFinall_2() {
        return finall_2;
    }
    public void setFinall_2(boolean finall_2) {
        this.finall_2 = finall_2;
    }
    public boolean isEquivalente() {
        return equivalente;
    }
    public void setEquivalente(boolean equivalente) {
        this.equivalente = equivalente;
    }
    public ListaMinimizacao(int id_1, int id_2, ArrayList<Transicao> transicoes1, ArrayList<Transicao> transicaos2, boolean finall_1,
            boolean finall_2) {
        this.id_1 = id_1;
        this.id_2 = id_2;
        this.transicao_1 = transicoes1;
        this.transicao_2 = transicaos2;
        this.finall_1 = finall_1;
        this.finall_2 = finall_2;
    } 
    public void finall(){
        if (finall_1 != finall_2) {
            equivalente = false;
        }else{
            equivalente = true;
        }
    }
}
