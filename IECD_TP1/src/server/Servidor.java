package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe ServidorTCPConcorrente tem por base um servidor TCP concorrente. Cada
 * tarefa disponibiliza acesso remoto a um jogo do galo mantido no servidor.
 *
 * @author Engº Porfírio Filipe
 */
public class Servidor {

    /**
     * Porta padrão onde o servidor irá aguardar conexões de clientes.
     */
    public final static int DEFAULT_PORT = 5025;

    /**
     * Método principal do programa ServidorTCPConcorrente.
     *
     * @param args argumentos da linha de comando: port
     */
    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        // Acede ao parametro se existir
        if(args.length==1)
            port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port);) {
            // Cria um socket servidor na porta especificada
            System.out.println("Servidor TCP concorrente iniciado...");
            // Loop infinito para aguardar ligações de clientes
            for (;;) {
                // Aguarda uma conexão de cliente
                System.out.println("Aguarda ligação no porto " + port + "...");
                Socket newSock = serverSocket.accept(); // cria circuito virtual
                System.out.println("Nova ligação recebida de " + newSock.getRemoteSocketAddress());

                // Cria uma nova thread para tratar a ligação que remete pedidos do cliente
                Thread th = new HandleConnectionThread1(newSock);
                th.start(); // Inicia a thread para executar a interação com o cliente
            }
        } catch (IOException e) {
            System.err.println("Exceção no servidor: " + e.getLocalizedMessage());
        }
    } // fim main
} // end ServidorTCPConcorrente

/**
 * Classe HandleConnectionThread representa uma thread responsável por tratar a
 * comunicação com um cliente ligado.
 *
 */
class HandleConnectionThread1 extends Thread {

    private Socket connection; // Socket da ligação com o cliente

    public HandleConnectionThread1(Socket connection) {
        this.connection = connection;
    }

    /**
     * Método executado pela thread para atender pedidos de um cliente.
     */
    public void run() {

        // Cria streams para leitura e escrita de dados no socket
        try (
                Scanner sc = new Scanner(connection.getInputStream());
                PrintStream os = new PrintStream(connection.getOutputStream(), true);) {
            // Circuito virtual estabelecido: socket cliente na variável newSock
            System.out.println("Thread " + this.getId() + " a processar " + connection.getRemoteSocketAddress());

            // Jogo para acesso remoto
            Othello oth= new Othello();

            // Loop para o jogo da galo
            for (;;) {
                //oth.jogar(oth.getJogador1());

                // Verifica se o jogo terminou
                if (oth.isGameOver()) {
                    //System.out.println(oth.printBoard());
                    break;
                }

                //oth.jogar(oth.getJogador2());

                // Verifica se o jogo terminou
                if (oth.isGameOver()) {
                    //System.out.println(oth.JogoParaTXT());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Terminou a ligação " + connection + ": " + e.getLocalizedMessage());
        } finally {
            // Garante o socket é fechado, mesmo em caso de exceção
            try {
                connection.close();
            } catch (IOException e) {
                // Ignora a exceção caso ocorra algum erro ao fechar
            }
        }
        System.out.println("Terminou a Thread (servidor dedicado) " + this.getId());
    } // fim run
} // end HandleConnectionThread