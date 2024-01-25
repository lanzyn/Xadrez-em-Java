package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Cavalo extends PeçaXadrez {

    public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);

    }

    public boolean podeMover(PosiçãoTabuleiro posição) {
        PeçaXadrez p = (PeçaXadrez) getTabuleiro().peça(posição);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

        p.definirValores(posição.getLinha() - 1, posição.getColuna() + 2);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() - 1, posição.getColuna() - 2);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() + 1, posição.getColuna() - 2);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() + 1, posição.getColuna() + 2);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() - 2, posição.getColuna() - 1);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() - 2, posição.getColuna() + 1);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() + 2, posição.getColuna() - 1);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        p.definirValores(posição.getLinha() + 2, posição.getColuna() + 1);
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        return matriz;
    }

    @Override
    public String toString() {
        return "C";
    }

}