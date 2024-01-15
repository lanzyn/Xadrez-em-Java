package CamadaXadrez;

import CamadaXadrez.PeçasDoXadrez.*;
import CamadaTabuleiro.*;;

public class PartidaXadrez {
    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean xeque;
    private boolean xequeMate;
    
    
    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        iniciarTabuleiro();
    }

    public PeçaXadrez[][] getPecas(){
        PeçaXadrez[][] matriz = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for(int i = 0;i<tabuleiro.getLinhas();i++){
            for(int j = 0;j<tabuleiro.getColunas();j++){
                matriz[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
            }
        }
        return matriz;
    }

    private void iniciarTabuleiro(){
        tabuleiro.colocarPeça(new Torre(tabuleiro, Cor.BRANCO), new Posição(0,0));
        tabuleiro.colocarPeça(new Rei(tabuleiro, Cor.PRETO), new Posição(0,4));
        tabuleiro.colocarPeça(new Rei(tabuleiro, Cor.BRANCO), new Posição(7,4));
        tabuleiro.colocarPeça(new Torre(tabuleiro, Cor.BRANCO), new Posição(0,7));
        tabuleiro.colocarPeça(new Torre(tabuleiro, Cor.BRANCO), new Posição(7,0));
        tabuleiro.colocarPeça(new Torre(tabuleiro, Cor.BRANCO), new Posição(7,7));
    }
}

