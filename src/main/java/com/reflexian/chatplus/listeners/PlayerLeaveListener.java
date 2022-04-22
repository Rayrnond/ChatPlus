package com.reflexian.chatplus.listeners;

import com.reflexian.chatplus.utils.objects.PlayerData;
import com.reflexian.rapi.api.annotation.Registrar;
import org.bukkit.event.Listener;

@Registrar(type = Registrar.Type.LISTENER)
public class PlayerLeaveListener implements Listener {
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        PlayerData.get(event.getPlayer()).save();
    }

}
