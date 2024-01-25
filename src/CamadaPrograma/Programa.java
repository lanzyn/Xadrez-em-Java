package CamadaPrograma;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

import CamadaXadrez.ExceçãoXadrez;
import CamadaXadrez.PartidaXadrez;
import CamadaXadrez.PosiçãoXadrez;

public class Programa {
    public static void main(String[] args) throws Exception {
        PartidaXadrez partida = new PartidaXadrez();
        Scanner sc = new Scanner(System.in);

        while (partida.getXequeMate() == false) {
            try {
                UI.limparTela();
                UI.imprimirPatida(partida);
                System.out.println();
                System.out.print("Origem: ");
                PosiçãoXadrez origem = UI.lerPosiçãoXadrez(sc);

                UI.limparTela();
                UI.imprimirPatida(partida, origem);

                System.out.println();
                System.out.print("Destino: ");
                PosiçãoXadrez destino = UI.lerPosiçãoXadrez(sc);
                partida.executarMovimento(origem, destino);

                if (partida.getPromovido() != null) {
                    System.out.print("Digite a peça para promoção (B/C/R/Q): ");
                    String tipo = sc.nextLine().toUpperCase();
                    if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("Q")) {
                        throw new InvalidParameterException("Tipo inválido para promoção");
                    }
                    partida.substituirPeçaPromovida(tipo);
                }

            } catch (ExceçãoXadrez e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch(InvalidParameterException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limparTela();
        UI.imprimirPatida(partida);
    }

}