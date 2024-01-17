package CamadaTabuleiro;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peça[][] peças;
    
    public Tabuleiro(int linhas, int colunas) {
        if(linhas < 1 || colunas < 1) throw new ExceçãoTabuleiro("Erro ao criar tabuleiro: é necessário que haja pelo menos uma linha e uma coluna");
        this.linhas = linhas;
        this.colunas = colunas;
        peças = new Peça[linhas][colunas];
    }

    public Peça peça(int linha, int coluna){
        if(!existePosição(new PosiçãoTabuleiro(linha, coluna))) throw new ExceçãoTabuleiro("Posição não existe no tabuleiro");
        return peças[linha][coluna];
    }

    public Peça peça(PosiçãoTabuleiro posição){
        if(!existePosição(posição)) throw new ExceçãoTabuleiro("Posição não existe no tabuleiro");
        return peças[posição.getLinha()][posição.getColuna()];
    }

    public void colocarPeça(Peça peça, PosiçãoTabuleiro posição){
        if(existePeça(posição)) throw new ExceçãoTabuleiro("Já existe uma peça na posição " + posição);
        peças[posição.getLinha()][posição.getColuna()] = peça;
        peça.posição = posição;
    }

    public Peça removerPeça(PosiçãoTabuleiro posição){
        if(existePeça(posição) == false) throw new ExceçãoTabuleiro("Não há peça na posição " + posição);
        Peça peçaRemovida = peça(posição);
        peças[posição.getLinha()][posição.getColuna()] = null;
        return peçaRemovida;
    }

    public boolean existePosição(PosiçãoTabuleiro posição){
        if(posição.getLinha() < 0 || posição.getLinha() > linhas || posição.getColuna() < 0 || posição.getColuna() > colunas){
            return false;
        }
        else return true;
    }

    public boolean existePeça(PosiçãoTabuleiro posição){
        if(!existePosição(posição)) throw new ExceçãoTabuleiro("Posição não existe no tabuleiro");
        if(peça(posição) == null) return false;
        else return true;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }
}