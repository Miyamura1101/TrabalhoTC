package main.java;

import java.util.ArrayList;

public class ExcluirEstadosMortos {

    private ArrayList<Estado> estados = new ArrayList<Estado>();
    private ArrayList<Transicao> transicoes = new ArrayList<Transicao>();
    private Estado estadoInicial;
    private ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
    
    public ExcluirEstadosMortos(ArrayList<Estado> estados, ArrayList<Transicao> transicoes,
            Estado estadoInicial, ArrayList<Estado> estadosFinais) {
        this.estados = estados;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }
    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public ArrayList<Transicao> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(ArrayList<Transicao> transicoes) {
        this.transicoes = transicoes;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public ArrayList<Estado> getEstadosFinais() {
        return estadosFinais;
    }

    public void setEstadosFinais(ArrayList<Estado> estadosFinais) {
        this.estadosFinais = estadosFinais;
    }

    public void EliminarEstadosMortos(){
        
        ArrayList<Integer> estadoAcessiveis = new ArrayList<Integer>();
         identificarEstadosAcessiveis(estadoInicial, estadoAcessiveis);

        ArrayList<Integer> estadosMortos = new ArrayList<Integer>();
        for (Estado estado : estadosFinais) {
            indentificarEstadosQueAlcancamFinal(estado, estadosMortos);
        }
        ArrayList<Estado> estadosRemover = new ArrayList<Estado>();

        for (Estado estado : estados) {
            if (!estadoAcessiveis.contains(estado.getId())) {
                estadosRemover.add(estado);
            }
        }
        for (Estado estado : estados) {
            if (!estadosMortos.contains(estado.getId())) {
                estadosRemover.add(estado);
            }
        }
        estados.removeAll(estadosRemover);
        todasTransicoesMortas(estadosRemover);
    }

    public void todasTransicoesMortas(ArrayList<Estado> estadosMortos){
        ArrayList<Transicao> transicoesMortas = new ArrayList<Transicao>();
        for (Transicao transicao : transicoes) {
            for (Estado estado : estadosMortos) {
                if (estado.getId() == transicao.getEstado_Inicial() || estado.getId() == transicao.getEstado_Final()) {
                    transicoesMortas.add(transicao);
                }
            }
            
        }
        transicoes.remove(transicoesMortas);
    }

    public void identificarEstadosAcessiveis(Estado estado, ArrayList<Integer> estadosAcessiveis){
        if (estadosAcessiveis.contains(estado.getId())) {
            return;
        }
        
        estadosAcessiveis.add(estado.getId());

        for (Transicao transicao : transicoes) {
            if (transicao.getEstado_Inicial() == estado.getId()) {
                Estado destino = getEstadoId(transicao.getEstado_Final());

                if (destino != null) {
                    identificarEstadosAcessiveis(destino, estadosAcessiveis);
                }

            }
        }
    }

    public void indentificarEstadosQueAlcancamFinal(Estado estado, ArrayList<Integer> estadosQueAlcancamFinal){
        if (estadosQueAlcancamFinal.contains(estado.getId())) {
            return;
        }

        estadosQueAlcancamFinal.add(estado.getId());

        for (Transicao transicao : transicoes) {
            if (transicao.getEstado_Final() == estado.getId()) {
                Estado origem = getEstadoId(transicao.getEstado_Inicial());
                if (origem != null) {
                    indentificarEstadosQueAlcancamFinal(origem, estadosQueAlcancamFinal);
                }
            }
        }
    }

    private Estado getEstadoId(int id){
        for (Estado estado : estados) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }

}
