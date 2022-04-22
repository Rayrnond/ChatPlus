package com.reflexian.chatplus.commands.whisper;

import com.reflexian.chatplus.utils.ChatConfiguration;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInfo(name = "whisper",aliases = {"msg","w"})
public class WhisperCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!ChatConfiguration.getInstance().isWhisperEnabled()) {
            player.sendMessage("§cThat command is not enabled!");
            return true;
        }

        if (args.length<2) {
            player.sendMessage("§cUsage: /whisper <player> <message>");
            return true;
        } else if (Bukkit.getPlayer(args[0])==null) {
            player.sendMessage("§cPlayer not found!");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (ChatConfiguration.getInstance().getPlayerIgnores().getOrDefault(player.getUniqueId(),new java.util.HashSet<>()).contains(target.getUniqueId())) {
            player.sendMessage("§cYou can't whisper to that player, they are ignoring you!");
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }
        player.sendMessage("§7[§6Whisper§7] §f"+player.getName()+" -> "+target.getName()+": "+message.toString());
        target.sendMessage("§7[§6Whisper§7] §f" + player.getName() + ": " + message.toString().trim());
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return null;
    }
}
