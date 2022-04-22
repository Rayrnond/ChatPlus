package com.reflexian.chatplus.commands.base;

import com.reflexian.chatplus.utils.ChatConfiguration;
import com.reflexian.chatplus.utils.objects.Chat;
import com.reflexian.chatplus.utils.objects.PlayerData;
import com.reflexian.rapi.api.annotation.CommandInfo;
import com.reflexian.rapi.api.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "chatplus", aliases = {"chat","c"}, description = "The main chatplus command")
public class ChatPlusCommand extends Command {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length==0) {
            player.sendMessage("§cUsage: /chat <channel>");
            player.sendMessage("§cAvailable channels: " + ChatConfiguration.getInstance().getChats().stream().map(chat -> (player.hasPermission(chat.getPermission()) ? "§a":"§c") + chat.getName()).collect(Collectors.joining("§7, ")));
            return true;
        }

        Chat chat = ChatConfiguration.getInstance().getChats().stream().filter(e->e.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
        if (chat==null) {
            player.sendMessage("§cChannel not found!");
            return true;
        } else {
            if (!player.hasPermission(chat.getPermission())) {
                player.sendMessage("§cYou don't have permission to use that channel!");
                return true;
            }
            if (PlayerData.get(player).getChannel().equalsIgnoreCase(args[0])) {
                player.sendMessage("§cYou are already in that channel!");
                return true;
            }
            PlayerData.get(player).setChannel(args[0].toLowerCase());
            player.sendMessage("§aYou are now talking in channel §e" + args[0]+"§a!");
        }
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender sender, String[] args) {
        return null;
    }
}
