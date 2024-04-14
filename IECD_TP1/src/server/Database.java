package server;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import server.Player;

public class Database {

    public static final String xmlFilePath = "IECD_TP1\\jogadores.xml";


    public void addPlayer(Player player) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFilePath);

            // Encontrar o nó raiz
            Node rootNode = doc.getFirstChild();

            // Cria novo jogador
            Element jogador = doc.createElement("jogador");

            // Define nome do jogador
            Element nome = doc.createElement("nome");
            nome.appendChild(doc.createTextNode(player.getName()));
            jogador.appendChild(nome);

            // Define password do jogador
            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode(player.getPassword()));
            jogador.appendChild(password);

            // Define nacionalidade do jogador
            Element nacionalidade = doc.createElement("nacionalidade");
            nacionalidade.appendChild(doc.createTextNode(player.getNationality()));
            jogador.appendChild(nacionalidade);

            // Define idade do jogador
            Element idade = doc.createElement("idade");
            idade.appendChild(doc.createTextNode(Integer.toString(player.getAge())));
            jogador.appendChild(idade);

            // Define número de vitórias do jogador
            Element winCount = doc.createElement("wins");
            winCount.appendChild(doc.createTextNode(Integer.toString(player.getWinCount(player))));
            jogador.appendChild(winCount);

            rootNode.appendChild(jogador);

            // Escrever o conteúdo atualizado no arquivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            System.out.println("Jogador adicionado com sucesso!" + "\n");

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }



    public static void updateWins(Player player, int wins) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFilePath);

            NodeList nodeList = doc.getElementsByTagName("jogador");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element userElement = (Element) nodeList.item(i);
                if (player.getName().equals(userElement.getElementsByTagName("nome").item(0).getTextContent())) {
                    if (player.getPassword().equals(userElement.getElementsByTagName("password").item(0).getTextContent())) {
                        userElement.getElementsByTagName("wins").item(0).setTextContent(String.valueOf(wins));
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFilePath);
            transformer.transform(source, result);

            System.out.println(player.getName() + " tem: " + wins + " vitórias!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
