package main.java;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
        // Eleminar os estados mortos

        EliminarEstadosMortos();

        //Separar, em grupos, os estados não finais do estados finais

        ArrayList<ArrayList<Estado>> conjunto = separarEstadosEmGrupos();

        // Refinar os grupos. Criando novas partições até que a partição gerada seja igual a partição corrente

        conjunto = identificarEstadosEquivalentes(conjunto);
    
        Automato automatoMinimizado = contruirAutomatoMinimizado(conjunto);

        return automatoMinimizado;
    }

    

    // private void refinarGrupos(ArrayList<ArrayList<Estado>> gruposDeEstadosFinaisENaoFinais) {
        // Identificar pares de estados Equivalentes
        //ArrayList<ArrayList<Estado>> estadosEquivalentes = identificarEstadosEquivalentes(gruposDeEstadosFinaisENaoFinais);
        
        // Agrupar os estados em classes de equivalencia, com um representante
        
        // Criação de um novo AFD
   // }

    private ArrayList<ArrayList<Estado>> identificarEstadosEquivalentes(
            ArrayList<ArrayList<Estado>> gruposDeEstadosFinaisENaoFinais) {

                ArrayList<ArrayList<Estado>> gruposEquivalentes = new ArrayList<>();
                ArrayList<ArrayList<Estado>> conjuntoCompleto = new ArrayList<>();
                ArrayList<ArrayList<Estado>> conjuntos = new ArrayList<>();
                Set<Estado> estadosUtilizados = new HashSet<>(); 
         
        boolean refinado;
        do {
            refinado = false;    
       
             for (ArrayList<Estado> grupos : gruposDeEstadosFinaisENaoFinais) {
                for (Estado estado : grupos) {

                    if (estadosUtilizados.contains(estado)) {
                        continue;
                    }

                    ArrayList<Estado> grupoEquivalentes = podeSerRefinado(estado, grupos, gruposDeEstadosFinaisENaoFinais);

                    if (!grupoEquivalentes.isEmpty()) {

                        ArrayList<Estado> estadosCopiados = new ArrayList<>();
                        for (Estado estados : grupoEquivalentes) {
                            estadosCopiados.add(new Estado(estados));
                        }
                        conjuntoCompleto.add(estadosCopiados);
                        gruposEquivalentes.add(grupoEquivalentes);
                        estadosUtilizados.addAll(grupoEquivalentes);
                        refinado = true;
                    
                        break;
                    }
                    
                }
                if (refinado) {
                    break;
                }
             }

              if (!gruposEquivalentes.isEmpty()) {
                 for (ArrayList<Estado> grupos : gruposDeEstadosFinaisENaoFinais) {
                     grupos.removeAll(estadosUtilizados);
                 }
              }
             gruposDeEstadosFinaisENaoFinais.addAll(gruposEquivalentes);
             
        } while (refinado);

        for (ArrayList<Estado> grupo : gruposDeEstadosFinaisENaoFinais) {
            if (!grupo.isEmpty()) {
               for (Estado estado : grupo) {
                 ArrayList<Estado> subgrupo = new ArrayList<>();
                 subgrupo.add(estado);
                 conjuntoCompleto.add(subgrupo);
               }
            }
        }
 
            return conjuntoCompleto;
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
    public ArrayList<Estado> podeSerRefinado(Estado estado, ArrayList<Estado> grupoAtual, ArrayList<ArrayList<Estado>> Conjunto){
        int destino, destinoComparar;
        ArrayList<Estado> equivalente = new ArrayList<Estado>();
        
        for (Estado estado2 : grupoAtual) {

            if (estado != estado2 && estado.getId() <= estado2.getId()) {
                boolean DestinosIguais = true;
                for (String simbolo : alfabeto) {
                     destino = DestinoPelaOrigem(simbolo, estado.getId());
                     destinoComparar = DestinoPelaOrigem(simbolo, estado2.getId());

                    if (!estaNoConjunto(destino, destinoComparar, Conjunto)) {
                        DestinosIguais = false;
                    }
                }
                if (DestinosIguais) {
                    if (!equivalente.contains(estado)) {
                        equivalente.add(estado);
                    }
                    equivalente.add(estado2);
                }
                
            }
          
        }

        return equivalente;
    }

    public boolean estaNoConjunto(int id, ArrayList<Integer> grupo){
        return grupo.contains(id);
    }

    public boolean estaNoConjunto(int destino2, int destino, ArrayList<ArrayList<Estado>> Conjunto){
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
            Estado estado = new Estado(id, "q"+id, Inicial(grupo), Final(grupo));
            estadosMinimizados.add(estado);
            
            id++;
        }

        for (int i = 0; i < Conjunto.size(); i++) {
           ArrayList<Estado> grupo = Conjunto.get(i);
            Estado estadoMinimmizado = estadosMinimizados.get(i);

                    for (int j = 0; j < Conjunto.size(); j++) {

                        for (String simbulo : alfabeto) {
                            int destino = DestinoPelaOrigem(simbulo, grupo.get(0).getId());
                            
                        if (EncontarDestino(Conjunto.get(j), destino) ) {
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

    public boolean Inicial(ArrayList<Estado>grupo){
        for (Estado estado : grupo) {
            if (estado.isInicial()) {
                return true;
            }
        }
        return false;
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
