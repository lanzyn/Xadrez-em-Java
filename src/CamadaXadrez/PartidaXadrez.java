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

    private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça){
        tabuleiro.colocarPeça(peça, new PosiçãoXadrez(coluna, linha).paraPosiçãoTabuleiro());
    }

    private void iniciarTabuleiro(){
        colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO));
        colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCO));
    }
}

