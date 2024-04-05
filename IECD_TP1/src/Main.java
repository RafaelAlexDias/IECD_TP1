import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        int option = 100000;

        while (option != 0) {
            System.out.println("1 - Criar jogador");
            System.out.println("0 - Fechar Programa");
            option = scanner.nextInt();

            if (option == 1) {
                database.addPlayer(Player.newPlayer());
            } else if (option == 0) {
                System.exit(1);
            }
        }
        
        scanner.close();

        /*

        //Othello othello = new Othello();
        //othello.play();
         */
    }
}
