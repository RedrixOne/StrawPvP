package com.redrixone.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    public static ItemStack createObject(Material materiale, String nome) {
        ItemStack itemStack = new ItemStack(materiale);
        if (itemStack.getType() == Material.GLASS_BOTTLE)
            itemStack = new ItemStack(Material.POTION, 1, (byte)0);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemStack.getType() == Material.ENCHANTED_BOOK)
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(nome);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack createGeneralObject(Material materiale, String nome) {
        ItemStack itemStack = new ItemStack(materiale);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemStack.getType() == Material.DIAMOND_SWORD) {
            List<String> lore1 = new ArrayList<String>();
            lore1.add("§c");
            lore1.add("§6Prezzo: §e800$");
            itemMeta.setLore(lore1);
        }
        if (itemStack.getType() == Material.GOLDEN_APPLE) {
            itemStack = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
            List<String> lore1 = new ArrayList<String>();
            lore1.add("§c");
            lore1.add("§6Prezzo: §e1900$");
            itemMeta.setLore(lore1);
        }
        if (itemStack.getType() == Material.EXP_BOTTLE) {
            itemStack = new ItemStack(Material.EXP_BOTTLE, 32);
            List<String> lore1 = new ArrayList<String>();
            lore1.add("§c");
            lore1.add("§6Prezzo: §e2000$");
            itemMeta.setLore(lore1);
        }
        itemMeta.setDisplayName(nome);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack creaPozioni (Material materiale, String nome, short tipo) {
        ItemStack potion = new ItemStack(materiale, 1, tipo);
        ItemMeta potionMeta = potion.getItemMeta();
        potionMeta.setDisplayName(nome);
        potion.setItemMeta(potionMeta);

        return potion;
    }

    public static ItemStack creaLibri (String name, Enchantment enchant, int livello, int prezzo) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<String>();
        lore.add("§c");
        lore.add("§6Prezzo: §e" + prezzo + "$");
        meta.setLore(lore);
        book.setItemMeta(meta);

        return book;
    }

    public static void apriSezioni(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Shop");
        inventory.setItem(11, createObject(Material.DIAMOND_SWORD, "§eGenerale"));
        inventory.setItem(13, createObject(Material.GLASS_BOTTLE, "§ePozioni §7[§cSoon..§4]"));
        inventory.setItem(15, createObject(Material.ENCHANTED_BOOK, "§eEnchant"));
        player.openInventory(inventory);
    }

    public static void apriGenerale(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "Generale");
        inventory.setItem(11, createGeneralObject(Material.DIAMOND_SWORD, "§eSpada in §bDiamante"));
        inventory.setItem(13, createGeneralObject(Material.GOLDEN_APPLE, "§eMela d'Oro"));
        inventory.setItem(15, createGeneralObject(Material.EXP_BOTTLE, "§eEXP"));
        inventory.setItem(31, createGeneralObject(Material.ARROW, "§eIndietro"));
        player.openInventory(inventory);
    }

    public static void apriPozioni(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "Pozioni");
        inventory.setItem(11, creaPozioni(Material.POTION, "§eVelocità II", (short) 8226));
        player.openInventory(inventory);
    }

    public static void apriLibri(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "Enchant");
        inventory.setItem(10, creaLibri("§eSharpness IV", Enchantment.DAMAGE_ALL, 4, 4500));
        inventory.setItem(11, creaLibri("§eProtection IV", Enchantment.PROTECTION_ENVIRONMENTAL, 4, 4500));
        inventory.setItem(12, creaLibri("§eUnbreaking III", Enchantment.DAMAGE_UNDEAD, 3, 4500));
        inventory.setItem(13, creaLibri("§eFire Aspect I", Enchantment.FIRE_ASPECT, 1, 4500));
        inventory.setItem(14, creaLibri("§eInfinity", Enchantment.ARROW_INFINITE, 1, 4500));
        inventory.setItem(15, creaLibri("§eFeather Falling IV", Enchantment.PROTECTION_FALL, 4, 4500));
        inventory.setItem(16, creaLibri("§ePunch I", Enchantment.ARROW_KNOCKBACK, 1, 4500));
        inventory.setItem(31, createGeneralObject(Material.ARROW, "§eIndietro"));
        player.openInventory(inventory);
    }

}
