package gamelogic.fighters;

import client.gui.Cell;
import gamelogic.Fighter;
import gamelogic.Player;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import server.ThreadServer;

public class ReleaseTheKraken extends Fighter {

    public ReleaseTheKraken(String name, String image, int percentage, int power, int resistance, int sanity, Color color, Player playerExecuting) {
        super(name, image, "Release The Kraken", percentage, power, resistance, sanity, color, playerExecuting);
    }
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    @Override
    protected boolean specialAttack(String[] args, ThreadServer target) {
        return switch (args[3].toUpperCase()) {
            case "TENTACLES" -> tentacles(args, target);
            case "KRAKEN BREATH" -> krakenBreath(args, target);
            case "RELEASE THE KRAKEN" -> releaseTheKraken(args, target);
            default ->false;
        };
    }   
    
    private boolean tentacles(String[] args, ThreadServer target) {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x.add(Integer.parseInt(args[i + 1]));
                    case "y" -> y.add(Integer.parseInt(args[i + 1]));
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            String forRecord = "Jugador " + this.player.getName() 
                    + " destruyo esta casilla con el ataque Tentacles";
            for (int i = 0; i < 3; i++) {
                for (Cell cell : target.getPlayer().getCellsInRadius(new int[] {x.get(i), y.get(i)}, 1)) {
                    if (cell.setHp(0, forRecord))
                        target.getPlayer().removeCell();
                }
            }
            return true;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /*
    Kraken Breath: se selecciona una
    casilla donde el Kraken lanza su
    aliento hacia una dirección: arriba,
    abajo, derecha, izquierda. El aliento
    destruye entre 1 y 8 casillas en esa
    dirección.
    */
    
    // 0 attack 1 target 2 fighter 3 attacktype 4 instructions
    private boolean krakenBreath(String[] args, ThreadServer target) {
        Random random = new Random();       
        int numOfCells = random.nextInt(8) + 1;
        int x = 0; int y = 0;
        String direction = "";
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    case "direccion" -> direction = args[i + 1].toLowerCase();
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            String forRecord = "Jugador " + this.player.getName() 
                    + " destruyo esta casilla con el ataque Kraken Breath";
            for (Cell cell : target.getPlayer().getCellsInLine(new int[] {x, y}, numOfCells, direction)) {
                if (cell.setHp(0, forRecord))
                    target.getPlayer().removeCell();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    /*
    Release the Kraken: el Kraken
    aparece en un punto del mapa y
    destruye todo en un radio de
    1,2,3,4,5,6,7,8,9 casillas.
    */
    private boolean releaseTheKraken(String[] args, ThreadServer target) {
        Random random = new Random();       
        int radius = random.nextInt(9) + 1;
        int x = 0; int y = 0;
        try {
            for (int i = 4; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "x" -> x = Integer.parseInt(args[i + 1]);
                    case "y" -> y = Integer.parseInt(args[i + 1]);
                    default -> throw new NumberFormatException();
                }
                i++;
            }
            String forRecord = "Jugador " + this.player.getName() 
                    + " destruyo esta casilla con el ataque Release The Kraken";
            for (Cell cell : target.getPlayer().getCellsInRadius(new int[] {x, y}, radius)) {
                if (cell.setHp(0, forRecord))
                    target.getPlayer().removeCell();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }    
    }
    
}
