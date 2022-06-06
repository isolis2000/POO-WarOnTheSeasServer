/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commandsmanager.commands;

import client.ClientManager;
import commandsmanager.BaseCommand;
import gamelogic.Player;
import java.io.Serializable;
import server.ServerFrame;

/**
 *
 * @author ivan
 */
public class SkipTurnCommand extends BaseCommand implements Serializable {
    
    private String excecutionResult = "";
    
    public SkipTurnCommand(String commandName, String[] args, Player player) {
        super(commandName, args, true, false, player);
    }
    
    @Override
    public String executeOnServer() {
        ServerFrame.getServer().changeTurn();
        excecutionResult = "Jugador " + playerExcecuting.getName() + " ha saltado su turno.";
        return excecutionResult;
    }

    @Override
    public String executeOnClient() {
        ClientManager.getCM().getMainScreen().getPlayer().syncPlayer(getPlayerExcecuting());
        ClientManager.getCM().getMainScreen().updateInfoPanels();
        return excecutionResult;
    }
    
}
