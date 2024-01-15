package CamadaXadrez.PeçasDoXadrez;

import CamadaTabuleiro.Tabuleiro;
import CamadaXadrez.Cor;
import CamadaXadrez.PeçaXadrez;

public class Torre extends PeçaXadrez{

    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'movimentosPossiveis'");
    }

    @Override
    public String toString() {
        return "R";
    }

    
}