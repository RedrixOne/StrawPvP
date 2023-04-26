package com.redrixone.statistiche;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class Stats implements Listener {
    private Connection connection;


    public Stats (JavaPlugin plugin) {
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

        createTable();
        createTable2();
    }


    public void createTable() {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS stats (" +
                        "player_uuid VARCHAR(36) NOT NULL," +
                        "player_name VARCHAR(255) NOT NULL," +
                        "kills INT NOT NULL," +
                        "deaths INT NOT NULL," +
                        "PRIMARY KEY (player_uuid)" +
                        ");")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawCreateFail");
            e.printStackTrace();
        }
    }


    public void createTable2() {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS killstreaks (" + "player_uuid VARCHAR(36) NOT NULL," + "killstreak INT NOT NULL," + "PRIMARY KEY (player_uuid)" + ");")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawCreateFail");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player victim = event.getEntity();
        if (killer != null) {
            addKill(killer);
            addStreak(killer);
        }

        if (victim != null) {
            addDeath(victim);
            setStreak(victim);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        addPlayerInTable(player);
        addPlayerInKillstreak(player);
    }

    public void addKill(Player player) {
        if (!isConnected()) {
            System.out.println("Connessione al database non valida!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE stats SET kills=kills+1 WHERE player_uuid=?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawUpdateFail");
            e.printStackTrace();
        }
    }

    public void addDeath(Player player) {
        if (!isConnected()) {
            System.out.println("Connessione al database non valida!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE stats SET deaths=deaths+1 WHERE player_uuid=?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawUpdateFail");
            e.printStackTrace();
        }
    }

    public void addStreak(Player player) {
        if (!isConnected()) {
            System.out.println("Connessione al database non valida!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE killstreaks SET killstreak=killstreak+1 WHERE player_uuid=?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawUpdateFail");
            e.printStackTrace();
        }
    }

    public void setStreak(Player player) {
        if (!isConnected()) {
            System.out.println("Connessione al database non valida!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE killstreaks SET killstreak=0 WHERE player_uuid=?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawUpdateFail");
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5); // controlla che la connessione sia aperta e valida
        } catch (SQLException e) {
            return false;
        }
    }

    private void addPlayerInTable(Player player) {
        if (!isConnected()) {
            System.out.println("Connessione al database non valida!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT IGNORE INTO stats (player_uuid, player_name, kills, deaths) VALUES (?, ?, 0, 0)")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, player.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawInsertFail");
            e.printStackTrace();
        }
    }

    private void addPlayerInKillstreak(Player player) {
        if (!isConnected()) {
            System.out.println("Connessione al database non valida!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT IGNORE INTO killstreaks (player_uuid, killstreak) VALUES (?, 0)")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("StrawInsertFail");
            e.printStackTrace();
        }
    }


}
