package org.slivkaa.offlineTp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class offlineTpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1){
            sender.sendMessage("Needs at least 1 argument");
            return true;
        }
        sender.sendMessage("Hello world with " + args[0] + "!");
        return true;
    }
}
