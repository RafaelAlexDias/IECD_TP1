package client;

import server.Database;
import server.LoginPlayer;
import server.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    private final static String DEFAULT_HOST = "localhost";
    private final static int DEFAULT_PORT = 5025;

    public static void main(String[] args) {
        Database database = new Database();

        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        if (args.length == 2) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }

        try (Socket socket = new Socket(host, port);
             BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Java-> Ligação estabelecida: " + socket);

            Thread threadLeitura = new Thread(() -> {
                try {
                    String line;
                    while ((line = is.readLine()) != null) {
                        if (line.equals("")) {
                            continue;
                        }
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.err.println("Ligação cancelada remotamente pelo servidor!" + e.getLocalizedMessage());
                }
            });
            threadLeitura.start();

            int option;
            do {
                System.out.println("Selecione uma opção:");
                System.out.println("1 - Criar usuário");
                System.out.println("2 - Fazer login");
                System.out.println("0 - Sair");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        database.addPlayer(Player.newPlayer());
                        break;
                    case 2:
                        Player player = LoginPlayer.loginPlayer();
                        option = 0;

                        break;
                }
            } while (option != 0);

            for (;;) {
                // Envia a linha escrita pelo jogador para o servidor.
                os.println(scanner.nextLine());
            }

        } catch (IOException e) {
            System.err.println("Erro na ligação: " + e.getLocalizedMessage());
        }
    }
}