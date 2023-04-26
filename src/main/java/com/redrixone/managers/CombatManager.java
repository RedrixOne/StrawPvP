package com.redrixone.managers;

import com.redrixone.Main;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class CombatManager {

    private HashMap<UUID, Boolean> isCombat = new HashMap<>();

    public void setInCombat(UUID playerUUID) {
        isCombat.put(playerUUID, true);
    }

    public void setOutOfCombat(UUID playerUUID) {
        isCombat.remove(playerUUID);
    }

    public boolean isInCombat(UUID playerUUID) {
        return isCombat.getOrDefault(playerUUID, false);
    }

}
