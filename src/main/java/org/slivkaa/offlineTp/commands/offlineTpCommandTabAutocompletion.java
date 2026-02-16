package org.slivkaa.offlineTp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class offlineTpCommandTabAutocompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> autoCompletionList = new ArrayList<>();

        if (args.length == 1){
            autoCompletionList.add("Java");
            autoCompletionList.add("Offlinetp plugin");
            autoCompletionList.add("Slivkaa");
        }

        return autoCompletionList;
    }
}
