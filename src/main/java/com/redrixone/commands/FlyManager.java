package com.redrixone.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FlyManager implements CommandExecutor, Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("fly.toggle")) {
            player.setAllowFlight(true);
            player.setFlying(true);
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            if (sender.hasPermission("fly.toggle")) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    flyActions(player);
                } else if (args.length == 1) {
                    if (sender.hasPermission("fly.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            flyActions(target);
                            if (target.getAllowFlight() == true) {
                                sender.sendMessage(ChatColor.GREEN + "Fly enabled for " + ChatColor.DARK_GREEN + args[0]);
                            } else {
                                sender.sendMessage(ChatColor.RED + "Fly disabled for " + ChatColor.DARK_RED + args[0]);
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " is offline!");
                        }
                    }
                }
            } else {
                sender.sendMessage(ChatColor.AQUA + "Unknown command. Type \"/help\" for help.");
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage("Please insert the player nickname: /login <player>");
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    flyActions(target);
                    if (target.getAllowFlight() == true) {
                        sender.sendMessage(ChatColor.GREEN + "Fly enabled for " + ChatColor.DARK_GREEN + args[0]);
                    } else {
                        sender.sendMessage(ChatColor.RED + "Fly disabled for " + ChatColor.DARK_RED + args[0]);
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " is offline!");
                }
            }
        }

        return true;
    }

    private void flyActions(Player player) {
        if (player.getAllowFlight() == true) {
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.RED + "Fly disabled.");
        } else {
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.GREEN + "Fly enabled.");
        }
    }
}