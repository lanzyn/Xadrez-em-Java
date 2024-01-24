package CamadaXadrez;

import CamadaXadrez.PeçasDoXadrez.*;

import java.util.ArrayList;

import CamadaTabuleiro.*;;

public class PartidaXadrez {
    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean xeque;
    private boolean xequeMate;

    private ArrayList<Peça> peçasNoTabuleiro = new ArrayList<Peça>();
    private ArrayList<Peça> peçasCapturadas = new ArrayList<Peça>();
    
    
    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        iniciarTabuleiro();
    }

    public ArrayList<Peça> getPeçasNoTabuleiro() {
        return peçasNoTabuleiro;
    }

    public ArrayList<Peça> getPeçasCapturadas() {
        return peçasCapturadas;
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

    public boolean[][] movimentosPossiveis(PosiçãoXadrez origemPosição){
        PosiçãoTabuleiro posição = origemPosição.paraPosiçãoTabuleiro();
        validarPosiçãoOrigem(posição);
        return tabuleiro.peça(posição).movimentosPossiveis();
    }

    public PeçaXadrez executarMovimento(PosiçãoXadrez origem, PosiçãoXadrez destino){
        PosiçãoTabuleiro origemTabuleiro = origem.paraPosiçãoTabuleiro();
        PosiçãoTabuleiro destinoTabuleiro = destino.paraPosiçãoTabuleiro();
        validarPosiçãoOrigem(origemTabuleiro);
        validarPosiçãoDestino(origemTabuleiro, destinoTabuleiro);
        Peça peçaCapturada = fazerMovimento(origemTabuleiro, destinoTabuleiro);
        if(testeXeque(jogadorAtual)){
            desfazerMovimento(origemTabuleiro, destinoTabuleiro, peçaCapturada);
            throw new ExceçãoXadrez("Você não pode se colocar em xeque");
        }
        xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
        if(testeXequeMate(oponente(jogadorAtual))){
            xequeMate = true;
        }
        else{
            próximoTurno();
        }
        return (PeçaXadrez)peçaCapturada;
    }

    public Peça fazerMovimento(PosiçãoTabuleiro origem, PosiçãoTabuleiro destino){
        PeçaXadrez peçaMovida = (PeçaXadrez)tabuleiro.removerPeça(origem);
        peçaMovida.incrementarContadorMovimentos();
        Peça peçaCapturada = tabuleiro.removerPeça(destino);
        tabuleiro.colocarPeça(peçaMovida, destino);
        if(peçaCapturada != null){
            peçasCapturadas.add(peçaCapturada);
            peçasNoTabuleiro.remove(peçaCapturada);
        }
        return peçaCapturada;
    }

    public void desfazerMovimento(PosiçãoTabuleiro origem, PosiçãoTabuleiro destino, Peça peçaCapturada){
        PeçaXadrez peçaMovida = (PeçaXadrez)tabuleiro.removerPeça(destino);
        peçaMovida.decrementarContadorMovimentos();
        tabuleiro.colocarPeça(peçaMovida, origem);
        if(peçaCapturada != null){
            tabuleiro.colocarPeça(peçaCapturada, destino);
            peçasCapturadas.remove(peçaCapturada);
            peçasNoTabuleiro.add(peçaCapturada);
        }
    }

    public void validarPosiçãoOrigem(PosiçãoTabuleiro posição){
        if(!tabuleiro.existePeça(posição)) throw new ExceçãoXadrez("Não existe peça na posição de origem");
        if(!tabuleiro.peça(posição).existeMovimentoPossivel()) throw new ExceçãoXadrez("Não há movimentos possíveis para a peça escolhida");
        if(jogadorAtual != ((PeçaXadrez)tabuleiro.peça(posição)).getCor()) throw new ExceçãoXadrez("A peça escolhida não é sua");
    }

    public void validarPosiçãoDestino(PosiçãoTabuleiro origem, PosiçãoTabuleiro destino){
        if(!tabuleiro.peça(origem).movimentoPossivel(destino)) throw new ExceçãoXadrez("Movimento invalido! A peça escolhida não pode se mover para a posição de destino");
    }

    public void próximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }
    
    private Cor oponente(Cor cor){
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PeçaXadrez rei(Cor cor){
        for(Peça peça : peçasNoTabuleiro){
            if(((PeçaXadrez)peça).getCor() == cor && peça instanceof Rei){
                return (PeçaXadrez)peça;
            }
        }
        throw new IllegalStateException("Não existe o rei da cor " + cor + " no tabuleiro");
    }
    
    private boolean testeXeque(Cor cor){
        PosiçãoTabuleiro posiçãoRei = rei(cor).getPosiçãoXadrez().paraPosiçãoTabuleiro();
        for(Peça peça : peçasNoTabuleiro){
            if(((PeçaXadrez)peça).getCor() == oponente(cor)){
                boolean[][] matriz = peça.movimentosPossiveis();
                if(matriz[posiçãoRei.getLinha()][posiçãoRei.getColuna()]){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean testeXequeMate(Cor cor){
        if(!testeXeque(cor)) return false;
        for(Peça peça : peçasNoTabuleiro){
            if(((PeçaXadrez)peça).getCor() == cor){
                boolean[][] matriz = peça.movimentosPossiveis();
                for(int i = 0; i<tabuleiro.getLinhas(); i++){
                    for(int j = 0; j<tabuleiro.getColunas(); j++){
                        if(matriz[i][j]){
                            PosiçãoTabuleiro origem = ((PeçaXadrez)peça).getPosiçãoXadrez().paraPosiçãoTabuleiro();
                            PosiçãoTabuleiro destino = new PosiçãoTabuleiro(i, j);
                            Peça peçaCapturada = fazerMovimento(origem, destino);
                            boolean testeXeque = testeXeque(cor);
                            desfazerMovimento(origem, destino, peçaCapturada);
                            if(!testeXeque){
                                return false;
                            }
                        }
                    }
                }
            }
            }
        return true;
    }

    private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça){
        tabuleiro.colocarPeça(peça, new PosiçãoXadrez(coluna, linha).paraPosiçãoTabuleiro());
        peçasNoTabuleiro.add(peça);
    }

    private void iniciarTabuleiro(){
        colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETO));
        colocarNovaPeça('a', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('b', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('c', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('d', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 7, new Peão(tabuleiro, Cor.PRETO));
        colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        
        colocarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('a', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('b', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('c', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('f', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('g', 2, new Peão(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 2, new Peão(tabuleiro, Cor.BRANCO));
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean getXeque() {
        return xeque;
    }

    public boolean getXequeMate() {
        return xequeMate;
    }
    
}

