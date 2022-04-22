package com.reflexian.chatplus.utils.objects;

import com.reflexian.chatplus.ChatPlus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@AllArgsConstructor
@Getter
public class Chat {

    private String name;
    private String permission;
    private int area;
    private boolean global;
    private List<String> worlds;

    public static Chat from(String section) {
        FileConfiguration config = ChatPlus.getInstance().getConfig();
        return new Chat(
                config.getString(section+".name"),
                config.getString(section+".permission"),
                config.getInt(section+".area"),
                config.getBoolean(section+".global"),
                config.getStringList(section+".worlds")
        );
    }

}
