package com.redrixone.listeners;

import com.redrixone.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserListener implements Listener {

    Plugin plugin;

    public UserListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Location spawn = new Location(Bukkit.getWorld("world"), -8.500, 184, -24.500);
        Player player = event.getPlayer();
        player.teleport(spawn);
    }


    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        double fallDistance = player.getFallDistance();
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

                if (fallDistance >= 80.0) {
                    event.setCancelled(true);
                } else {
                    if (player.getHealth() - event.getFinalDamage() <= 1) {
                        Player killer = null;
                        event.setCancelled(true);
                        eseguiMorte(player);
                    }
                }
            } else if (player.getHealth() - event.getFinalDamage() <= 1) {
                event.setCancelled(true);
                Player killer = null;
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent) event;
                    Entity damager = entityEvent.getDamager();
                    if (damager instanceof Player) {
                        killer = (Player) damager;
                    } else if (damager instanceof Projectile) {
                        Projectile projectile = (Projectile) damager;
                        if (projectile.getShooter() instanceof Player) {
                            killer = (Player) projectile.getShooter();
                        }
                    }
                }
                eseguiMorte(player);
            }
        }
    }


    public static void eseguiMorte(Player player) {
        Economy economy = Main.getEconomy();
        Player killer = player.getKiller();
        PlayerInventory inventory = player.getInventory();
        Location location = player.getLocation();
        List<ItemStack> item = Arrays.asList(inventory.getContents());
        List<ItemStack> armor = Arrays.asList(inventory.getArmorContents());
        inventory.setArmorContents(new ItemStack[4]);
        inventory.clear();
        dropAll(item, location);
        dropAll(armor, location);
        Bukkit.getPluginManager().callEvent((Event)new PlayerDeathEvent(player, new ArrayList(), 0, ""));
        Location spawn = new Location(Bukkit.getWorld("world"), -8.500, 184, -24.500);
        if (spawn == null)
            spawn = player.getWorld().getSpawnLocation();
        player.teleport(spawn);
        Bukkit.getPluginManager().callEvent((Event)new PlayerRespawnEvent(player, spawn, false));
        player.setHealth(20);
        player.setFoodLevel(20);
        EconomyResponse response = economy.depositPlayer(killer, 200.0);
        killer.sendMessage("§7Hai ottenuto §e200$ §7uccidendo §c " + player.getName() + "§7.");
        Bukkit.broadcastMessage("§a" + player.getName() + " §eè stato ucciso da §c" + killer.getName());
        killer.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 5, 4));
    }

    private static void dropAll(List<ItemStack> items, Location location) {
        for (ItemStack item : items) {
            if (item != null && item.getType() != Material.AIR && !(item.getItemMeta().getDisplayName().contains("base"))) {
                location.getWorld().dropItemNaturally(location, item);
            }
        }
    }

    @EventHandler
    public void FeedDurability(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
