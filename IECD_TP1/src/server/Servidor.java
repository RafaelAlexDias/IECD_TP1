package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe ServidorTCPConcorrente tem por base um servidor TCP concorrente.
 * Cada tarefa disponibiliza acesso remoto a um jogo do galo mantido no servidor.
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
        // Cria um socket servidor na porta especificada
        try (ServerSocket serverSocket = new ServerSocket(port);) {

            System.out.println("Servidor TCP concorrente iniciado...");
            // Loop infinito para aguardar ligações de clientes
            for (;;) {
                System.out.println("Aguarda ligação no porto " + port + "...");
                // Espera ligação do primeiro jogador
                System.out.println("Espera pelo Jogador X");
                Socket newSock1 = serverSocket.accept();
                System.out.println("Aceitou ligação do Jogador X");
                // Espera ligação do segundo jogador
                System.out.println("Espera pelo Jogador O");
                Socket newSock2 = serverSocket.accept();
                System.out.println("Aceitou ligação do Jogador O");


                // Cria uma nova thread gerir o jogo entre os dois jogadores
                Thread th = new HandleConnectionThread(newSock1, newSock2);
                th.start(); // Inicia a thread para executar a interação com os jogadores
            }

        } catch (IOException e) {
            System.err.println("Exceção no servidor: " + e.getLocalizedMessage());
        }
    } // fim main
} // end ServidorTCPConcorrente

/**
 * Classe HandleConnectionThread representa uma thread responsável por tratar a
 * comunicação com dois clientes ligados que interagem durante um jogo.
 *
 */
class HandleConnectionThread extends Thread {

    private Socket connection1 = null; // Socket da ligação com o jogador X
    private Socket connection2 = null; // Socket da ligação com o jogador O

    public HandleConnectionThread(Socket connection1, Socket connection2) {
        this.connection1 = connection1;
        this.connection2 = connection2;
    }

    /**
     * Método executado pela thread para gerir um jogo.
     */
    public void run() {

        try (// Cria streams para leitura e escrita de dados no socket
             Scanner scX = new Scanner(connection1.getInputStream());
             Scanner scO = new Scanner(connection2.getInputStream());
             PrintStream osX = new PrintStream(connection1.getOutputStream(), true);
             PrintStream osO = new PrintStream(connection2.getOutputStream(), true);) {
            System.out.println("Thread " + this.getId() + ":");
            System.out.println("	Jogador X: " + connection1);
            System.out.println("	Jogador O: " + connection2);

            // Gere esta instância do jogo
            Jogador jogo = new Jogador();

            // Ciclo para gerir a interação com os jogadores, primeiro a jogar X
            for (;;) {
                jogo.joga('X',scX, osX);
                if (jogo.terminou(osX)) {
                    jogo.terminou(osO);
                    break;
                }
                jogo.joga('O', scO, osO);
                if (jogo.terminou(osO)) {
                    jogo.terminou(osX);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Terminou a ligação!");
        } finally {
            // Garante os socket são fechados, mesmo em caso de exceção
            try {
                connection1.close();
                connection2.close();
            } catch (IOException e) {
                // Ignora a exceção caso ocorra algum erro ao fechar
            }
        }
        System.out.println("Terminou a Thread (servidor dedicado) " + this.getId());
    } // fim run
} // end HandleConnectionThread