package com.reflexian.chatplus;

import com.reflexian.rapi.RAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatPlus extends JavaPlugin {

    @Getter private static ChatPlus instance;
    private RAPI rapi;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        rapi= new RAPI(this);
        rapi.init();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI")!=null) {
            try {
                Class<?> clazz = Class.forName("com.reflexian.chatplus.utils.PlaceholderIntegration");
                Object i = clazz.newInstance();
                clazz.getMethod("register").invoke(i);
            } catch (Exception e) {
                getLogger().severe("Could not load PlaceholderAPI integration!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {}
}
