package com.redrixone.commands;

import com.redrixone.managers.CombatManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpawnCommand implements CommandExecutor {

    private CombatManager combatManager;
    
    public SpawnCommand(CombatManager combatManager) {
        this.combatManager = combatManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        if (!(sender instanceof Player))
            return false;
        else {
            if (combatManager.isInCombat(playerUUID)) {
                Location spawn = new Location(Bukkit.getWorld("world"), -8.500, 184, -24.500);
                player.teleport(spawn);
            } else {
                player.sendMessage("§cNon puoi eseguire comandi durante il combattimento");
            }
        }

        return true;
    }
}
