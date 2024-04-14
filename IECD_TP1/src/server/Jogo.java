package server;

/**
 * Classe que implementa as regras do jogo Othello (Reversi).
 *
 * @author Engº Porfírio Filipe
 */
public class Jogo {

    /**
     * Tabuleiro do jogo Othello (8x8).
     */
    protected char[][] tabuleiro = new char[8][8];

    /**
     * Jogador 1 com o símbolo 'X'.
     */
    public static final char JOGADOR1 = 'X';

    /**
     * Jogador 2 com o símbolo 'O'.
     */
    public static final char JOGADOR2 = 'O';

    /**
     * Construtor sem argumentos que inicializa o tabuleiro com a configuração inicial de Othello.
     */
    public Jogo() {
        // Inicializa o tabuleiro com espaços em branco
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabuleiro[i][j] = ' ';
            }
        }

        // Configuração inicial de Othello
        tabuleiro[3][3] = 'O';
        tabuleiro[3][4] = 'X';
        tabuleiro[4][3] = 'X';
        tabuleiro[4][4] = 'O';
    }

    /**
     * Método para verificar se uma jogada é válida para um jogador específico.
     *
     * @param linha   Linha da jogada.
     * @param coluna  Coluna da jogada.
     * @param jogador Símbolo do jogador.
     * @return true se a jogada é válida, false caso contrário.
     */
    public boolean jogadaValida(int linha, int coluna, char jogador) {
        // Verifica se a posição está dentro do tabuleiro
        if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8 || tabuleiro[linha][coluna] != ' ') {
            return false;
        }

        char adversario = (jogador == JOGADOR1) ? JOGADOR2 : JOGADOR1;

        boolean jogadaValida = false;

        // Verifica em todas as direções se a jogada é válida
        for (int dLinha = -1; dLinha <= 1; dLinha++) {
            for (int dColuna = -1; dColuna <= 1; dColuna++) {
                if (dLinha == 0 && dColuna == 0) continue; // Ignora a direção atual

                int l = linha + dLinha;
                int c = coluna + dColuna;

                boolean encontrouAdversario = false;

                while (l >= 0 && l < 8 && c >= 0 && c < 8 && tabuleiro[l][c] == adversario) {
                    l += dLinha;
                    c += dColuna;
                    encontrouAdversario = true;
                }

                if (l >= 0 && l < 8 && c >= 0 && c < 8 && tabuleiro[l][c] == jogador && encontrouAdversario) {
                    jogadaValida = true;
                    break;
                }
            }
            if (jogadaValida) break;
        }

        return jogadaValida;
    }

    /**
     * Método para realizar uma jogada no tabuleiro.
     *
     * @param linha   Linha da jogada.
     * @param coluna  Coluna da jogada.
     * @param jogador Símbolo do jogador.
     * @return true se a jogada é válida e realizada com sucesso, false caso contrário.
     */
    public boolean fazJogada(int linha, int coluna, char jogador) {
        if (jogadaValida(linha - 1, coluna - 1, jogador)) {
            tabuleiro[linha - 1][coluna - 1] = jogador;
            char adversario = (jogador == JOGADOR1) ? JOGADOR2 : JOGADOR1;

            // Inverte as peças do oponente nas direções apropriadas
            for (int dLinha = -1; dLinha <= 1; dLinha++) {
                for (int dColuna = -1; dColuna <= 1; dColuna++) {
                    if (dLinha == 0 && dColuna == 0) continue; // Ignora a direção atual

                    int l = linha - 1 + dLinha;
                    int c = coluna - 1 + dColuna;

                    boolean encontrouAdversario = false;
                    boolean encontrouJogador = false;

                    while (l >= 0 && l < 8 && c >= 0 && c < 8) {
                        if (tabuleiro[l][c] == adversario) {
                            encontrouAdversario = true;
                        } else if (tabuleiro[l][c] == jogador) {
                            encontrouJogador = true;
                            break;
                        } else if (tabuleiro[l][c] == ' ') {
                            break;
                        }
                        l += dLinha;
                        c += dColuna;
                    }

                    if (encontrouAdversario && encontrouJogador) {
                        // Inverte as peças do oponente
                        l = linha - 1 + dLinha;
                        c = coluna - 1 + dColuna;
                        while (tabuleiro[l][c] == adversario) {
                            tabuleiro[l][c] = jogador;
                            l += dLinha;
                            c += dColuna;
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Método para verificar se o jogo acabou (não há mais jogadas possíveis).
     *
     * @return true se o jogo acabou, false caso contrário.
     */
    public boolean jogoAcabou() {
        // Verifica se há jogadas válidas para algum jogador
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                if (jogadaValida(linha, coluna, JOGADOR1) || jogadaValida(linha, coluna, JOGADOR2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método para determinar o vencedor do jogo.
     *
     * @return 'X' se o jogador 1 venceu, 'O' se o jogador 2 venceu, ' ' se houve empate.
     */
    public char determinaVencedor() {
        int countJogador1 = 0;
        int countJogador2 = 0;

        // Conta o número de peças de cada jogador no tabuleiro
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                if (tabuleiro[linha][coluna] == JOGADOR1) {
                    countJogador1++;
                } else if (tabuleiro[linha][coluna] == JOGADOR2) {
                    countJogador2++;
                }
            }
        }

        // Determina o vencedor com base no número de peças
        if (countJogador1 > countJogador2) {
            return JOGADOR1;
        } else if (countJogador2 > countJogador1) {
            return JOGADOR2;
        } else {
            return ' '; // Empate
        }
    }

    /**
     * Método para obter o tabuleiro atual do jogo.
     *
     * @return O tabuleiro do jogo como uma matriz de caracteres.
     */
    public char[][] getTabuleiro() {
        return tabuleiro;
    }
}
