package org.slivkaa.offlineTp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slivkaa.offlineTp.OfflineTp;

public class offlineTpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)){
            sender.sendMessage("ERROR: This command is available to be ran only by players");
            return true;
        }

        if (args.length < 1){
            sender.sendMessage(ChatColor.RED + "ERROR: Excepted at least 1 argument (Player)");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!target.hasPlayedBefore()){
            sender.sendMessage(ChatColor.RED + "ERROR: Player hasn't joined yet");
            return true;
        }

        if (args.length == 1){
            player.teleport(target.getLocation());
        } else if (args.length == 2){
            OfflinePlayer otherTarget = Bukkit.getOfflinePlayer(args[1]);

            if (!otherTarget.hasPlayedBefore()) {
                sender.sendMessage(ChatColor.RED + "ERROR: Player 2 hasn't joined yet");
            } else if (target.isOnline()) {
                target.getPlayer().teleport(otherTarget.getLocation());
            } else{
                OfflineTp.SetNewOfflinePlayerLocation(target, otherTarget.getLocation());
            }
        }

        return true;
    }
}
