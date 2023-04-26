package com.redrixone.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.redrixone.utils.ItemBuilder.*;

public class Stack {



    public static void kitStarter(Player player) {
        setItem(player, createItem(Material.IRON_SWORD, "§6Spada §ebase", Enchantment.DAMAGE_ALL, 4, 1), 0);
        setItem(player, createItem(Material.BOW, "§6Arco §ebase", Enchantment.ARROW_DAMAGE, 3, 1), 1);
        setItem(player, createItem(Material.GOLDEN_APPLE, "§eMele", null, 0, 8), 2);
        setItem(player, createItem(Material.ARROW, "§6Frecce", null, 0, 16), 9);
        setItem(player, createItem(Material.DIAMOND_BOOTS, "§6Gambali §ebase", null, 0, 1), 36);
        setItem(player, createItem(Material.DIAMOND_LEGGINGS, "§6Pantaloni §ebase", null, 0, 1), 37);
        setItem(player, createItem(Material.DIAMOND_CHESTPLATE, "§6Corpetto §ebase", null, 0, 1), 38);
        setItem(player, createItem(Material.DIAMOND_HELMET, "§6Elmo §ebase", null, 0, 1), 39);
    }

    public static void kitVip(Player player) {
        addItem(player, createItem(Material.DIAMOND_SWORD, "§6Spada §evip", Enchantment.DAMAGE_ALL, 3, 1));
        addItem(player, createItem(Material.BOW, "§6Arco §evip", Enchantment.ARROW_DAMAGE, 4, 1));
        addItem(player, createItem(Material.ARROW, "§6Frecce", null, 1, 1));
        addItem(player, createItem(Material.DIAMOND_BOOTS, "§6Gambali §evip", Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1));
        addItem(player, createItem(Material.DIAMOND_LEGGINGS, "§6Pantaloni §evip", Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1));
        addItem(player, createItem(Material.DIAMOND_CHESTPLATE, "§6Corpetto §evip", Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1));
        addItem(player, createItem(Material.DIAMOND_HELMET, "§6Elmo §evip", Enchantment.PROTECTION_ENVIRONMENTAL, 2, 1));

        ItemStack mele = new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1);
        ItemMeta meleMeta = mele.getItemMeta();
        meleMeta.setDisplayName("§eMele");
        mele.setItemMeta(meleMeta);
        player.sendMessage("§6Hai ricevuto il kit §eVIP");
        player.getInventory().addItem(mele);
    }

}
