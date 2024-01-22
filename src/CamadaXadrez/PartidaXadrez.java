package CamadaXadrez;

import CamadaXadrez.PeçasDoXadrez.*;

import java.lang.reflect.Array;
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
        próximoTurno();
        return peçaCapturada;
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

    private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça){
        tabuleiro.colocarPeça(peça, new PosiçãoXadrez(coluna, linha).paraPosiçãoTabuleiro());
        peçasNoTabuleiro.add(peça);
    }

    private void iniciarTabuleiro(){
        colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('d', 1, new Rei(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.PRETO));
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
    
}

