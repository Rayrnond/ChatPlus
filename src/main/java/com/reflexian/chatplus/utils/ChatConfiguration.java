package com.reflexian.chatplus.utils;

import com.reflexian.chatplus.ChatPlus;
import com.reflexian.chatplus.utils.objects.Chat;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

@Getter
public class ChatConfiguration {

    @Getter private static ChatConfiguration instance = new ChatConfiguration();

    private final String defaultChannel;
    private final Set<Chat> chats=new HashSet<>();
    private final boolean isWhisperEnabled;
    private final Map<UUID,HashSet<UUID>> playerIgnores=new HashMap<>();

    private ChatConfiguration() {
        System.out.println("test");
        FileConfiguration fileConfiguration = ChatPlus.getInstance().getConfig();
        instance=this;
        defaultChannel = fileConfiguration.getString("default-channel","global");
        isWhisperEnabled = fileConfiguration.getBoolean("whisper-chat-enabled",true);
        for (String channels : fileConfiguration.getConfigurationSection("channels").getKeys(false)) {
            chats.add(Chat.from("channels."+channels));
            System.out.println("Added " + channels);
        }
    }

}
