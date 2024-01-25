package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Rainha extends PeçaXadrez {

    public Rainha(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    public boolean podeMover(PosiçãoTabuleiro posição) {
        PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
        return p == null || existePeçaOponente(posição);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

        // cima
        p.definirValores(posição.getLinha() - 1, posição.getColuna());
        while (p.getLinha() >= 0 && getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            if (getTabuleiro().existePeça(p)) {
                if (existePeçaOponente(p)) {
                    matriz[p.getLinha()][p.getColuna()] = true;
                }
                break;
            }
            p.setLinha(p.getLinha() - 1);
        }

        // baixo
        p.definirValores(posição.getLinha() + 1, posição.getColuna());
        while (p.getLinha() < getTabuleiro().getLinhas() && getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            if (getTabuleiro().existePeça(p)) {
                if (existePeçaOponente(p)) {
                    matriz[p.getLinha()][p.getColuna()] = true;
                }
                break;
            }
            p.setLinha(p.getLinha() + 1);
        }

        // esquerda
        p.definirValores(posição.getLinha(), posição.getColuna() - 1);
        while (p.getLinha() >= 0 && getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            if (getTabuleiro().existePeça(p)) {
                if (existePeçaOponente(p)) {
                    matriz[p.getLinha()][p.getColuna()] = true;
                }
                break;
            }
            p.setColuna(p.getColuna() - 1);
        }

        // direita
        p.definirValores(posição.getLinha(), posição.getColuna() + 1);
        while (p.getColuna() < getTabuleiro().getColunas() && getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            if (getTabuleiro().existePeça(p)) {
                if (existePeçaOponente(p)) {
                    matriz[p.getLinha()][p.getColuna()] = true;
                }
                break;
            }
            p.setColuna(p.getColuna() + 1);
        }

        // abaixo
        p.definirValores(posição.getLinha() + 1, posição.getColuna());
        while (p.getLinha() < getTabuleiro().getLinhas() && getTabuleiro().existePosição(p)
                && !getTabuleiro().existePeça(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);

            if (p.getLinha() < getTabuleiro().getLinhas() && getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }

            // Noroeste
            p.definirValores(posição.getLinha() - 1, posição.getColuna() - 1);
            while (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() - 1, p.getColuna() - 1);
            }
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }

            // Nordeste
            p.definirValores(posição.getLinha() - 1, posição.getColuna() + 1);
            while (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() - 1, p.getColuna() + 1);
            }
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }

            // Sudeste
            p.definirValores(posição.getLinha() + 1, posição.getColuna() + 1);
            while (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() + 1, p.getColuna() + 1);
            }
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }

            // Sudoeste
            p.definirValores(posição.getLinha() + 1, posição.getColuna() - 1);
            while (getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
                p.definirValores(p.getLinha() + 1, p.getColuna() - 1);
            }
            if (getTabuleiro().existePosição(p) && existePeçaOponente(p)) {
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "Q";
    }

}