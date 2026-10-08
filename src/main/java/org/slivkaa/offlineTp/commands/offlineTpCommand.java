package org.slivkaa.offlineTp.commands;

import org.bukkit.Location;
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
        if (args.length > 2){
            return false;
        }

        OfflinePlayer target = OfflineTp.FindKnownPlayer(args[0]);
        if (target == null){
            sendUnknownPlayer(sender, args[0]);
            return true;
        }

        if (args.length == 1){
            if (!perm1){sender.sendMessage(OfflineTp.getColorText("messages.error-no-permission")); return true;}
            Location destination = OfflineTp.GetCurrentOrQueuedLocation(target);
            if (!isLoaded(destination)){
                sendUnknownWorld(sender, target);
                return true;
            }
            Player player = (Player) sender;
            player.teleport(destination);
            sender.sendMessage(OfflineTp.getColorText("messages.ran-with-1-player")
                    .replace("%player%", target.getName())
                    .replace("%me%", player.getDisplayName()));
        } else {
            if (!perm2){sender.sendMessage(OfflineTp.getColorText("messages.error-no-permission")); return true;}
            OfflinePlayer otherTarget = OfflineTp.FindKnownPlayer(args[1]);
            if (otherTarget == null){
                sendUnknownPlayer(sender, args[1]);
                return true;
            }

            Location destination = OfflineTp.GetCurrentOrQueuedLocation(otherTarget);
            if (!isLoaded(destination)){
                sendUnknownWorld(sender, otherTarget);
                return true;
            }
            if (target.isOnline()) {
                target.getPlayer().teleport(destination);
            } else {
                OfflineTp.SetNewOfflinePlayerLocation(target, destination);
            }
            sender.sendMessage(OfflineTp.getColorText("messages.ran-with-2-players")
                    .replace("%player1%", target.getName())
                    .replace("%player2%", otherTarget.getName()));
        }

        return true;
    }

    private boolean isLoaded(Location loc){
        return loc != null && loc.isWorldLoaded();
    }

    private void sendUnknownWorld(CommandSender sender, OfflinePlayer player){
        sender.sendMessage(OfflineTp.getColorText("messages.error-unknown-world").replace("%player%", player.getName()));
    }

    private void sendUnknownPlayer(CommandSender sender, String name){
        sender.sendMessage(OfflineTp.getColorText("messages.error-unknown-player").replace("%player%", name));
    }
}
