package com.redrixone.commands;

import com.redrixone.commands.sottocomandi.kit.OttieniKit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Comando accessibile solamente da un giocatore.");
            return false;
        }

        Player player = (Player) sender;
        if (args.length > 0) {
            String subcommand = args[0];
            switch (subcommand) {
                case "starter":
                    OttieniKit.kitStarter(sender);
                    return true;
                case "vip":
                    if (player.hasPermission("kit.vip"))
                        OttieniKit.kitVip(sender);
                    else
                        player.sendMessage("§cNon hai il permesso!");
                    return true;
            }
        } else {
            boolean isVip = player.hasPermission("strawpvp.kits.vip");
            boolean isMvp = player.hasPermission("strawpvp.kits.mvp");

            String availableKits = "starter";
            if (isVip) {
                availableKits += ", vip";
            }
            if (isMvp) {
                availableKits += ", mvp";
            }
            sender.sendMessage("§6Kits: §e" + availableKits);
        }

        return true;
    }
}
