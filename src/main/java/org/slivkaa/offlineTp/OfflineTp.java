package org.slivkaa.offlineTp;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.slivkaa.offlineTp.commands.offlineTpCommand;
import org.slivkaa.offlineTp.commands.offlineTpCommandTabAutocompletion;
import org.slivkaa.offlineTp.commands.reloadCommand;
import org.slivkaa.offlineTp.listeners.onJoinListener;

public final class OfflineTp extends JavaPlugin {
    private static DataManager data;
    private static DataManager config;

    @Override
    public void onEnable() {
        data = new DataManager(this, "tpqueue.yml");
        config = new DataManager(this, "config.yml");
        getCommand("offlinetp").setExecutor(new offlineTpCommand());
        getCommand("offlinetp-reload").setExecutor(new reloadCommand());
        getCommand("offlinetp").setTabCompleter(new offlineTpCommandTabAutocompletion());
        getServer().getPluginManager().registerEvents(new onJoinListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("See ya later!");
    }

    // Next 2 methods are credited to Gemini AI
    public static String LocationToString(Location loc){
        return loc.getWorld().getName() + "/" + loc.getX() + "/" + loc.getY() + "/" + loc.getZ() + "/" + loc.getYaw() + "/" + loc.getPitch();
    }
    public static Location StringToLocation(String str){
        String[] p = str.split("/");
        return new Location(
                Bukkit.getWorld(p[0]),    // World
                Double.parseDouble(p[1]), // X
                Double.parseDouble(p[2]), // Y
                Double.parseDouble(p[3]), // Z
                Float.parseFloat(p[4]),   // Yaw
                Float.parseFloat(p[5])    // Pitch
        );
    }
    public static void SetNewOfflinePlayerLocation(OfflinePlayer target, Location loc){
        if (target.getName() != null){
            data.getConfig().set(target.getName(), null);
        }
        data.getConfig().set(target.getUniqueId().toString(), LocationToString(loc));
        data.saveConfig();
    }

    // Queues written before 2.0.0 are keyed by player name
    private static String QueueKey(OfflinePlayer target){
        String uuid = target.getUniqueId().toString();
        String name = target.getName();
        if (!data.getConfig().contains(uuid) && name != null && data.getConfig().contains(name)){
            return name;
        }
        return uuid;
    }

    public static Location GetNewOfflinePlayerLocation(OfflinePlayer target){
        String loc = data.getConfig().getString(QueueKey(target));
        if (loc == null){
            return null;
        }
        return StringToLocation(loc);
    }

    public static Location GetCurrentOrQueuedLocation(OfflinePlayer target){
        Location queued = GetNewOfflinePlayerLocation(target);
        return queued != null ? queued : target.getLocation();
    }

    // Local lookup only: Bukkit.getOfflinePlayer(String) asks Mojang on the main thread for unknown names
    public static OfflinePlayer FindKnownPlayer(String name){
        Player online = Bukkit.getPlayerExact(name);
        if (online != null){
            return online;
        }
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()){
            if (name.equalsIgnoreCase(player.getName())){
                return player;
            }
        }
        return null;
    }

    public static Location GetNewOfflinePlayerLocationAndRemove(OfflinePlayer target){
        String key = QueueKey(target);
        String locStr = data.getConfig().getString(key);
        if (locStr == null){
            return null;
        }
        data.getConfig().set(key, null);
        data.saveConfig();
        Location loc = StringToLocation(locStr);
        if (!loc.isWorldLoaded()){
            getPlugin(OfflineTp.class).getLogger().warning("Dropped queued teleport of " + target.getName()
                    + " to " + locStr + ": world is not loaded");
            return null;
        }
        return loc;
    }

    public static DataManager getDataFile() {return data;}
    public static DataManager getConfigFile() {return config;}
    public static String getColorText(String path){return ChatColor.translateAlternateColorCodes('&', config.getConfig().getString(path));}
}
