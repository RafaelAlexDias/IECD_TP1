package server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import server.Player;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class LoginPlayer {

    public static Player loginPlayer() {
        int credenciaisValidated = 0;
        Player player;

        player = Player.playerForLogin();

        while(credenciaisValidated != 1) {
            if (checkUser(player.getName(), player.getPassword())) {
                credenciaisValidated = 1;
                System.out.println("Login bem-sucedido!");
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
                player = Player.playerForLogin();
            }
        }
        return player;
    }


    public static boolean checkUser(String username, String password) {
        try {
            // Criar o objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Criar o objeto DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Carregar o arquivo XML
            Document document = builder.parse(new File(Database.xmlFilePath));

            // Obter a raiz do documento
            Element root = document.getDocumentElement();

            // Obter a lista de elementos "User"
            NodeList userList = root.getElementsByTagName("jogador");

            // Percorrer os elementos "User"
            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;

                    // Obter o elemento "Username" e verificar se corresponde ao valor fornecido
                    String storedUsername = userElement.getElementsByTagName("nome").item(0).getTextContent();
                    if (storedUsername.equals(username)) {
                        // Obter o elemento "Password" e verificar se corresponde ao valor fornecido
                        String storedPassword = userElement.getElementsByTagName("password").item(0).getTextContent();
                        if (storedPassword.equals(password)) {
                            return true; // Usuário encontrado
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Usuário não encontrado
    }

}
