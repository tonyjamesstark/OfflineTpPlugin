package org.slivkaa.offlineTp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.slivkaa.offlineTp.OfflineTp;

public class reloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OfflineTp.getConfigFile().reloadConfig();
        OfflineTp.getDataFile().reloadConfig();
        return true;
    }
}
