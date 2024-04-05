import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class LoginPlayer {

    public void loginPlayer() {
        Scanner scanner = new Scanner(System.in);

        int credenciaisValidated = 0;

        System.out.println("Insira o seu username: ");
        String username = scanner.next();

        System.out.println("Insira a sua password: ");
        String password = scanner.next();

        while(credenciaisValidated != 1) {
            if (checkUser(username, password)) {
                credenciaisValidated = 1;
                System.out.println("Login bem-sucedido!");
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
                System.out.println("Insira o seu username: ");
                username = scanner.next();

                System.out.println("Insira a sua password: ");
                password = scanner.next();
            }
        }

    }


    public static boolean checkUser(String username, String password) {
        try {
            // Criar o objeto DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Criar o objeto DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Carregar o arquivo XML
            Document document = builder.parse(new File("IECD_TP1/jogadores.xml"));

            // Obter a raiz do documento
            Element root = document.getDocumentElement();

            // Obter a lista de elementos "User"
            NodeList userList = root.getElementsByTagName("User");

            // Percorrer os elementos "User"
            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;

                    // Obter o elemento "Username" e verificar se corresponde ao valor fornecido
                    String storedUsername = userElement.getElementsByTagName("Username").item(0).getTextContent();
                    if (storedUsername.equals(username)) {
                        // Obter o elemento "Password" e verificar se corresponde ao valor fornecido
                        String storedPassword = userElement.getElementsByTagName("Password").item(0).getTextContent();
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
