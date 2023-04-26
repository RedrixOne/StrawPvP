package com.redrixone.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.redrixone.menus.Menu.menu;
public class HelpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Comando accessibile solamente da un giocatore.");
            return true;
        }

        Player player = (Player) sender;
        menu(player);

        return true;
    }
}
