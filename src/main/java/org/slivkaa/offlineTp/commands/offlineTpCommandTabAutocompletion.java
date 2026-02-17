package org.slivkaa.offlineTp.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class offlineTpCommandTabAutocompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> autoCompletionList = new ArrayList<>();

        if (args.length == 1){
            OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
            for (OfflinePlayer offPlayer : allPlayers){
                if (offPlayer.hasPlayedBefore()){
                    autoCompletionList.add(offPlayer.getName());
                }
            }
        } else if (args.length == 2) {
            List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            autoCompletionList.addAll(onlinePlayers);
        }


        return autoCompletionList;
    }
}
