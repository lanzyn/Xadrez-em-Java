package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Peão extends PeçaXadrez {
        
    public Peão(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        PosiçãoTabuleiro p = new PosiçãoTabuleiro(0, 0);

        //Peão branco	
        if(getCor() == Cor.BRANCO){
            p.definirValores(posição.getLinha() - 1, posição.getColuna());
            if(getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() - 2, posição.getColuna());
            PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha() - 1, posição.getColuna());
            if(getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p) && getContadorMovimentos() == 0
            && getTabuleiro().existePosição(p2) && !getTabuleiro().existePeça(p2)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() - 1, posição.getColuna() - 1);
            if(getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() - 1, posição.getColuna() + 1);
            if(getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }else{
            //Peão preto
            p.definirValores(posição.getLinha() + 1, posição.getColuna());
            if(getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() + 2, posição.getColuna());
            PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha() + 1, posição.getColuna());
            if(getTabuleiro().existePosição(p) && !getTabuleiro().existePeça(p) && getContadorMovimentos() == 0
            && getTabuleiro().existePosição(p2) && !getTabuleiro().existePeça(p2)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() + 1, posição.getColuna() - 1);
            if(getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
            p.definirValores(posição.getLinha() + 1, posição.getColuna() + 1);
            if(getTabuleiro().existePosição(p) && existePeçaOponente(p)){
                matriz[p.getLinha()][p.getColuna()] = true;
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }
    
}
