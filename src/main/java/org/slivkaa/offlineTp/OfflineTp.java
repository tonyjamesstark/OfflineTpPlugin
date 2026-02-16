package org.slivkaa.offlineTp;

import org.bukkit.plugin.java.JavaPlugin;
import org.slivkaa.offlineTp.commands.offlineTpCommand;
import org.slivkaa.offlineTp.commands.offlineTpCommandTabAutocompletion;

public final class OfflineTp extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("offlinetp").setExecutor(new offlineTpCommand());
        getCommand("offlinetp").setTabCompleter(new offlineTpCommandTabAutocompletion());
    }

    @Override
    public void onDisable() {
    }
}
