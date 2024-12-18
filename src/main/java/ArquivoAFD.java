package main.java;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ArquivoAFD {
    private ArrayList<String> alfabeto = new ArrayList<String>();
    private ArrayList<Estado> estadosFinais = new ArrayList<Estado>();
    private Estado estadoInical;

    private SeletorDeArquivo seletorDeArquivo = new SeletorDeArquivo();

    public Automato LerAFD() {
        //File file = new File("automatoD.jff");
        File arquivo = seletorDeArquivo.selecionarArquivo();
        Automato automatoMinimizado = LerAFD(arquivo);
        return automatoMinimizado;
    }

    public Automato LerAFD(File arquivo) {
       
        Automato automatoAFN = new Automato();
        
        try {
            /*Serve para criar uma nova instancia de DocumentBuild, sendo ele responsavel pela 
             * construção e processamento de arquivos XML e a newInstance() serve para criar uma
             * nova instancia do documento com as configurações padrões*/
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); 
            /*Comverte o XML em uma estrutura de árvore em memória*/
            DocumentBuilder construtorD = dbFactory.newDocumentBuilder();
            Document Afd = construtorD.parse(arquivo);

            NodeList listaEstados = Afd.getElementsByTagName("state");
            
            for (int i = 0; i < listaEstados.getLength(); i++) {
                Node noEstado = listaEstados.item(i);

                if (noEstado.getNodeType() == Node.ELEMENT_NODE) {
                    /*Permite manipular diretamente elementos do XML*/
                    Element elementEstado = (Element) noEstado;

                    int id = Integer.parseInt(elementEstado.getAttribute("id"));
                    String nome = elementEstado.getAttribute("name");

                    /*getAttribute é usado para o valor de atributo dentro de uma tag (id = 1) */
                    
                    boolean inicial = elementEstado.getElementsByTagName("initial").getLength() > 0;
                    boolean finall = elementEstado.getElementsByTagName("final").getLength() > 0; 

                    Estado estado = new Estado(id, nome, inicial, finall);

                    if (finall) {
                        estadosFinais.add(estado);
                    }
                    if (inicial) {
                        estadoInical = estado;
                    }

                    automatoAFN.addEstado(estado);
                }
            }

            NodeList listaTransicoes = Afd.getElementsByTagName("transition");
            for (int i = 0; i < listaTransicoes.getLength(); i++) {
                Node noTransicao = listaTransicoes.item(i);

                if (noTransicao.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoTransicao = (Element) noTransicao;

                    /*getElementsByTagName é usado para pegar o conteudo dentron de elementos XML (<read>a</read>)*/

                    int estado_Inicial = Integer.parseInt(elementoTransicao.getElementsByTagName("from").item(0).getTextContent());
                    int estado_Final = Integer.parseInt(elementoTransicao.getElementsByTagName("to").item(0).getTextContent());
                    String simbulo = elementoTransicao.getElementsByTagName("read").item(0).getTextContent();

                    if (Procurar(simbulo)) {
                        alfabeto.add(simbulo);
                    }

                    try {
                        ChecagemDeSimbuloVazio(simbulo);
                    } catch (AutomatoAFNException e) {
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                    
                    Transicao transicao = new Transicao(estado_Inicial, estado_Final, simbulo);
                    automatoAFN.addTransicoe(transicao);
                }
            }

            System.out.println("Deu certo");
            return automatoAFN;
        } catch (Exception e) {
            System.out.println("Deu algo de errado, porfavor tentar novamente !!!!");
            return null;
        }
    }

    private void ChecagemDeSimbuloVazio(String simbolo) throws AutomatoAFNException {
        if (simbolo.isEmpty()) {
           throw new AutomatoAFNException("Existe transições com simbulos Vazios");
        }
    }

    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }
    
    public ArrayList<Estado> getEstadosFinais() {
        return estadosFinais;
    }

    public Estado getEstadoInical() {
        return estadoInical;
    }

    public Boolean Procurar(String simbulo){
        if (!alfabeto.contains(simbulo)) {
            return true;
        }
        return false;
    }

    public String abrirExploradorDeArquivos() {
        try {
            //     Aqui temos um filtro para aceitar apenas arquivos .jff
            FileNameExtensionFilter arqFiltro = new FileNameExtensionFilter("Somente arquivos .jff", "jff");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(arqFiltro);
            fileChooser.setDialogTitle("Selecione um arquivo JFLAP (.jff)");

            //      Caixa de diálogo para seleção de arquivo
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File arquivo = fileChooser.getSelectedFile();
                String caminho = "file:///" + arquivo.getAbsolutePath(); // Caminho
                return caminho;
            } else {
                System.out.println("Nenhum arquivo selecionado");
                return null; // Caso o usuário cancele a seleção
            }
        } catch (Exception v) {
            System.out.println("Erro: !!!! " + v);
            return null;
        }
    }
}
