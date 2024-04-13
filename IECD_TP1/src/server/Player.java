package server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Scanner;

public class Player {

    private String name;
    private int age;
    private String password;
    private String nationality;
    private int winCount;

    // Para Login de um jogador
    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        this.winCount = 0;
    }

    // Para registo de um novo jogador
    public Player(String name, String password, String nationality, int age) {
        this.name = name;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.winCount = 0;
    }

    public static Player newPlayer() {
        Scanner scanner = new Scanner(System.in);
        Player player;

        System.out.print("Nome: ");
        String player_name = scanner.next();
        System.out.print("Password: ");
        String player_password = scanner.next();
        System.out.print("Nacionalidade: ");
        String player_nationality = scanner.next();
        System.out.print("Idade: ");
        int player_age = scanner.nextInt();
        player = new Player(player_name, player_password, player_nationality, player_age);

        return player;
    }

    public static Player playerForLogin() {
        Scanner scanner = new Scanner(System.in);
        Player player;

        System.out.print("Nome: ");
        String player_name = scanner.next();
        System.out.print("Password: ");
        String player_password = scanner.next();
        player = new Player(player_name, player_password);

        return player;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNationality() {
        return this.nationality;
    }

    public int getAge() {
        return this.age;
    }

    public int getWinCount(Player player) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(Database.xmlFilePath);

            Element rootElement = doc.getDocumentElement();

            NodeList nodeList = doc.getElementsByTagName("jogador");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element userElement = (Element) nodeList.item(i);
                if (player.getName().equals(userElement.getElementsByTagName("nome").item(0).getTextContent())) {
                    if (player.getPassword().equals(userElement.getElementsByTagName("password").item(0).getTextContent())) {
                        this.winCount = userElement.getElementsByTagName("wins").item(0).getNodeType();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(this.winCount);
        return this.winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

}
