package org.slivkaa.offlineTp.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slivkaa.offlineTp.DataManager;
import org.slivkaa.offlineTp.OfflineTp;

// no ai

public class onJoinListener implements Listener {
    private final DataManager data = OfflineTp.getDataFile();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        OfflineTp.RememberName(player);
        if (data.getConfig().getKeys(false).contains(player.getName())){
            Location loc = OfflineTp.GetNewOfflinePlayerLocationAndRemove(player);
            player.teleport(loc);
        }
    }
}
