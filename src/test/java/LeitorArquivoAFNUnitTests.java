package test.java;
import main.java.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeitorArquivoAFNUnitTests {

    @Test
    void DadoUmAFNQuandoLerEntaoOAutomatoDeveTerOsEstadosPresentesNoArquivo() {
        LeitorArquivoAFN leitorArquivoAFN = new LeitorArquivoAFN();

        Automato automato = leitorArquivoAFN.LerAFN();
        
        assertEquals(3, automato.getEstados().size());
    }

    @Test
    void DadoUmAFNQuandoLerEntaoOAutomatoDeveTerAsTransicoesPresentesNoArquivo() {
        LeitorArquivoAFN leitorArquivoAFN = new LeitorArquivoAFN();

        Automato automato = leitorArquivoAFN.LerAFN();
        
        assertEquals(6, automato.getTransicoes().size());
    }
}