package test.java;
import main.java.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

public class LeitorArquivoAFNUnitTests {

    @Test
    @DisplayName("Dado Um AFN Quando Ler Entao O Automato Deve Ter Os Estados Presentes No Arquivo")
    void Dado_Um_AFN_Quando_Ler_Entao_O_Automato_Deve_Ter_Os_Estados_Presentes_No_Arquivo() {
        LerArquivoAFN lerArquivoAFN = new LerArquivoAFN();

        Automato automato = lerArquivoAFN.LerAFN();
        
        assertEquals(3, automato.getEstados().size());
    }

    @Test
    void DadoUmAFNQuandoLerEntaoOAutomatoDeveTerAsTransicoesPresentesNoArquivo() {
        LerArquivoAFN leitorArquivoAFN = new LerArquivoAFN();

        Automato automato = leitorArquivoAFN.LerAFN();
        
        assertEquals(6, automato.getTransicoes().size());
    }
}