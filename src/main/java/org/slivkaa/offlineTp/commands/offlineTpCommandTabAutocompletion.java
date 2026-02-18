package org.slivkaa.offlineTp.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

// half with ai ,.,

public class offlineTpCommandTabAutocompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> autoCompletionList = new ArrayList<>();

        if ((args.length == 1) || (args.length == 2)){
            OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
            for (OfflinePlayer offPlayer : allPlayers){
                if (offPlayer.hasPlayedBefore()){
                    autoCompletionList.add(offPlayer.getName());
                }
            }
        }


        return autoCompletionList;
    }
}
