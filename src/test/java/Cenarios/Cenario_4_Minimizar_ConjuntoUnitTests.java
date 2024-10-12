package test.java.Cenarios;

import org.junit.jupiter.api.Test;

import main.java.ArquivoAFD;
import main.java.Automato;
import main.java.Estado;
import main.java.Minimizacao_Conjunto;
import main.java.Transicao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class Cenario_4_Minimizar_ConjuntoUnitTests {

       ArquivoAFD afd = new ArquivoAFD();
       Automato afds = afd.LerAFD();
       Minimizacao_Conjunto minimizacao_Conjunto = new Minimizacao_Conjunto(afds.getEstados(), afd.getAlfabeto(), afds.getTransicoes(), afd.getEstadoInical(), afd.getEstadosFinais());
      
    
    @Test
    void Dado_Um_AFN_Deve_Ser_Separado_EM_Grupo_Mostando_Esse_Grupos(){

        Automato automatoMinimizado = minimizacao_Conjunto.minimizar();
        ArrayList<Estado> estadosMinimizados = minimizacao_Conjunto.getEstadosMinimizados();
        ArrayList<Estado> estadosEsperados = new ArrayList<Estado>();

        estadosEsperados.add(new Estado(0, "q0", false, false));
        estadosEsperados.add(new Estado(1, "q1", false, true));
        estadosEsperados.add(new Estado(2, "q2", false, false));
        estadosEsperados.add(new Estado(3, "q3", true, true));

        /*for (Estado estado : estadosEsperados) {
            assertTrue(estadosMinimizados.contains(estado));
            System.out.println(estado);   
        }*/

        int quantEstadosEsperados = 4;

        assertEquals(quantEstadosEsperados, automatoMinimizado.getEstados().size(), "O número de estados minimizados está incorreto");
    }

   @Test
    void Dado_Um_AFN_Deve_Mostar_As_Transações_Criadas(){
        Automato automatoMinimizado = minimizacao_Conjunto.minimizar();
        ArrayList<Transicao> transicaos = automatoMinimizado.getTransicoes();
        ArrayList<Transicao> transicaosEsperadas = new ArrayList<Transicao>();

       transicaosEsperadas.add(new Transicao(0, 1, "b"));
       transicaosEsperadas.add(new Transicao(0, 1, "a"));
       transicaosEsperadas.add( new Transicao(1, 0, "b"));
       transicaosEsperadas.add(new Transicao(1, 0, "a"));
       transicaosEsperadas.add(new Transicao(2, 2, "a"));
       transicaosEsperadas.add( new Transicao(2, 3, "b"));
       transicaosEsperadas.add( new Transicao(3, 0, "a"));
       transicaosEsperadas.add( new Transicao(3, 2, "b"));

        assertEquals(transicaosEsperadas.size(), automatoMinimizado.getTransicoes().size());

        for (Transicao transicao : transicaosEsperadas) {
            assertTrue(transicaos.contains(transicao));
        }
       
    }

    @Test
    void  Dado_Um_AFN_Deve_Mostar_O_Seu_Alfabeto(){
        ArrayList<String> alfabeto = afd.getAlfabeto();

        String[] alfabetoEsperado = { "a", "b"};

        assertEquals(alfabetoEsperado.length, alfabeto.size());
    }

}
