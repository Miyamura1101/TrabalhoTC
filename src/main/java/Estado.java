package main.java;

public class Estado {

    private int id; // Valor que é olhado durante as Transições
    private String nome; // Símbolo referente ao estado
    private boolean inicial; // Determina se o estado é inicial ou não
    private boolean finall; // Determina se o estado é final ou não
    private double x; // Coordenada no JFLAP
    private double y; // Coordenada no JFLAP

    public Estado(int id, String nome, boolean inicial, boolean finall) {
        this.id = id;
        this.nome = nome;
        this.inicial = inicial;
        this.finall = finall;
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

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
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
        this.x = 300;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = 300;
    }

    // Método para exportar estado como XML
    @Override
    public String toString() {
        return "Estado{id=" + id + ", nome='" + nome + 
                        "', inicial=" + inicial + ", finall=" + finall + '}';
    }

    public String toXml() {
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
