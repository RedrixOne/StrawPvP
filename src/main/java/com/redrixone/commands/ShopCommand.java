package com.redrixone.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.redrixone.menus.Shop.apriSezioni;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return false;
        else {
            Player player = (Player) sender;
            apriSezioni(player);
        }

        return true;
    }
}
