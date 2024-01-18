package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Torre extends PeçaXadrez{

    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

        //acima
        p.definirValores(posição.getLinha() - 1, posição.getColuna());
        while (p.getLinha() >= 0 && getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);

            if(p.getLinha() >= 0 && getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }

        //esquerda
        p.definirValores(posição.getLinha(), posição.getColuna() - 1);
        while (p.getLinha() >= 0 && getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);

            if(p.getLinha() >= 0 && getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }
        
        //direita
        p.definirValores(posição.getLinha(), posição.getColuna() + 1);
        while (p.getColuna() < getTabuleiro().getColunas() && getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);

            if(p.getColuna() < getTabuleiro().getColunas() && getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }

        //abaixo
        p.definirValores(posição.getLinha() + 1, posição.getColuna());
        while (p.getLinha() < getTabuleiro().getLinhas() && getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);

            if(p.getLinha() < getTabuleiro().getLinhas() && getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "R";
    }

    
}