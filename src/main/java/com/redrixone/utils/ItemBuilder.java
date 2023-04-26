package com.redrixone.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    public static void setItem(Player player, ItemStack item, int slot) {
        ItemStack slotItem = player.getInventory().getItem(slot);
        if (slotItem == null || slotItem.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
            player.getInventory().setItem(slot, item);
        }
    }

    public static void addItem(Player player, ItemStack item) {
        player.getInventory().addItem(item);
    }

    public static ItemStack createItem(Material material, String name, Enchantment enchantment, int level, int amount) {
        ItemStack item = new ItemStack(material, amount);
        if (enchantment != null) {
            if (item.getType() == Material.BOW) {
                item.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            }
            item.addEnchantment(enchantment, level);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

}
