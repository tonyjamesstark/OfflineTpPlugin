package org.slivkaa.offlineTp;

import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.slivkaa.offlineTp.commands.offlineTpCommand;
import org.slivkaa.offlineTp.commands.offlineTpCommandTabAutocompletion;
import org.slivkaa.offlineTp.commands.reloadCommand;
import org.slivkaa.offlineTp.listeners.onJoinListener;

public final class OfflineTp extends JavaPlugin {
    private static DataManager data;
    private static DataManager config;

    @Override
    public void onEnable() {
        data = new DataManager(this, "tpqueue");
        config = new DataManager(this, "config");
        data.saveDefaultConfig();
        config.saveDefaultConfig();
        getCommand("offlinetp").setExecutor(new offlineTpCommand());
        getCommand("offlinetp-reload").setExecutor(new reloadCommand());
        getCommand("offlinetp").setTabCompleter(new offlineTpCommandTabAutocompletion());
        getServer().getPluginManager().registerEvents(new onJoinListener(), this);
    }

    @Override
    public void onDisable() {
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
