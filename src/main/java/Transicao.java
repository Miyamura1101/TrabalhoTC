package main.java;

import java.util.Objects;
public class Transicao {

    private int estado_Inicial; // Referente ao estado de origem
    private int estado_Final; // Referente ao estado resultante
    private String simbulo; // Referente ao simbulo utilizado na Transicao
    
    public Transicao(int estado_Inicial, int estado_Final, String simbulo) {
        this.estado_Inicial = estado_Inicial;
        this.estado_Final = estado_Final;
        this.simbulo = simbulo;
    }

    public int getEstado_Inicial() {
        return estado_Inicial;
    }

    public void setEstado_Inicial(int estado_Inicial) {
        this.estado_Inicial = estado_Inicial;
    }

    public int getEstado_Final() {
        return estado_Final;
    }

    public void setEstado_Final(int estado_Final) {
        this.estado_Final = estado_Final;
    }

    public String getSimbulo() {
        return simbulo;
    }

    public void setSimbulo(String simbulo) {
        this.simbulo = simbulo;
    }

    /*Referente as transições realizado no automato
     * Ex:
     *  <transition>;
			<from>3</from>;
			<to>3</to>;
			<read>0</read>;
		</transition>; 
     */
    public String toXml(){
        StringBuilder construtor = new StringBuilder();
        construtor.append("\t<transition>\n");
        construtor.append("\t\t<from>").append(estado_Inicial).append("</from>\n");
        construtor.append("\t\t<to>").append(estado_Final).append("</to>\n"); 
        construtor.append("\t\t<read>").append(simbulo).append("</read>\n");
        construtor.append("\t</transition>\n");
        return construtor.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transicao that = (Transicao) o;
        return estado_Inicial == that.estado_Inicial &&
               estado_Final == that.estado_Final &&
               Objects.equals(simbulo, that.simbulo);
    }
    @Override
    public int hashCode(){
        return Objects.hash(estado_Inicial, estado_Final, simbulo);
    }
}
