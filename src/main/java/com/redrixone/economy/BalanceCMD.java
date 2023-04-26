package com.redrixone.economy;

import com.redrixone.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            int balance = (int) Main.getEconomy().getBalance(player);
            player.sendMessage("Conto: " + balance);
        }

        return true;
    }
}
