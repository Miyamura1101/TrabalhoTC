package main.java;

import java.util.ArrayList;
public class Minimizacao_Teste {

    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private ArrayList<String> alfabeto = new ArrayList<String>();
    private ArrayList<Transicao> transicoes = new ArrayList<Transicao>();
    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinais = new ArrayList<Estado>();

    private ArrayList<Estado> estadosMinimizados = new ArrayList<Estado>();
    private ArrayList<Transicao> transicoesMinimizados = new ArrayList<Transicao>();
    
    public Minimizacao_Teste(ArrayList<Estado> estados, ArrayList<String> alfabeto, ArrayList<Transicao> transicoes,
            Estado estadoInicial, ArrayList<Estado> estadosFinais) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
    }

    public int DestinoPelaOrigem(String simbulo, int Id_Origem){
        for (Transicao transicao : transicoes) {
            if (transicao.getSimbulo().equals(simbulo)) {
                if (transicao.getEstado_Inicial() == Id_Origem) {
                    return transicao.getEstado_Final();
                }
            }
        }
        return -1;
    }
    
    public boolean estaNoConjunto(int id, ArrayList<Integer> Conjunto){
        return Conjunto.contains(id);
    }

    public Automato minimizar(){
        ArrayList<ArrayList<Estado>> conjunto = new ArrayList<ArrayList<Estado>>();

        ArrayList<Estado> estadosNaoFinais = new ArrayList<Estado>(estados);
        estadosNaoFinais.removeAll(estadosFinais);

        conjunto.add(estadosNaoFinais); // G1
        conjunto.add(estadosFinais); // G2

        boolean refinado = true;
        do{
            refinado = false;
            ArrayList<ArrayList<Estado>> novoConjunto = new ArrayList<ArrayList<Estado>>();

            for (ArrayList<Estado> grupo : conjunto) {
                ArrayList<Estado> subGrupo = new ArrayList<>();

                for (Estado estado : grupo) {
                    if (podeSerRefinado(estado, grupo, conjunto)) {
                        subGrupo.add(estado);
                    }
                    
                }
                
                if (!subGrupo.isEmpty()) {
                    refinado = true;
                    novoConjunto.add(subGrupo);
                    grupo.removeAll(subGrupo);
                }
               

                if (!grupo.isEmpty()) {
                    novoConjunto.add(grupo);
                }

            }
            if (!novoConjunto.isEmpty()) {
                conjunto = novoConjunto;
            }
        }while(refinado);
        return contruirAutomatoMinimizado(conjunto);
    }
/*Tenho que rever a logica desse metodo -- está completamente maluca */
    public boolean podeSerRefinado(Estado estado, ArrayList<Estado> grupoAtual, ArrayList<ArrayList<Estado>> Conjunto){
  
        for (String simbolo : alfabeto) {
            int destino = DestinoPelaOrigem(simbolo, estado.getId());
            if (destino == -1) { // Correto != -1 -- O que funciona == -1 
                for (ArrayList<Estado> grupo : Conjunto) {
                    if (estaNoConjunto(destino, idsDoGrupo(grupo))) {
                        
                        if (!grupoAtual.contains(estados.get(destino))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Integer> idsDoGrupo(ArrayList<Estado> grupo){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Estado estado : grupo) {
            ids.add(estado.getId());
        }
        return ids;
    }

    public Automato contruirAutomatoMinimizado(ArrayList<ArrayList<Estado>> Conjunto){
        int id = 0;
        for (ArrayList<Estado> grupo : Conjunto) {
            Estado estado = new Estado(id, "q"+id, Inicial(grupo), Final(grupo), 300, 300);
            estadosMinimizados.add(estado);
            
            id++;
        }

        for (int i = 0; i < Conjunto.size(); i++) {
            ArrayList<Estado> grupo = Conjunto.get(i);
            Estado estadoMinimmizado = estadosMinimizados.get(i);
            for (String simbulo : alfabeto) {
                
                for (Estado estado : grupo) {
                    int destino = DestinoPelaOrigem(simbulo, estado.getId());
                    
                    for (int j = 0; j < Conjunto.size(); j++) {
                        
                        if (EncontarDestino(Conjunto.get(j), destino)) {
                            Transicao novaTransicao = new Transicao(estadoMinimmizado.getId(),IdMinimizado(j).getId(), simbulo);
                            transicoesMinimizados.add(novaTransicao);
                        }
                    }
                }
            }
        }

        Automato automatoMinimizado = new Automato(estadosMinimizados, transicoesMinimizados);
        return automatoMinimizado;
    }

    public Estado IdMinimizado(int id){
        for (Estado estado : getEstadosMinimizados()) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }    

    public ArrayList<Estado> getEstadosMinimizados() {
        return estadosMinimizados;
    }

    public boolean estaNoConjunto(ArrayList<Estado> grupo){
       return grupo.contains(estadoInicial);
    }

    public boolean Final(ArrayList<Estado>grupo){
        for (Estado estado : grupo) {
            if (estadosFinais.contains(estado)) {
                return true;
            }
        }
        return false;
    }
    public boolean Inicial(ArrayList<Estado>grupo){
        if (estaNoConjunto(grupo)) {
            return true;
        }else{
            return false;
        }
    }

    public boolean EncontarDestino(ArrayList<Estado> grupo, int destino){
        for (Estado estado : grupo) {
            if (estado.getId() == destino) {
                return true;
            }
        }
        return false;
    }
}   
