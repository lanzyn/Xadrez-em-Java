package CamadaTabuleiro;

public class PosiçãoTabuleiro {
    private int linha;
    private int coluna;

    public PosiçãoTabuleiro(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void definirValores(int linha, int coluna) {
        setColuna(coluna);
        setLinha(linha);
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    @Override
    public String toString() {
        return linha + ", " + coluna;
    }
}