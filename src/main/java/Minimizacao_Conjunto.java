package main.java;

import java.util.ArrayList;
public class Minimizacao_Conjunto {

    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private ArrayList<String> alfabeto = new ArrayList<String>();
    private ArrayList<Transicao> transicoes = new ArrayList<Transicao>();
    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinais = new ArrayList<Estado>();

    private ArrayList<Estado> estadosMinimizados = new ArrayList<Estado>();
    private ArrayList<Transicao> transicoesMinimizados = new ArrayList<Transicao>();
    
    public Minimizacao_Conjunto(ArrayList<Estado> estados, ArrayList<String> alfabeto, ArrayList<Transicao> transicoes,
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

    public Automato minimizar(){
        //Separar, em grupos, os estados não finais do estados finais

        ArrayList<ArrayList<Estado>> conjunto = separarEstadosEmGrupos();

        // Refinar os grupos. Criando novas partições até que a partição gerada seja igual a partição corrente

        refinarGrupos(conjunto);
    
        Automato automatoMinimizado = contruirAutomatoMinimizado(conjunto);

        return automatoMinimizado;
    }

    private void refinarGrupos(ArrayList<ArrayList<Estado>> conjunto) {
        boolean refinado = true;
        do{
            refinado = false;
            ArrayList<ArrayList<Estado>> novoConjunto = new ArrayList<ArrayList<Estado>>();

            for (ArrayList<Estado> grupo : conjunto) {
            ArrayList<Estado> subGrupo = new ArrayList<>();
              System.out.println(grupo);
                for (Estado estado : grupo) {

                    if (podeSerRefinado(estado, grupo, conjunto)) {
                       subGrupo.add(estado);
                       //grupo.remove(estado); /* Como está retirando o primeiro está bagunçando tudo */
                       break;
                    } else {
                        refinado = false;
                    }
                    
                }
                System.out.println("oi0000000000");
                System.out.println(grupo);
                if (!subGrupo.isEmpty()) {
                    refinado = true;
                    grupo.removeAll(subGrupo);
                    novoConjunto.add(subGrupo);
                    System.out.println("oi");
                }
            }
            //System.out.println(conjunto);
            conjunto.addAll(novoConjunto);
            //System.out.println(conjunto);

        }while(refinado);
    }

    private ArrayList<ArrayList<Estado>> separarEstadosEmGrupos() {
        ArrayList<ArrayList<Estado>> conjunto = new ArrayList<ArrayList<Estado>>();

        ArrayList<Estado> estadosNaoFinais = new ArrayList<Estado>(estados);
        estadosNaoFinais.removeAll(estadosFinais);

        conjunto.add(estadosNaoFinais); // G1
        conjunto.add(estadosFinais); // G2

        return conjunto;
    }
/*Tenho que rever a logica desse metodo -- está completamente maluca */ // Resolvido
    public boolean podeSerRefinado(Estado estado, ArrayList<Estado> grupoAtual, ArrayList<ArrayList<Estado>> Conjunto){
            
         for (Estado estado2 : grupoAtual) {
            if (estado != estado2) {
                for (String simbolo : alfabeto) {
                    int destino = DestinoPelaOrigem(simbolo, estado.getId());
                    int destino2 = DestinoPelaOrigem(simbolo, estado2.getId());

                    if (!estaNoConjunto(destino2, destino, Conjunto)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean estaNoConjunto(int id, ArrayList<Integer> grupo){
        return grupo.contains(id);
    }

    public boolean estaNoConjunto(int destino2, int destino,ArrayList<ArrayList<Estado>> Conjunto){
        for (ArrayList<Estado> grupo : Conjunto) {
            boolean estadoGrupo = estaNoConjunto(destino, idsDoGrupo(grupo));
            boolean outroEstadoGrupo = estaNoConjunto(destino2, idsDoGrupo(grupo));

            if (estadoGrupo && outroEstadoGrupo) {
                return true;
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

    //Construir os estados do automato pelos grupos criados anteriormente
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
                
                    int destino = DestinoPelaOrigem(simbulo, grupo.get(0).getId());
                    
                    for (int j = 0; j < Conjunto.size(); j++) {
                        
                        if (EncontarDestino(Conjunto.get(j), destino)) {
                            Transicao novaTransicao = new Transicao(estadoMinimmizado.getId(),IdMinimizado(j).getId(), simbulo);
                            transicoesMinimizados.add(novaTransicao);
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

    public boolean Final(ArrayList<Estado>grupo){
        for (Estado estado : grupo) {
            if (estado.isFinall()) {
                return true;
            }
        }
        return false;
    }

    public boolean estaNoConjunto(ArrayList<Estado> grupo){
        return grupo.contains(estadoInicial);
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

    // Eliminar os estados mortos
    public void EliminarEstadosMortos(){
        
    }
}   
