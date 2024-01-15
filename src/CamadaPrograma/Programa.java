package CamadaPrograma;

import CamadaXadrez.PartidaXadrez;

public class Programa {
    public static void main(String[] args) throws Exception {
        PartidaXadrez partida = new PartidaXadrez();
        UI.imprimirTabuleiro(partida.getPecas());
    }
}