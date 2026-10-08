package org.slivkaa.offlineTp.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slivkaa.offlineTp.OfflineTp;

// no ai

public class onJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        Location loc = OfflineTp.GetNewOfflinePlayerLocationAndRemove(player);
        if (loc != null){
            player.teleport(loc);
        }
    }
}
