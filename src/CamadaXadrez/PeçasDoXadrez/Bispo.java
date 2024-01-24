package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Bispo extends PeçaXadrez {

    public Bispo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

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

        return matriz;
    }

    @Override
    public String toString() {
        return "B";
    }

    
}

