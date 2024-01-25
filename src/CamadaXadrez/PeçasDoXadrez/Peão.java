package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PartidaXadrez;
import CamadaXadrez.PeçaXadrez;

public class Peão extends PeçaXadrez {

    private PartidaXadrez partida;

    public Peão(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
        super(tabuleiro, cor);
        this.partida = partida;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

        // Peão branco
        if (getCor() == Cor.BRANCO) {
            p.definirValores(posição.getLinha() - 1, posição.getColuna());
            if (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() - 2, posição.getColuna());
            PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha() - 1, posição.getColuna());
            if (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p) && getContadorMovimentos() == 0
                    && getTabuleiro().existePosição(p2) && !getTabuleiro().existePeça(p2)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() - 1, posição.getColuna() - 1);
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() - 1, posição.getColuna() + 1);
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            // En Passant
            if (posição.getLinha() == 3) {
                PosiçãoTabuleiro esquerda = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 1);
                if (getTabuleiro().existePosição(esquerda) && existePeçaOponente(esquerda)
                        && getTabuleiro().peça(esquerda) == partida.getEnPassantVulnerável()) {
                    matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }
                PosiçãoTabuleiro direita = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 1);
                if (getTabuleiro().existePosição(direita) && existePeçaOponente(direita)
                        && getTabuleiro().peça(direita) == partida.getEnPassantVulnerável()) {
                    matriz[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        } else {
            // Peão preto
            p.definirValores(posição.getLinha() + 1, posição.getColuna());
            if (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() + 2, posição.getColuna());
            PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha() + 1, posição.getColuna());
            if (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p) && getContadorMovimentos() == 0
                    && getTabuleiro().existePosição(p2) && !getTabuleiro().existePeça(p2)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() + 1, posição.getColuna() - 1);
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() + 1, posição.getColuna() + 1);
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            // En Passant
            if (posição.getLinha() == 4) {
                PosiçãoTabuleiro esquerda = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 1);
                if (getTabuleiro().existePosição(esquerda) && existePeçaOponente(esquerda)
                        && getTabuleiro().peça(esquerda) == partida.getEnPassantVulnerável()) {
                    matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                PosiçãoTabuleiro direita = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 1);
                if (getTabuleiro().existePosição(direita) && existePeçaOponente(direita)
                        && getTabuleiro().peça(direita) == partida.getEnPassantVulnerável()) {
                    matriz[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }

}
