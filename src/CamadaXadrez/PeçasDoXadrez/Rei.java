package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
        
    }

    public boolean podeMover(PosiçãoTabuleiro posição){
        PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
        return p == null ||  p.getCor() != getCor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

        //acima
        p.definirValores(posição.getLinha() - 1, posição.getColuna());
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //abaixo
        p.definirValores(posição.getLinha() + 1, posição.getColuna());
        if (getTabuleiro().existePosição(p) && podeMover(p)) {
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //esquerda
        p.definirValores(posição.getLinha(), posição.getColuna() - 1); 
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //direita
        p.definirValores(posição.getLinha(), posição.getColuna() + 1);
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //noroeste
        p.definirValores(posição.getLinha() - 1, posição.getColuna() - 1);
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //nordeste
        p.definirValores(posição.getLinha() - 1, posição.getColuna() + 1);
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //sudoeste
        p.definirValores(posição.getLinha() + 1, posição.getColuna() - 1);
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        //sudeste
        p.definirValores(posição.getLinha() + 1, posição.getColuna() + 1);
        if(getTabuleiro().existePosição(p) && podeMover(p)){
            matriz[p.getLinha()][p.getColuna()] = true;
        }

        return matriz;
    }

    @Override
    public String toString() {
        return "K";
    }
    
    
}