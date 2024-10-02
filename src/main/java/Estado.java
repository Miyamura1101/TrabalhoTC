package main.java;
public class Estado {
   
    private int id; /* Valor que é olhado durante as Trancisões */
    private String nome; /* Simbulo referente ao estado */
    private Boolean inicial; /* Determina se o estado é inicial ou não -- Depois checar a existencia de um estado iniciaç */
    private boolean finall; /*Determina se o estado é final ou não */
    private double x; // Coordenada no JFLAP
    private double y; // Coordenada no JFLAP
    
    public Estado(int id, String nome, Boolean inicial, boolean finall, double x, double y) {
        this.id = id;
        this.nome = nome;
        this.inicial = inicial;
        this.finall = finall;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isInicial() {
        return inicial;
    }

    public void setInicial(Boolean inicial) {
        this.inicial = inicial;
    }

    public boolean isFinall() {
        return finall;
    }

    public void setFinall(boolean finall) {
        this.finall = finall;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /*Os estados criados
     * EX:
     *  <state id="0" name="q0">;
			<x>59.0</x>;
			<y>115.0</y>;
			<initial/>; 
		</state>;
      */

    public  String toXml(){
        StringBuilder construtor = new StringBuilder();
        construtor.append("\t<state id=\"").append(id).append("\" name=\"").append(nome).append("\">\n");
        construtor.append("\t\t<x>").append(x).append("</x>\n");
        construtor.append("\t\t<y>").append(y).append("</y>\n"); 
        if (inicial) {
            construtor.append("\t\t<initial/>\n"); 
        }
        if (finall) {
            construtor.append("\t\t<final/>\n");
        }
        construtor.append("\t</state>\n");
        return construtor.toString();
    }    
}
