package com.redrixone.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return false;
        else {
            Location spawn = new Location(Bukkit.getWorld("world"), -8.500, 184, -24.500);
            Player player = (Player) sender;
            player.teleport(spawn);
        }

        return true;
    }
}
