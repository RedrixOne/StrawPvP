package com.redrixone.commands.sottocomandi.kit;

import com.redrixone.api.Stack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class OttieniKit {

    private static final Map<String, Long> cooldownsDef = new HashMap<>();
    private static final Map<String, Long> cooldownsVip = new HashMap<>();



    public static void kitStarter(CommandSender sender) {
        Player player = (Player) sender;
        long tempoCorrente = System.currentTimeMillis();
        long ultimoUtilizzo = cooldownsDef.getOrDefault(player.getName(), 0L);
        long tempoTrascorso = tempoCorrente - ultimoUtilizzo;
        long tempoRimanente = (10000 - tempoTrascorso) / 1000;

        if (tempoCorrente - ultimoUtilizzo < 10000) {
            sender.sendMessage("§6Devi attendere ancora §e" + tempoRimanente + " §6secondi");
        } else {
            Stack.kitStarter(player);
            cooldownsDef.put(player.getName(), System.currentTimeMillis());
        }
    }

    public static void kitVip(CommandSender sender) {
        Player player = (Player) sender;
        if (sender.hasPermission("kit.admin")) {
            Stack.kitVip(player);
        } else{
            long tempoCorrente = System.currentTimeMillis();
            long ultimoUtilizzo = cooldownsVip.getOrDefault(player.getName(), 0L);
            long tempoTrascorso = tempoCorrente - ultimoUtilizzo;
            long tempoRimanente = (21600000  - tempoTrascorso);

            if (tempoCorrente - ultimoUtilizzo < 21600000) {
                long minuti = tempoRimanente / 60000;
                long secondi = (tempoRimanente % 60000) / 1000;
                long ore = tempoRimanente / 3600000;
                if (ore < 1) {
                    sender.sendMessage("§6Devi attendere ancora §e" + minuti + " §6minuti");
                } else if (minuti < 1) {
                    sender.sendMessage("§6Devi attendere ancora §e" + secondi + " §6secondi");
                } else {
                    sender.sendMessage("§6Devi attendere ancora §e" + ore + " §6ore");
                }
            } else {
                Stack.kitVip(player);
                cooldownsVip.put(player.getName(), System.currentTimeMillis());
            }
        }
    }
}
