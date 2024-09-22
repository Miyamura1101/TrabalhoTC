package main.java;

import java.util.*;

public class ConversorAFNparaAFD {
    private Automato afn;
    private List<Transicao> transicoesAFD;
    private Map<List<Integer>, Integer> estadoMap; // Mapeia os estados para seus IDs no AFD

    public ConversorAFNparaAFD() {
        LerArquivoAFN lerArquivoAFN = new LerArquivoAFN();
        this.afn = lerArquivoAFN.LerAFN(); // Lê o automato AFN diretamente
        this.transicoesAFD = new ArrayList<>();
        this.estadoMap = new HashMap<>();
        if (afn != null) {
            converterAFNparaAFD();
        }
    }

    private void converterAFNparaAFD() {
        Queue<List<Integer>> fila = new LinkedList<>();

        // Inicializa com o estado inicial do AFN
        List<Integer> estadoInicial = new ArrayList<>();
        for (Estado estado : afn.getEstados()) {
            if (estado.getInicial()) {
                estadoInicial.add(estado.getId());
            }
        }
        fila.add(estadoInicial);
        estadoMap.put(estadoInicial, estadoInicial.hashCode()); // Usa hashCode como ID temporário

        while (!fila.isEmpty()) {
            List<Integer> estadoAtual = fila.poll();

            // Obter destinos para cada símbolo
            for (String simbolo : getSimbolosTransicoes(estadoAtual)) {
                List<Integer> destinos = obterDestinos(estadoAtual, simbolo);

                if (!destinos.isEmpty()) {
                    // Se os destinos ainda não foram mapeados, adicione à fila
                    if (!estadoMap.containsKey(destinos)) {
                        estadoMap.put(destinos, destinos.hashCode());
                        fila.add(destinos);
                    }

                    // Criar a transição AFD usando os IDs reais
                    Integer idOrigem = estadoMap.get(estadoAtual);
                    Integer idDestino = estadoMap.get(destinos);
                    transicoesAFD.add(new Transicao(idOrigem, idDestino, simbolo));
                }
            }
        }

        // Criação de estados AFD
        for (Map.Entry<List<Integer>, Integer> entry : estadoMap.entrySet()) {
            List<Integer> estados = entry.getKey();
            boolean inicial = estados.stream().anyMatch(id -> afn.getEstados().stream().anyMatch(e -> e.getId() == id && e.getInicial()));
            boolean finall = estados.stream().anyMatch(id -> afn.getEstados().stream().anyMatch(e -> e.getId() == id && e.isFinall()));
            Estado novoEstado = new Estado(entry.getValue(), estados.toString(), inicial, finall, 0, 0); // A posição x,y é simplificada
            // Adiciona o novo estado AFD
        }
    }

    private Set<String> getSimbolosTransicoes(List<Integer> estados) {
        Set<String> simbolos = new HashSet<>();
        for (Integer estado : estados) {
            for (Transicao transicao : afn.getTransicoes()) {
                if (transicao.getEstado_Inicial() == estado) {
                    simbolos.add(transicao.getSimbulo());
                }
            }
        }
        return simbolos;
    }

    private List<Integer> obterDestinos(List<Integer> estados, String simbolo) {
        List<Integer> destinos = new ArrayList<>();
        for (Integer estado : estados) {
            for (Transicao transicao : afn.getTransicoes()) {
                if (transicao.getEstado_Inicial() == estado && transicao.getSimbulo().equals(simbolo)) {
                    if (!destinos.contains(transicao.getEstado_Final())) {
                        destinos.add(transicao.getEstado_Final());
                    }
                }
            }
        }
        return destinos;
    }

    public Automato getAFD() {
        Automato afd = new Automato();
        for (Map.Entry<List<Integer>, Integer> entry : estadoMap.entrySet()) {
            List<Integer> estados = entry.getKey();
            boolean inicial = estados.stream().anyMatch(id -> afn.getEstados().stream().anyMatch(e -> e.getId() == id && e.getInicial()));
            boolean finall = estados.stream().anyMatch(id -> afn.getEstados().stream().anyMatch(e -> e.getId() == id && e.isFinall()));
            Estado novoEstado = new Estado(entry.getValue(), estados.toString(), inicial, finall, 0, 0);
            afd.addEstado(novoEstado);
        }
        for (Transicao transicao : transicoesAFD) {
            afd.addTransicoe(transicao);
        }
        return afd;
    }
}