package com.redrixone;

import com.redrixone.commands.*;
import com.redrixone.commands.gamemodes.gmc;
import com.redrixone.commands.gamemodes.gms;
import com.redrixone.commands.sottocomandi.kit.GetKit;
import com.redrixone.economy.BalanceCMD;
import com.redrixone.listeners.CombatLog;
import com.redrixone.events.DeathMessages;
import com.redrixone.events.HelpListener;
import com.redrixone.events.kits.PlayerJoin;
import com.redrixone.listeners.ShopListener;
import com.redrixone.listeners.UserListener;
import com.redrixone.managers.CombatManager;
import com.redrixone.scoreboard.Scoreboard;
import com.redrixone.statistiche.Stats;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public FileConfiguration kitsConfig;
    public static File kitsFile;
    public static Main instance;
    private CombatManager combatManager = new CombatManager();
    private CombatLog combatLog;
    private static Economy econ = null;
    private Connection connection;
    private String host, database, username, password;
    private int port;
    private int hologramTaskId = -1;

    //Mammt
    public void onEnable() {
        instance = this;
        host = "localhost";
        port = 3306;
        database = "teststats";
        username = "luckperms";
        password = "luckpermspass";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=utf8", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //createHologram();

        //hologramTaskId = Bukkit.getScheduler().runTaskTimer(this, this::createHologram, 20 * 30, 20 * 30).getTaskId();

        //dATABASE

        System.out.println("StrawPvP | Enabled");

        getServer().getPluginManager().registerEvents(new Scoreboard(this, combatManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new DeathMessages(), this);
        getServer().getPluginManager().registerEvents(new CombatLog(this, combatManager, this), this);
        getServer().getPluginManager().registerEvents(new HelpListener(), this);
        getServer().getPluginManager().registerEvents(new UserListener(this), this);
        getServer().getPluginManager().registerEvents(new Stats(this), this);
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getServer().getPluginManager().registerEvents(new DeathMessages(), this);
        //getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("kit").setExecutor(new KitCommand(this));
        getCommand("cestino").setExecutor(new Cestino());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("bal").setExecutor(new BalanceCMD());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("fly").setExecutor(new FlyManager());
        getCommand("gmc").setExecutor(new gmc());
        getCommand("gms").setExecutor(new gms());
        getCommand("creakit").setExecutor(new CreateKit(this));
        getCommand("getkit").setExecutor(new GetKit(this));

        Scoreboard scoreboard = new Scoreboard(this, combatManager, this);
        scoreboard.initScoreboard(20);

        //Vault
        if (!setupEconomy() ) {
            System.out.println("No economy plugin found. Disabling Vault");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Crea Kits
        kitsFile = new File(getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            saveResource("kits.yml", false);
        }
        kitsConfig = YamlConfiguration.loadConfiguration(kitsFile);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public void onDisable() {
        saveKitsConfig();
    }

    public static Main getInstance() {
        return instance;
    }

    private void saveKitsConfig() {
        try {
            kitsConfig.save(kitsFile);
            reloadKitsConfig();
        } catch (IOException e) {
            getLogger().warning("Impossibile salvare il file kits.yml: " + e.getMessage());
        }
    }

    private void reloadKitsConfig() {
        kitsConfig = YamlConfiguration.loadConfiguration(kitsFile);
    }

}
