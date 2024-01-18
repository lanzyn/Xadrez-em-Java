package CamadaPrograma;

import java.util.Scanner;

import CamadaXadrez.PartidaXadrez;
import CamadaXadrez.PosiçãoXadrez;

public class Programa {
    public static void main(String[] args) throws Exception {
        PartidaXadrez partida = new PartidaXadrez();
        Scanner sc = new Scanner(System.in);
       while(true){
            UI.imprimirTabuleiro(partida.getPecas());
            System.out.println();
            System.out.print("Origem: ");
            PosiçãoXadrez origem = UI.lerPosiçãoXadrez(sc);
            System.out.println();  
            System.out.print("Destino: ");
            PosiçãoXadrez destino = UI.lerPosiçãoXadrez(sc);
            partida.executarMovimento(origem, destino);
            
        }
    
    }
}