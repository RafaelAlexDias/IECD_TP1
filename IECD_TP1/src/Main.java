import server.Database;
import server.Othello;
import server.Player;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Othello othello = new Othello();
        Database database = new Database();
        int option;

        do {
            System.out.println(
                    "1 - Criar jogador" + "\n" +
                    "2 - Jogar server.Othello na mesma máquina" + "\n" +
                    "0 - Fechar Programa"
            );
            option = scanner.nextInt();

            switch (option) {
                case 0:
                    System.out.println("Até uma próxima! :)");
                    System.exit(0);

                case 1:
                    database.addPlayer(Player.newPlayer());
                    break;

                case 2:
                    othello.play();
                    break;
            }

        } while (option != 0);


    }
}
