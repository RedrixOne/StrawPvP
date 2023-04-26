package com.redrixone.listeners;

import com.redrixone.Main;
import com.redrixone.menus.Shop;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class ShopListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Material getType = item.getType();
        if (inventory.getName().equals("Shop")) {
            event.setCancelled(true);
            switch (getType) {
                case DIAMOND_SWORD:
                    Shop.apriGenerale(player);
                    break;
                case POTION:
                    player.sendMessage("§cSoon...");
                    break;
                case ENCHANTED_BOOK:
                    Shop.apriLibri(player);
                    break;
            }
        }

        if (inventory.getName().equals("Generale")) {
            Economy economy = Main.getEconomy();
            int balance = (int) Main.getEconomy().getBalance(player);
            event.setCancelled(true);
            switch (getType) {
                case DIAMOND_SWORD:
                    if (balance < 800) {
                        player.sendMessage("§cNon hai abbastanza soldi!");
                    } else {
                        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
                        EconomyResponse response = economy.withdrawPlayer(player, 800.0);
                    }
                    break;
                case GOLDEN_APPLE:
                    if (balance < 1900) {
                        player.sendMessage("§cNon hai abbastanza soldi!");
                    } else {
                        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1));
                        EconomyResponse response = economy.withdrawPlayer(player, 1900.0);
                    }
                    break;
                case EXP_BOTTLE:
                    if (balance < 2000) {
                        player.sendMessage("§cNon hai abbastanza soldi!");
                    } else {
                        player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 32));
                        EconomyResponse response = economy.withdrawPlayer(player, 1900.0);
                    }
                    break;
                case ARROW:
                    if (item.getItemMeta().getDisplayName().contains("Indietro")) {
                        Shop.apriSezioni(player);
                        break;
                    }
            }
        }

        if (inventory.getName().equals("Pozioni")) {
            event.setCancelled(true);
            ItemStack itemStack = new ItemStack(Material.POTION,1 , (short) 8226);
            switch (getType) {
                case POTION:
                    break;
            }
        }

        if (inventory.getName().equals("Enchant")) {
            event.setCancelled(true);
            Economy economy = Main.getEconomy();
            int balance = (int) Main.getEconomy().getBalance(player);
            switch (getType) {
                case ENCHANTED_BOOK:
                    if (balance < 4500) {
                        player.sendMessage("§cNon hai abbastanza soldi!");
                    } else {
                        if (item.getItemMeta().getDisplayName().equals("§eSharpness IV")) {
                            player.getInventory().addItem(createLibri(Enchantment.DAMAGE_ALL, 4));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        } else if (item.getItemMeta().getDisplayName().equals("§eProtection IV")) {
                            player.getInventory().addItem(createLibri(Enchantment.PROTECTION_ENVIRONMENTAL, 4));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        }
                        else if (item.getItemMeta().getDisplayName().equals("§eUnbreaking III")) {
                            player.getInventory().addItem(createLibri(Enchantment.DAMAGE_UNDEAD, 3));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        } else if (item.getItemMeta().getDisplayName().equals("§eFire Aspect I")) {
                            player.getInventory().addItem(createLibri(Enchantment.FIRE_ASPECT, 1));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        } else if (item.getItemMeta().getDisplayName().equals("§eInfinity")) {
                            player.getInventory().addItem(createLibri(Enchantment.ARROW_INFINITE, 1));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        } else if (item.getItemMeta().getDisplayName().equals("§eFeather Falling IV")) {
                            player.getInventory().addItem(createLibri(Enchantment.PROTECTION_FALL, 4));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        } else if (item.getItemMeta().getDisplayName().equals("§ePunch I")) {
                            player.getInventory().addItem(createLibri(Enchantment.ARROW_KNOCKBACK, 1));
                            EconomyResponse response = economy.withdrawPlayer(player, 4500.0);
                        }
                    }

                    break;
                case ARROW:
                    if (item.getItemMeta().getDisplayName().contains("Indietro")) {
                        Shop.apriSezioni(player);
                        break;
                    }
            }
        }
    }

    private ItemStack createLibri(Enchantment enchant, int level) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(enchant, level, true);
        book.setItemMeta(meta);

        return book;
    }

}
