package com.redrixone.menus;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.redrixone.utils.*;

import java.util.ArrayList;

public class Menu {

    public static void menu(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Aiuto");


        ItemStack sito = new ItemStack(Material.ARROW);

        ItemMeta sitoMeta = sito.getItemMeta();
        sitoMeta.setDisplayName("§eSito");

        ArrayList<String> sitolore = new ArrayList<>();
        sitolore.add(" ");
        sitolore.add("§7Click-Destro");

        sitoMeta.setLore(sitolore);
        sito.setItemMeta(sitoMeta);




        ItemStack forum = new ItemStack(Material.BOW);

        ItemMeta forummeta = forum.getItemMeta();
        forummeta.setDisplayName("§eForum");

        ArrayList<String> forumlore = new ArrayList<>();
        forumlore.add(" ");
        forumlore.add("§7Click-Destro");

        forummeta.setLore(forumlore);
        forum.setItemMeta(forummeta);





        ItemStack store = new ItemStack(Material.DIAMOND);

        ItemMeta storemeta = store.getItemMeta();
        storemeta.setDisplayName("§eStore");

        ArrayList<String> storelore = new ArrayList<>();
        storelore.add(" ");
        storelore.add("§7Click-Destro");

        storemeta.setLore(storelore);
        store.setItemMeta(storemeta);


        gui.setItem(12, forum);
        gui.setItem(13, sito);
        gui.setItem(14, store);


        player.openInventory(gui);
    }

}
