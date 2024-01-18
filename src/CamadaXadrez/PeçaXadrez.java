package CamadaXadrez;

import CamadaTabuleiro.Peça;
import CamadaTabuleiro.PosiçãoTabuleiro;
import CamadaTabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça{
    private Cor cor;
    private int contadorMovimentos;
    
    public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    protected void incrementarContadorMovimentos(){
        contadorMovimentos++;
    }

    protected void decrementarContadorMovimentos(){
        contadorMovimentos--;
    }

    protected boolean existePeçaOponente(PosiçãoTabuleiro posição){
        PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posição);
        return p != null && p.getCor() != cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getContadorMovimentos() {
        return contadorMovimentos;
    }
    
}