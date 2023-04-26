package com.redrixone.scoreboard;

import com.redrixone.Main;
import com.redrixone.managers.CombatManager;
import com.redrixone.statistiche.Stats;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Scoreboard implements Listener {

    private CombatManager combatManager;
    private BukkitTask task;
    private Main plugin;
    private Connection connection;
    private Stats stats;



    Date ora = new Date();
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");



    public Scoreboard(Main plugin, CombatManager combatManager, JavaPlugin javaPlugin) {
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

    @EventHandler
    public void onCreate(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Netherboard.instance().createBoard(player, "StrawPvP");
    }

    public void initScoreboard(int delay) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(plugin, 0, delay);
    }

    public void updateScoreboard(Player player) {
        UUID playerUUID = player.getUniqueId();
        int balance = (int) Main.getEconomy().getBalance(player);
        BPlayerBoard board = Netherboard.instance().getBoard(player);
        String formattedBalance = balance >= 1000000 ? String.format("%.1fM", balance / 1000000.0)
                : balance >= 1000 ? String.format("%.1fk", balance / 1000.0)
                : String.valueOf(balance);
        if (!(combatManager.isInCombat(playerUUID))) {
            String replace = PlaceholderAPI.setPlaceholders(player, "%player_ping%");
            board.set("§7" + format.format(ora) + " §8kitpvp-1", 12);
            board.set("§c ", 11);
            board.set("§r• §6Nome ➨ §e" + player.getName(), 10);
            board.set("§r• §6Ping ➨ §7  " + replace, 9);
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

        //}

        board.setName("§6§lKITPVP");
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
        int killstreak = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT killstreak FROM killstreaks WHERE player_uuid=?")) {
            statement.setString(1, playerUUID.toString());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                killstreak = result.getInt("killstreak");
            }
        } catch (SQLException e) {
            System.out.println("StrawSelectFail");
            e.printStackTrace();
        }

        return killstreak;
    }


}