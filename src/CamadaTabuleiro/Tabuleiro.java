package CamadaTabuleiro;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peça[][] peças;
    
    public Tabuleiro(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        peças = new Peça[linhas][colunas];
    }

    public Peça peça(int linha, int coluna){
        return peças[linha][coluna];
    }

    public Peça peça(Posição posição){
        return peças[posição.getLinha()][posição.getColuna()];
    }

    public void colocarPeça(Peça peça, Posição posição){
      peças[posição.getLinha()][posição.getColuna()] = peça;
      peça.posição = posição;
    }

    public Peça removerPeça(Posição posição){
        Peça peçaRemovida = peça(posição);
        peças[posição.getLinha()][posição.getColuna()] = null;
        return peçaRemovida;
    }

    public boolean existePosição(Posição posição){
        if(posição.getLinha() < 0 || posição.getLinha() > linhas || posição.getColuna() < 0 || posição.getColuna() > colunas){
            return false;
        }
        else return true;
    }

    public boolean existePeça(Posição posição){
        if(peça(posição) == null) return false;
        else return true;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

}