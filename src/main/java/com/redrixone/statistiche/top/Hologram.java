package com.redrixone.statistiche.top;

import com.redrixone.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.sql.*;
import java.util.UUID;

public class Hologram {
    private static ArmorStand hologram;

    private static Main plugin;

    public Hologram(Main plugin) {
        Hologram.plugin = plugin;
    }

    public static void setHologram() {
        String[] topKills = getTopKills();

        Location location = new Location(Bukkit.getWorld("world"), -23.500, 185, -16.500);
        hologram = location.getWorld().spawn(location, ArmorStand.class);
        hologram.setGravity(false);
        hologram.setMarker(true);
        hologram.setVisible(false);

        // Aggiorna l'ologramma ogni minuto
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                // Aggiorna il testo dell'ologramma con la top 3
                hologram.setCustomName("Top Kills:\n1. " + topKills[0] + "\n2. " + topKills[1] + "\n3. " + topKills[2]);
            }
        }, 0, 20 * 60); // 20 ticks = 1 secondo, 60 secondi = 1 minuto
    }

    private static String[] getTopKills() {
        String[] topKills = new String[10];
        try {
            String username = "luckperms";
            String password = "luckpermspass";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teststats?characterEncoding=utf8", username, password);

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT player_uuid, kills FROM stats ORDER BY kills DESC LIMIT 3");

            int i = 0;
            while (rs.next()) {
                String uuidString = rs.getString("player_uuid");
                int kills = rs.getInt("kills");
                String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuidString)).getName();
                topKills[i] = playerName + " (" + kills + ")";
                i++;
            }

            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topKills;
    }

}
