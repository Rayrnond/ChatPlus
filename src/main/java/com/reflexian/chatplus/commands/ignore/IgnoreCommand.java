package com.reflexian.chatplus.commands.ignore;

import com.reflexian.chatplus.utils.ChatConfiguration;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "ignore")
public class IgnoreCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length!=1) {
            player.sendMessage("§cUsage: /ignore <player>");
            return true;
        } else if (Bukkit.getPlayer(args[0])==null) {
            player.sendMessage("§cPlayer not found! They need to be online.");
            return true;
        }

        ChatConfiguration chatConfiguration = ChatConfiguration.getInstance();
        Player target = Bukkit.getPlayer(args[0]);
        if (chatConfiguration.getPlayerIgnores().getOrDefault(player.getUniqueId(),new java.util.HashSet<>()).contains(target.getUniqueId())) {
            chatConfiguration.getPlayerIgnores().getOrDefault(player.getUniqueId(),new java.util.HashSet<>()).remove(target.getUniqueId());
            player.sendMessage("§aYou are no longer ignoring " + target.getName()+"!");
        } else {

            if (!chatConfiguration.getPlayerIgnores().containsKey(player.getUniqueId())) {
                chatConfiguration.getPlayerIgnores().put(player.getUniqueId(),new java.util.HashSet<>());
            }
            chatConfiguration.getPlayerIgnores().get(player.getUniqueId()).add(target.getUniqueId());
            player.sendMessage("§aYou are now ignoring " + target.getName()+"!");
        }
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }
}
