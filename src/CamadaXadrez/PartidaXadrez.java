package CamadaXadrez;

import CamadaXadrez.PeçasDoXadrez.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import CamadaTabuleiro.*;;

public class PartidaXadrez {
    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean xeque;
    private boolean xequeMate;
    private PeçaXadrez enPassantVulnerável;
    private PeçaXadrez promovido;

    private ArrayList<Peça> peçasNoTabuleiro = new ArrayList<Peça>();
    private ArrayList<Peça> peçasCapturadas = new ArrayList<Peça>();
    
    
    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
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

    public PeçaXadrez substituirPeçaPromovida(String tipo){
        if(promovido == null){
            throw new IllegalStateException("Não há peça para ser promovida");
        }
        PosiçãoTabuleiro pos = promovido.getPosiçãoXadrez().paraPosiçãoTabuleiro();
        Peça p = tabuleiro.removerPeça(pos);
        peçasNoTabuleiro.remove(p);
        PeçaXadrez novaPeça;
        if(tipo.equals("B")) novaPeça = new Bispo(tabuleiro, promovido.getCor());
        else if(tipo.equals("C")) novaPeça = new Cavalo(tabuleiro, promovido.getCor());
        else if(tipo.equals("Q")) novaPeça = new Rainha(tabuleiro, promovido.getCor());
        else novaPeça =  new Torre(tabuleiro, promovido.getCor());
        tabuleiro.colocarPeça(novaPeça, pos);
        peçasNoTabuleiro.add(novaPeça);
        return novaPeça;
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
        PeçaXadrez peçaMovida = (PeçaXadrez)tabuleiro.peça(destinoTabuleiro);
        //Promoção
        promovido = null;
        if(peçaMovida instanceof Peão){
            if((peçaMovida.getCor() == Cor.BRANCO && destinoTabuleiro.getLinha() == 0) || (peçaMovida.getCor() == Cor.PRETO && destinoTabuleiro.getLinha() == 7)){
                promovido = (PeçaXadrez)tabuleiro.peça(destinoTabuleiro);
                promovido = substituirPeçaPromovida("Q");
            }
        }
        xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
        if(testeXequeMate(oponente(jogadorAtual))){
            xequeMate = true;
        }
        else{
            próximoTurno();
        }
        //En passant
        if(peçaMovida instanceof Peão && (destinoTabuleiro.getLinha() == origemTabuleiro.getLinha() - 2 || destinoTabuleiro.getLinha() == origemTabuleiro.getLinha() + 2)){
            enPassantVulnerável = peçaMovida;
        }
        else{
            enPassantVulnerável = null;
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
        //Roque pequeno
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() + 2 && peçaMovida.getCor() == Cor.BRANCO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 3);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(origemT);
            tabuleiro.colocarPeça(torre, destinoT);
            torre.incrementarContadorMovimentos();
        }
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() - 2 && peçaMovida.getCor() == Cor.PRETO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 3);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(origemT);
            tabuleiro.colocarPeça(torre, destinoT);
            torre.incrementarContadorMovimentos();
        }
        //Roque grande
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() - 2 && peçaMovida.getCor() == Cor.BRANCO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 4);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(origemT);
            tabuleiro.colocarPeça(torre, destinoT);
            torre.incrementarContadorMovimentos();
        }
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() + 2 && peçaMovida.getCor() == Cor.PRETO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 4);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(origemT);
            tabuleiro.colocarPeça(torre, destinoT);
            torre.incrementarContadorMovimentos();
        }
        //En passant
        if(peçaMovida instanceof Peão){
            if(origem.getColuna() != destino.getColuna() && peçaCapturada == null){
                PosiçãoTabuleiro peãoPosição;
                if(peçaMovida.getCor() == Cor.BRANCO){
                    peãoPosição = new PosiçãoTabuleiro(destino.getLinha() + 1, destino.getColuna());
                }
                else{
                    peãoPosição = new PosiçãoTabuleiro(destino.getLinha() - 1, destino.getColuna());
                }
                peçaCapturada = tabuleiro.removerPeça(peãoPosição);
                peçasCapturadas.add(peçaCapturada);
                peçasNoTabuleiro.remove(peçaCapturada);
            }
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
        //Roque pequeno
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() + 2 && peçaMovida.getCor() == Cor.BRANCO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 3);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(destinoT);
            tabuleiro.colocarPeça(torre, origemT);
            torre.decrementarContadorMovimentos();
        }
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() - 2 && peçaMovida.getCor() == Cor.PRETO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 3);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(destinoT);
            tabuleiro.colocarPeça(torre, origemT);
            torre.decrementarContadorMovimentos();
        }
        //Roque grande
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() - 2 && peçaMovida.getCor() == Cor.BRANCO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 4);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() - 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(destinoT);
            tabuleiro.colocarPeça(torre, origemT);
            torre.decrementarContadorMovimentos();
        }
        if(peçaMovida instanceof Rei && destino.getColuna() == origem.getColuna() + 2 && peçaMovida.getCor() == Cor.PRETO){
            PosiçãoTabuleiro origemT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 4);
            PosiçãoTabuleiro destinoT = new PosiçãoTabuleiro(origem.getLinha(), origem.getColuna() + 1);
            PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(destinoT);
            tabuleiro.colocarPeça(torre, origemT);
            torre.decrementarContadorMovimentos();
        }
        //En passant
        if(peçaMovida instanceof Peão){
            if(origem.getColuna() != destino.getColuna() && peçaCapturada == enPassantVulnerável){
                PeçaXadrez peão = (PeçaXadrez)tabuleiro.removerPeça(destino);
                PosiçãoTabuleiro peãoPosição;
                if(peçaMovida.getCor() == Cor.BRANCO){
                    peãoPosição = new PosiçãoTabuleiro(3, destino.getColuna());
                }
                else{
                    peãoPosição = new PosiçãoTabuleiro(4, destino.getColuna());
                }
                tabuleiro.colocarPeça(peão, peãoPosição);
            }
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
        List<Peça> copiaPecas = new ArrayList<>(peçasNoTabuleiro);
        for(Peça peça : copiaPecas){
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
        colocarNovaPeça('d', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('a', 7, new Peão(tabuleiro, Cor.PRETO, this));
       // colocarNovaPeça('b', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('c', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('d', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('e', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('f', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('g', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('h', 7, new Peão(tabuleiro, Cor.PRETO, this));
        colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('e', 8, new Rainha(tabuleiro, Cor.PRETO));
       // colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETO));

        colocarNovaPeça('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));  
        colocarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeça('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('a', 2, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('b', 7, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('c', 2, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('d', 2, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('e', 2, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('f', 2, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('g', 2, new Peão(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeça('h', 2, new Peão(tabuleiro, Cor.BRANCO, this));
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

    public ArrayList<Peça> getPeçasNoTabuleiro() {
        return peçasNoTabuleiro;
    }

    public ArrayList<Peça> getPeçasCapturadas() {
        return peçasCapturadas;
    }

    public PeçaXadrez getEnPassantVulnerável() {
        return enPassantVulnerável;
    }

    public PeçaXadrez getPromovido() {
        return promovido;
    }
    
}

