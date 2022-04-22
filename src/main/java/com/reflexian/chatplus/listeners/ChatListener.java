package com.reflexian.chatplus.listeners;

import com.reflexian.chatplus.utils.ChatConfiguration;
import com.reflexian.chatplus.utils.objects.Chat;
import com.reflexian.chatplus.utils.objects.PlayerData;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;

@Registrar(type = Registrar.Type.LISTENER)
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {


        PlayerData playerData = PlayerData.get(event.getPlayer());
        event.getRecipients().removeIf(p->ChatConfiguration.getInstance().getPlayerIgnores().getOrDefault(event.getPlayer().getUniqueId(), new HashSet<>()).contains(p.getUniqueId()));
        Chat chat = ChatConfiguration.getInstance().getChats().stream().filter(c -> c.getName().equalsIgnoreCase(playerData.getChannel())).findFirst().orElse(null);
        if (chat==null) {
            playerData.setChannel("global");
            event.getPlayer().sendMessage("§cYour message failed to send as you are not in a channel.");
            return;
        }

        if (event.getMessage().startsWith("SHOUT ")) {
            event.setMessage(event.getMessage().replaceFirst("SHOUT ", ""));
            return;
        }

        if (chat.isGlobal()) return;
        else if (chat.getWorlds().size()==0&&chat.getArea()==0) {
            event.getRecipients().removeIf(e->!e.getWorld().equals(event.getPlayer().getWorld()));
        }
        else if (chat.getWorlds().size()==0&&chat.getArea()!=0) {
            event.getRecipients().removeIf(e->!e.getWorld().equals(event.getPlayer().getWorld())||e.getLocation().distance(event.getPlayer().getLocation())<=chat.getArea());
        }
        else if (chat.getWorlds().size()!=0) {

            event.getRecipients().removeIf(e->!e.getWorld().equals(event.getPlayer().getWorld()));
        }

    }
}
