package server;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Classe que implementa a interface para jogar Othello (Reversi) na consola.
 * Permite redirecionamento do input e output.
 * Baseada na classe Jogador original para Jogo da Galo.
 *
 * @author Porfírio Filipe
 */
public class Jogador extends Jogo {

    /**
     * Realiza a jogada do jogador com o símbolo especificado.
     *
     * @param simbolo Símbolo do jogador.
     * @param leitor  Acesso ao stream de entrada.
     * @param saida   Acesso ao stream de saída.
     */
    public void joga(final char simbolo, final Scanner leitor, final PrintStream saida) {
        // Exibe o tabuleiro atual.
        saida.println(jogoToTXT());

        // Pede ao jogador para inserir a sua jogada.
        saida.println("Jogador " + simbolo + ", insira a sua jogada no formato 'linha coluna' (ex: 2 4): ");

        // Lê a jogada do jogador.
        while (true) {
            if (leitor.hasNextInt()) {
                int linha = leitor.nextInt();
                int coluna = leitor.nextInt();

                // Concretiza a jogada.
                if (!fazJogada(linha, coluna, simbolo))
                    // Jogada inválida.
                    saida.println("Jogada inválida! Tente novamente.\n");
                else
                    // Jogada válida.
                    return;

            } else
                // Ignora a linha
                leitor.nextLine();
        }
    }

    /**
     * Verifica se o jogo terminou.
     *
     * @param saida Stream de output.
     * @return True se o jogo terminou, false caso contrário.
     */
    public boolean terminou(final PrintStream saida) {
        if (jogoAcabou()) {
            // Exibe o tabuleiro final.
            saida.println(jogoToTXT());

            // Determina o vencedor.
            char vencedor = determinaVencedor();

            // Exibe a mensagem de resultado.
            if (vencedor == JOGADOR1 || vencedor == JOGADOR2)
                saida.println("O jogador " + vencedor + " venceu!");
            else
                saida.println("Empate!");

            return true;
        }
        return false;
    }

    /**
     * Converte o tabuleiro do jogo numa string formatada para ser apresentada na consola.
     *
     * @return String com o tabuleiro formatado.
     */
    public String jogoToTXT() {
        StringBuilder out = new StringBuilder();
        out.append("  1 2 3 4 5 6 7 8\n");
        for (int i = 0; i < 8; i++) {
            out.append(i + 1).append(" ");
            for (int j = 0; j < 8; j++) {
                out.append(tabuleiro[i][j]).append(" ");
            }
            out.append("\n");
        }
        return out.toString();
    }

    /**
     * Método principal do jogo. Inicia o jogo e controla o loop principal.
     *
     * @param args Argumentos não utilizados.
     */
    public static void main(String[] args) {
        Jogador jogo = new Jogador(); // Cria uma nova instância do jogo.

        // Scanner para entrada de dados.
        Scanner scanner = new Scanner(System.in);

        // Loop do jogo.
        while (true) {
            // Jogada do jogador X.
            jogo.joga(JOGADOR1, scanner, System.out);

            // Verifica se o jogo terminou.
            if (jogo.terminou(System.out)) {
                break;
            }

            // Jogada do jogador O.
            jogo.joga(JOGADOR2, scanner, System.out);

            // Verifica se o jogo terminou.
            if (jogo.terminou(System.out)) {
                break;
            }
        }

        // Fecha o scanner.
        scanner.close();
    }
}
