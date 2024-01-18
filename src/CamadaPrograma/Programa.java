package CamadaPrograma;

import java.util.InputMismatchException;
import java.util.Scanner;

import CamadaXadrez.ExceçãoXadrez;
import CamadaXadrez.PartidaXadrez;
import CamadaXadrez.PosiçãoXadrez;

public class Programa {
    public static void main(String[] args) throws Exception {
        PartidaXadrez partida = new PartidaXadrez();
        Scanner sc = new Scanner(System.in);
       while(true){
        try{
                UI.limparTela();
                UI.imprimirTabuleiro(partida.getPecas());
                System.out.println();
                System.out.print("Origem: ");
                PosiçãoXadrez origem = UI.lerPosiçãoXadrez(sc);
                System.out.println();  
                System.out.print("Destino: ");
                PosiçãoXadrez destino = UI.lerPosiçãoXadrez(sc);
                partida.executarMovimento(origem, destino); }
        catch(ExceçãoXadrez e){
            System.out.println(e.getMessage());
            sc.nextLine();
        }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }
}
}