package com.redrixone.listeners;

import com.redrixone.Main;
import com.redrixone.listeners.UserListener;
import com.redrixone.managers.CombatManager;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatLog implements Listener {

    private Plugin plugin;
    private CombatManager combatManager;
    private Map<UUID, BukkitRunnable> combatTimers = new HashMap<>();
    private Map<UUID, BukkitTask> timers = new HashMap<>();
    private Connection connection;
    Date ora = new Date();
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
    public CombatLog(Plugin plugin, CombatManager combatManager, JavaPlugin javaPlugin) {
        this.plugin = plugin;
        this.combatManager = combatManager;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/teststats?characterEncoding=utf8";
            String username = "luckperms";
            String password = "luckpermspass";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("StrawFailDB: " + e.getMessage());
            e.printStackTrace();
        }
    }



    //todo:Da fare meglio e aggiungere danno da arco

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        UUID playerUUID = player.getUniqueId();

        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            UUID attackerUUID = attacker.getUniqueId();

            stopTimer(playerUUID);
            stopTimer(attackerUUID);
            startTimer(playerUUID, player);
            startTimer(attackerUUID, attacker);

            if (!(combatManager.isInCombat(playerUUID))) {
                player.sendMessage("§cSei stato messo in combat da §4" + attacker.getName() + "§c!");
            }
            if (!(combatManager.isInCombat(attackerUUID))) {
                attacker.sendMessage("§cHai messo in combat §4" + player.getName() + "§c!");
            }

            combatManager.setInCombat(attackerUUID);
            combatManager.setInCombat(playerUUID);

            if (combatTimers.containsKey(playerUUID)) {
                combatTimers.get(playerUUID).cancel();
            }

            if (combatTimers.containsKey(attackerUUID)) {
                combatTimers.get(attackerUUID).cancel();
            }

            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    combatManager.setOutOfCombat(attackerUUID);
                    combatManager.setOutOfCombat(playerUUID);
                    attacker.sendMessage(ChatColor.GREEN + "Non sei più in combat.");
                    player.sendMessage(ChatColor.GREEN + "Non sei più in combat.");
                    combatTimers.remove(playerUUID);
                    combatTimers.remove(attackerUUID);
                }
            };

            combatTimers.put(playerUUID, task);
            combatTimers.put(attackerUUID, task);

            task.runTaskLater(plugin, 10 * 20L);
        } else if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player attacker = (Player) arrow.getShooter();
                UUID attackerUUID = attacker.getUniqueId();

                stopTimer(playerUUID);
                stopTimer(attackerUUID);
                startTimer(playerUUID, player);
                startTimer(attackerUUID, attacker);

                if (!(combatManager.isInCombat(playerUUID))) {
                    player.sendMessage("§cSei stato messo in combat da §4" + attacker.getName() + "§c!");
                }
                if (!(combatManager.isInCombat(attackerUUID))) {
                    attacker.sendMessage("§cHai messo in combat §4" + player.getName() + "§c!");
                }

                combatManager.setInCombat(attackerUUID);
                combatManager.setInCombat(playerUUID);

                if (combatTimers.containsKey(playerUUID)) {
                    combatTimers.get(playerUUID).cancel();
                }

                if (combatTimers.containsKey(attackerUUID)) {
                    combatTimers.get(attackerUUID).cancel();
                }

                BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        combatManager.setOutOfCombat(attackerUUID);
                        combatManager.setOutOfCombat(playerUUID);
                        attacker.sendMessage(ChatColor.GREEN + "Non sei più in combat.");
                        player.sendMessage(ChatColor.GREEN + "Non sei più in combat.");
                        combatTimers.remove(playerUUID);
                        combatTimers.remove(attackerUUID);
                    }
                };

                combatTimers.put(playerUUID, task);
                combatTimers.put(attackerUUID, task);

                task.runTaskLater(plugin, 10 * 20L);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (combatManager.isInCombat(playerUUID)) {
            UserListener.eseguiMorte(player);
        }
        if (combatTimers.containsKey(playerUUID)) {
            combatTimers.get(playerUUID).cancel();
            combatTimers.remove(playerUUID);
        }
    }

    public void startTimer(UUID playerUUID, Player player) {
        BPlayerBoard board = Netherboard.instance().getBoard(player);
        int balance = (int) Main.getEconomy().getBalance(player);
        String formattedBalance = balance >= 1000000 ? String.format("%.1fM", balance / 1000000.0)
                : balance >= 1000 ? String.format("%.1fk", balance / 1000.0)
                : String.valueOf(balance);
        if (timers.containsKey(playerUUID))
            return;
        BukkitTask task = new BukkitRunnable() {
            int seconds = 10;

            @Override
            public void run() {
                if (seconds <= 0) {
                    cancel();
                    timers.remove(playerUUID);
                    combatManager.setOutOfCombat(playerUUID);
                    return;
                }

                seconds--;

                int displaySec = seconds + 1;

                String replace = PlaceholderAPI.setPlaceholders(player, "%player_ping%");
                board.set("§7" + format.format(ora) + " §8kitpvp-1", 13);
                board.set("§c ", 12);
                board.set("§r• §6Nome ➨ §e" + player.getName(), 11);
                board.set("§r• §6Ping ➨ §7  " + replace, 10);
                board.set("§r• §6Combat ➨ §e" + displaySec, 9);
                board.set("§f", 8);
                board.set(" §e§lSTATISTICHE ", 7);
                board.set("§l", 6);
                board.set("§r• §6Uccisioni: §e" + getKills(playerUUID), 5);
                board.set("§r• §6Morti ➨ §e" + getDeaths(playerUUID) , 4);
                board.set("§r• §6Soldi ➨ §e" + formattedBalance + "$", 3);
                board.set("§r• §6Streak ➨ §e" + getStreak(playerUUID), 2);
                board.set("§b ", 1);
                board.set("§ewww.strawlandia.it", 0);
            }
        }.runTaskTimer(plugin, 0L, 20L);
        timers.put(playerUUID, task);
    }

    public void stopTimer(UUID playerUUID) {
        if (timers.containsKey(playerUUID)) {
            BukkitTask task = timers.get(playerUUID);
            task.cancel();
            timers.remove(playerUUID);
        }
    }

    public int getKills(UUID playerUUID) {
        int kills = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT kills FROM stats WHERE player_uuid=?")) {
            statement.setString(1, playerUUID.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                kills = result.getInt("kills");
            }
        } catch (SQLException e) {
            System.out.println("StrawSelectFail");
            e.printStackTrace();
        }

        return kills;
    }

    public int getDeaths(UUID playerUUID) {
        int deaths = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT deaths FROM stats WHERE player_uuid=?")) {
            statement.setString(1, playerUUID.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                deaths = result.getInt("deaths");
            }
        } catch (SQLException e) {
            System.out.println("StrawSelectFail");
            e.printStackTrace();
        }

        return deaths;
    }

    public int getStreak(UUID playerUUID) {
        int deaths = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT killstreak FROM killstreaks WHERE player_uuid=?")) {
            statement.setString(1, playerUUID.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                deaths = result.getInt("killstreak");
            }
        } catch (SQLException e) {
            System.out.println("StrawSelectFail");
            e.printStackTrace();
        }

        return deaths;
    }

}