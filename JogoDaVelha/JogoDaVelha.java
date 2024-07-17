import java.util.Scanner;

public class JogoDaVelha {
    private int[][] tabuleiro = new int[3][3];
    private int jogador = 1;
    private Jogador[] jogadores = new Jogador[2];
    private int[][] pontuacoesJogadores;
    private int partidaAtual = 0;

    // Classe para representar um jogador
    static class Jogador {
        String nome;
        int idade;
        int pontuacao;

        Jogador(String nome, int idade) {
            this.nome = nome;
            this.idade = idade;
            this.pontuacao = 0;
        }
    }

    // Método para realizar uma jogada no tabuleiro
    public boolean jogar(int x, int y) {
        if ((x < 0) || (x > 2) || (y < 0) || (y > 2)) {
            return false;
        }
        if (tabuleiro[x][y] != 0) {
            return false;
        }
        tabuleiro[x][y] = jogador;
        jogador = (jogador == 1) ? 2 : 1;
        return true;
    }

    // Método para verificar se há um vencedor
    public int vencedor() {
        for (int j = 1; j < 3; j++) {
            // Testa as linhas
            for (int linha = 0; linha < 3; linha++) {
                if (tabuleiro[0][linha] == j && tabuleiro[1][linha] == j && tabuleiro[2][linha] == j) {
                    return j;
                }
            }
            // Testa as colunas
            for (int coluna = 0; coluna < 3; coluna++) {
                if (tabuleiro[coluna][0] == j && tabuleiro[coluna][1] == j && tabuleiro[coluna][2] == j) {
                    return j;
                }
            }
            // Testar as diagonais
            if (tabuleiro[0][0] == j && tabuleiro[1][1] == j && tabuleiro[2][2] == j) {
                return j;
            }
            if (tabuleiro[0][2] == j && tabuleiro[1][1] == j && tabuleiro[2][0] == j) {
                return j;
            }
        }
        boolean empate = true;
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                if (tabuleiro[coluna][linha] == 0) {
                    empate = false;
                }
            }
        }
        if (empate) {
            return 3;
        }
        return 0;
    }

    // Método para representar o tabuleiro como uma string
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int linha = 0; linha < 3; linha++) {
            for (int coluna = 0; coluna < 3; coluna++) {
                switch (tabuleiro[coluna][linha]) {
                    case 0:
                        out.append("_ ");
                        break;
                    case 1:
                        out.append("O ");
                        break;
                    case 2:
                        out.append("X ");
                        break;
                }
            }
            out.append("\n");
        }
        return out.toString();
    }

    // Método para reiniciar o tabuleiro para um novo jogo
    public void reiniciarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = 0;
            }
        }
    }

    // Método para executar uma partida completa entre dois jogadores
    public void executarPartida() {
        Scanner entrada = new Scanner(System.in);

        while (vencedor() == 0) {
            System.out.println(this); // Exibe o estado atual do tabuleiro
            System.out.println("Jogador: " + ((jogador == 1) ? jogadores[0].nome : jogadores[1].nome));
            System.out.print("Coluna: ");
            int coluna = entrada.nextInt();
            System.out.print("Linha: ");
            int linha = entrada.nextInt();

            if (!jogar(coluna, linha)) {
                System.out.println("Jogada inválida, tente novamente...");
            }

            int resultadoPartida = vencedor();
            if (resultadoPartida != 0) {
                               System.out.println(this); // Exibe o estado final do tabuleiro após a partida

                // Atualiza pontuações e exibe resultado da partida
                if (resultadoPartida == 1) {
                    System.out.println(jogadores[0].nome + " venceu a partida!");
                    jogadores[0].pontuacao++;
                } else if (resultadoPartida == 2) {
                    System.out.println(jogadores[1].nome + " venceu a partida!");
                    jogadores[1].pontuacao++;
                } else {
                    System.out.println("A partida terminou em empate!");
                }

                // Atualiza resultados do campeonato
                atualizarResultadosCampeonato();

                // Reinicia o tabuleiro para a próxima partida
                reiniciarTabuleiro();
                jogador = 1; // Define jogador de volta para 1 para próxima partida
                partidaAtual++; // Avança para a próxima partida
                break; // Sai do loop quando a partida é concluída
            }
        }
    }

    // Método para cadastrar jogadores
    public void cadastrarJogadores() {
        Scanner scanner = new Scanner(System.in);

        // Captura informações do Jogador 1
        System.out.print("Nome do Jogador 1: ");
        String nomeJogador1 = scanner.nextLine();
        System.out.print("Idade do Jogador 1: ");
        int idadeJogador1 = scanner.nextInt();
        jogadores[0] = new Jogador(nomeJogador1, idadeJogador1);

        // Captura informações do Jogador 2
        System.out.print("Nome do Jogador 2: ");
        String nomeJogador2 = scanner.nextLine(); // Consumir quebra de linha pendente
        nomeJogador2 = scanner.nextLine();
        System.out.print("Idade do Jogador 2: ");
        int idadeJogador2 = scanner.nextInt();
        jogadores[1] = new Jogador(nomeJogador2, idadeJogador2);

        // Reinicia o tabuleiro e define o jogador para 1 ao cadastrar jogadores
        reiniciarTabuleiro();
        jogador = 1;
    }

    // Método para atualizar os resultados do campeonato após cada partida
    public void atualizarResultadosCampeonato() {
        if (pontuacoesJogadores == null) {
            pontuacoesJogadores = new int[3][2];
        }

        pontuacoesJogadores[partidaAtual][0] = jogadores[0].pontuacao;
        pontuacoesJogadores[partidaAtual][1] = jogadores[1].pontuacao;
    }

    // Método para exibir os resultados do campeonato
    public void exibirResultadosCampeonato() {
        if (pontuacoesJogadores == null) {
            System.out.println("Nenhum campeonato foi jogado ainda.");
            return;
        }

        System.out.println("\nResultados do Campeonato:");
        for (int i = 0; i <= partidaAtual; i++) {
            System.out.println("Partida " + (i + 1) + ": " +
                    jogadores[0].nome + "(" + jogadores[0].idade + " anos): " + pontuacoesJogadores[i][0] + " pontos, " +
                    jogadores[1].nome + "(" + jogadores[1].idade + " anos): " + pontuacoesJogadores[i][1] + " pontos");
        }
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        JogoDaVelha jogo = new JogoDaVelha();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar Jogadores");
            System.out.println("2. Iniciar Jogo da Velha");
            System.out.println("3. Sair do programa");
            System.out.println("4. Mostrar Resultados do Campeonato");
            System.out.print("Escolha a opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    jogo.cadastrarJogadores();
                    break;
                case 2:
                    jogo.executarPartida();
                    break;
                case 3:
                    System.out.println("Saindo do programa...");
                    System.exit(0);
                    break;
                case 4:
                    jogo.exibirResultadosCampeonato();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
