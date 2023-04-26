package com.redrixone.events;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class HelpListener implements Listener {


    @EventHandler
    public void OnPlayerClickEvent(InventoryClickEvent e) {


        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("AIUTO")) {
            e.setCancelled(true);
            Material oggetto = e.getCurrentItem().getType();

            switch (oggetto) {
                case ARROW:
                    getClick(player, "§eSito", " ", "§e§nwww.strawlandia.it");
                    break;
                case BOW:
                    getClick(player, "§eForum:", " ", "§e§nstrawlandia.it/forum");
                    break;
                case DIAMOND:
                    getClick(player, "§eStore:", " ", "§e§nstore.strawlandia.it");

            }
        }
    }

    public void getClick(Player player, String message1, String message2, String message3) {
        player.closeInventory();
        player.sendMessage(message1);
        player.sendMessage(message2);
        player.sendMessage(message3);
    }
}
