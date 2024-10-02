import main.java.Transicao;

public class ListaMinimizacao {

    private int id_1;
    private int id_2;
    private Transicao transicao_1;
    private Transicao transicao_2;
    private Transicao transicao_revisao;
    private boolean finall_1;
    private boolean finall_2;
    private boolean equivalente;
    public int getId_1() {
        return id_1;
    }
    public void setId_1(int id_1) {
        this.id_1 = id_1;
    }
    public int getId_2() {
        return id_2;
    }
    public void setId_2(int id_2) {
        this.id_2 = id_2;
    }
    public Transicao getTransicao_1() {
        return transicao_1;
    }
    public void setTransicao_1(Transicao transicao_1) {
        this.transicao_1 = transicao_1;
    }
    public Transicao getTransicao_2() {
        return transicao_2;
    }
    public void setTransicao_2(Transicao transicao_2) {
        this.transicao_2 = transicao_2;
    }
    public Transicao getTransicao_revisao() {
        return transicao_revisao;
    }
    public void setTransicao_revisao(Transicao transicao_revisao) {
        this.transicao_revisao = transicao_revisao;
    }
    public boolean isFinall_1() {
        return finall_1;
    }
    public void setFinall_1(boolean finall_1) {
        this.finall_1 = finall_1;
    }
    public boolean isFinall_2() {
        return finall_2;
    }
    public void setFinall_2(boolean finall_2) {
        this.finall_2 = finall_2;
    }
    public boolean isEquivalente() {
        return equivalente;
    }
    public void setEquivalente(boolean equivalente) {
        this.equivalente = equivalente;
    }
    public ListaMinimizacao(int id_1, int id_2, Transicao transicao_1, Transicao transicao_2, boolean finall_1,
            boolean finall_2) {
        this.id_1 = id_1;
        this.id_2 = id_2;
        this.transicao_1 = transicao_1;
        this.transicao_2 = transicao_2;
        this.finall_1 = finall_1;
        this.finall_2 = finall_2;
    }

    
}
