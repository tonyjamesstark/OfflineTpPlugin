package org.slivkaa.offlineTp.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slivkaa.offlineTp.OfflineTp;

// If you wonder why this class is so unlogical and stupid and dumb hate me pls, i tried writing ts without ai

public class offlineTpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length < 2){
            sender.sendMessage(OfflineTp.getColorText("messages.error-cant-tp-non-player"));
            return true;
        }
        Player player = (Player) sender;
        boolean perm1 = sender.hasPermission("offlinetp.to-player");
        boolean perm2 = sender.hasPermission("offlinetp.player-to-player");
        if (!perm1 && !perm2){
            sender.sendMessage(OfflineTp.getColorText("messages.error-no-permission"));
            return true;
        }

        if (args.length < 1){
            sender.sendMessage(OfflineTp.getColorText("messages.error-no-args"));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!target.hasPlayedBefore()){
            sender.sendMessage(OfflineTp.getColorText("messages.error-unknown-player")
                    .replace("%player%", target.getName()));
            return true;
        }

        if (args.length == 1){
            if (!perm1){sender.sendMessage(OfflineTp.getColorText("messages.error-no-permission")); return true;}
            player.teleport(target.getLocation());
            sender.sendMessage(OfflineTp.getColorText("messages.ran-with-1-player")
                    .replace("%player%", target.getName())
                    .replace("%me%", player.getDisplayName()));
        } else if (args.length == 2){
            if (!perm2){sender.sendMessage(OfflineTp.getColorText("messages.error-no-permission")); return true;}
            OfflinePlayer otherTarget = Bukkit.getOfflinePlayer(args[1]);

            if (!otherTarget.hasPlayedBefore()) {
                sender.sendMessage(OfflineTp.getColorText("messages.error-unknown-player")
                        .replace("%player%", target.getName()));
            } else{
                if (target.isOnline()) {target.getPlayer().teleport(otherTarget.getLocation());}
                else{OfflineTp.SetNewOfflinePlayerLocation(target, otherTarget.getLocation());}
                    sender.sendMessage(OfflineTp.getColorText("messages.ran-with-2-players")
                            .replace("%player1%", target.getName())
                            .replace("%player2%", otherTarget.getName()));

            }
        }

        return true;
    }
}
