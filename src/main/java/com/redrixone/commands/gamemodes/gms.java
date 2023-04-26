package com.redrixone.commands.gamemodes;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gms implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (!(sender instanceof Player)) {
            return false;
        } else {
            if (player.hasPermission("gm.use")) {
                if (args.length == 0) {
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        sender.sendMessage("§cSei già in survival!");
                    } else {
                        player.setGameMode(GameMode.SURVIVAL);
                        sender.sendMessage("§eGamemode aggiornata!");
                    }
                }
            } else {
                sender.sendMessage("§cNon hai il permesso!");
            }
        }
        return true;
    }

}
