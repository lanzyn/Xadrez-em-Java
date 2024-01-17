package CamadaXadrez;

import CamadaTabuleiro.PosiçãoTabuleiro;

public class PosiçãoXadrez {
    private char coluna;
    private int linha;
    
    public PosiçãoXadrez(char coluna, int linha) {
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected PosiçãoTabuleiro paraPosiçãoTabuleiro(){
        return new PosiçãoTabuleiro(8 - linha, coluna - 'a');
    }

    protected PosiçãoXadrez paraPosiçãoXadrez(PosiçãoTabuleiro posição){
        return new PosiçãoXadrez((char)('a' - posição.getColuna()), (posição.getLinha() -8) *(-1 ));
    }
    
    @Override
    public String toString(){
        return "" + coluna + linha;
    }
}
