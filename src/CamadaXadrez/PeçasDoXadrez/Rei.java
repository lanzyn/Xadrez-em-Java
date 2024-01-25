package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PartidaXadrez;
import CamadaXadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {

    private PartidaXadrez partida;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partida) {
        super(tabuleiro, cor);
        this.partida = partida;
        
    }

    public boolean podeMover(PosiçãoTabuleiro posição){
        PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
        return p == null ||  p.getCor() != getCor();
    }

    private boolean testeRoque(PosiçãoTabuleiro posição){
        if (getTabuleiro().existePosição(posição)) {
            PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
            return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
        }
        return false;
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

        // #movimento especial roque
        if(getContadorMovimentos() == 0 && !partida.getXeque()){
            PosiçãoTabuleiro pt1 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 3);
            if(testeRoque(pt1) && getCor() == Cor.BRANCO){
                PosiçãoTabuleiro p1 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 1);
                PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 2);
                if(getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null){
                    matriz[posição.getLinha()][posição.getColuna() + 2] = true;
                }
            }
            PosiçãoTabuleiro pt2 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 4);
            if(testeRoque(pt2) && getCor() == Cor.BRANCO){
                PosiçãoTabuleiro p1 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 1);
                PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 2);
                PosiçãoTabuleiro p3 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 3);
                if(getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null && getTabuleiro().peça(p3) == null){
                    matriz[posição.getLinha()][posição.getColuna() - 2] = true;
                }
            }
            
            PosiçãoTabuleiro pt3 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 3);
            if(testeRoque(pt3) && getCor() == Cor.PRETO){
                PosiçãoTabuleiro p1 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 1);
                PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() - 2);
                if(getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null){
                    matriz[posição.getLinha()][posição.getColuna() - 2] = true;
                }
            }
            PosiçãoTabuleiro pt4 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 4);
            if(testeRoque(pt4) && getCor() == Cor.PRETO){
                PosiçãoTabuleiro p1 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 1);
                PosiçãoTabuleiro p2 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 2);
                PosiçãoTabuleiro p3 = new PosiçãoTabuleiro(posição.getLinha(), posição.getColuna() + 3);
                if(getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null && getTabuleiro().peça(p3) == null){
                    matriz[posição.getLinha()][posição.getColuna() + 2] = true;
                }
            }
        }



        return matriz;
    }

    @Override
    public String toString() {
        return "K";
    }
    
    
}