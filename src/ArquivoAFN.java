import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ArquivoAFN {
    public static void LerAFN() {

        Automato automatoAFN = new Automato();
        try {
            File file = new File("automatoN.jff");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder construtorN = dbFactory.newDocumentBuilder();
            Document Afn = construtorN.parse(file);

            NodeList listaEstados = Afn.getElementsByTagName("state");
            
            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node noEstado = listaEstados.item(i);

                if (noEstado.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementEstado = (Element) noEstado;

                    int id = Integer.parseInt(elementEstado.getAttribute("id"));
                    String nome = elementEstado.getAttribute("name");
                    
                    boolean inicial = elementEstado.getAttribute("initial") != null;
                    boolean finall = elementEstado.getAttribute("final") != null; 

                    Estado estado = new Estado(id, nome, inicial, finall, i, id);

                    automatoAFN.addEstado(estado);
                }
            }

            NodeList listaTransicoes = Afn.getElementsByTagName("transition");
            for (int i = 0; i < listaTransicoes.getLength(); i++) {
                Node noTransicao = listaTransicoes.item(i);

                if (noTransicao.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoTransicao = (Element) noTransicao;

                    int estado_Inicial = Integer.parseInt(elementoTransicao.getElementsByTagName("form").item(0).getTextContent());
                    int estado_Final = Integer.parseInt(elementoTransicao.getElementsByTagName("to").item(0).getTextContent());
                    String simbulo = elementoTransicao.getElementsByTagName("read").item(0).getTextContent();

                    Transicao transicao = new Transicao(estado_Inicial, estado_Final, simbulo);
                    automatoAFN.addTransicoe(transicao);
                }
            }

            System.out.println("Deu certo");
        } catch (Exception e) {
            System.out.println("Deu algo de errado, porfavor tentar novamente !!!!");
        }
    }
}
