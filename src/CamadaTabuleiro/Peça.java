package CamadaTabuleiro;

public abstract class Peça {
    protected PosiçãoTabuleiro posição;
    private Tabuleiro tabuleiro;

    public Peça(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posição = null;
    }

    public abstract boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel(PosiçãoTabuleiro posição){
        return movimentosPossiveis()[posição.getLinha()][posição.getColuna()];
    }

    public boolean existeMovimentoPossivel(){
        boolean[][] movimentos = movimentosPossiveis();
        for(int i = 0;i<movimentos.length;i++){
            for(int j = 0;j<movimentos.length;j++){
                if(movimentos[i][j] == true){
                    return true;
                }
                
            }
        }
        return false;

    }

    @Override
    public String toString() {
        return posição.toString();
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    
}