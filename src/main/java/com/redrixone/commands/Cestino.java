package com.redrixone.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Cestino implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player))
            return false;
        else {
            Inventory inventory = Bukkit.createInventory(null, 54, "Cestino");
            Player player = (Player) sender;
            player.openInventory(inventory);
        }

        return true;
    }
}
