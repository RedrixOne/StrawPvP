package com.redrixone.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessages implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player morto = event.getEntity();
        String nomeMorto = ChatColor.RED + morto.getName();
        Player assassino = morto.getKiller();
        if (assassino != null) {
            String nomeAssassino = ChatColor.GREEN + assassino.getName();
            event.setDeathMessage(nomeMorto + " §eè stato ucciso da " + nomeAssassino);
        } else {
            event.setDeathMessage(null);
        }
    }

}
