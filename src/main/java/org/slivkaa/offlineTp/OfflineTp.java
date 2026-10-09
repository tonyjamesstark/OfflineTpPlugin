package org.slivkaa.offlineTp;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import org.slivkaa.offlineTp.commands.offlineTpCommand;
import org.slivkaa.offlineTp.commands.offlineTpCommandTabAutocompletion;
import org.slivkaa.offlineTp.commands.reloadCommand;
import org.slivkaa.offlineTp.listeners.onJoinListener;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class OfflineTp extends JavaPlugin {
    private static DataManager data;
    private static DataManager config;
    private static final Map<UUID, String> knownNames = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        data = new DataManager(this, "tpqueue.yml");
        config = new DataManager(this, "config.yml");
        getCommand("offlinetp").setExecutor(new offlineTpCommand());
        getCommand("offlinetp-reload").setExecutor(new reloadCommand());
        getCommand("offlinetp").setTabCompleter(new offlineTpCommandTabAutocompletion());
        getServer().getPluginManager().registerEvents(new onJoinListener(), this);
        for (Player player : Bukkit.getOnlinePlayers()){
            RememberName(player);
        }
        // OfflinePlayer.getName() reads the player's data file, which takes minutes for a large playerdata folder
        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()){
                String name = player.getName();
                if (name != null){
                    knownNames.putIfAbsent(player.getUniqueId(), name);
                }
            }
        });
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
        String name = target.getName();
        String locStr = LocationToString(loc);
        data.getConfig().set(name, locStr);
        data.saveConfig();
    }

    public static Location GetNewOfflinePlayerLocation(OfflinePlayer target){
        String loc = data.getConfig().getString(target.getName());
        if (loc == null){
            return null;
        }
        return StringToLocation(loc);
    }

    public static Location GetCurrentOrQueuedLocation(OfflinePlayer target){
        Location queued = GetNewOfflinePlayerLocation(target);
        return queued != null ? queued : target.getLocation();
    }

    public static void RememberName(Player player){
        knownNames.put(player.getUniqueId(), player.getName());
    }

    public static Collection<String> KnownNames(){
        return knownNames.values();
    }

    // Local lookup only: Bukkit.getOfflinePlayer(String) asks Mojang on the main thread for unknown names
    public static OfflinePlayer FindKnownPlayer(String name){
        Player online = Bukkit.getPlayerExact(name);
        if (online != null){
            return online;
        }
        for (Map.Entry<UUID, String> known : knownNames.entrySet()){
            if (name.equalsIgnoreCase(known.getValue())){
                return Bukkit.getOfflinePlayer(known.getKey());
            }
        }
        return null;
    }

    public static Location GetNewOfflinePlayerLocationAndRemove(OfflinePlayer target){
        Location loc = StringToLocation(data.getConfig().getString(target.getName()));
        data.getConfig().set(target.getName(), null);
        data.saveConfig();
        return loc;
    }

    public static DataManager getDataFile() {return data;}
    public static DataManager getConfigFile() {return config;}
    public static String getColorText(String path){return ChatColor.translateAlternateColorCodes('&', config.getConfig().getString(path));}
}
