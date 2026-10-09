package org.slivkaa.offlineTp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.slivkaa.offlineTp.OfflineTp;

import java.util.ArrayList;
import java.util.List;

// half with ai ,.,

public class offlineTpCommandTabAutocompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> autoCompletionList = new ArrayList<>();

        if ((args.length == 1) || (args.length == 2)){
            StringUtil.copyPartialMatches(args[args.length - 1], OfflineTp.KnownNames(), autoCompletionList);
        }

        return autoCompletionList;
    }
}
