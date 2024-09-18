public class Main {

    public static void main(String[] args) {

        Automato automato = new Automato();

        Estado q0 = new Estado(0, "q0", true, false, 0, 0);
        Estado q1 = new Estado(1, "q1", false, true, 1, 33);
        Transicao ts = new Transicao(0, 0, "b");
        Transicao a = new Transicao(0, 1, "a");

        automato.addEstado(q0);
        automato.addTransicoe(a);
        automato.addTransicoe(ts);
        automato.addEstado(q1);

        automato.salvarEmArquivo("automato.jff");

    }
}
