package com.reflexian.chatplus.listeners;

import com.reflexian.chatplus.utils.objects.PlayerData;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Registrar(type = Registrar.Type.LISTENER)
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new PlayerData(event.getPlayer());
    }
}
