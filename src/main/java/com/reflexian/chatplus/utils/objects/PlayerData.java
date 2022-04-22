package com.reflexian.chatplus.utils.objects;

import com.reflexian.chatplus.ChatPlus;
import com.reflexian.chatplus.utils.ChatConfiguration;
import com.reflexian.chatplus.utils.DatabaseIntegration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor @Getter @Setter
public class PlayerData {

    private static Map<UUID,PlayerData> playerDataMap = new HashMap<>();

    private String channel;
    private final Player player;

    public PlayerData(Player player) {
        this.channel = ChatConfiguration.getInstance().getDefaultChannel();
        this.player = player;
        playerDataMap.put(player.getUniqueId(),this);

        CompletableFuture.runAsync(()->{
            try {
                PreparedStatement preparedStatement = DatabaseIntegration.getConnection().prepareStatement("SELECT * FROM player_data WHERE uuid = ?");
                preparedStatement.setString(1,player.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                channel=resultSet.getString("channel");
                ChatConfiguration.getInstance().getPlayerIgnores().put(player.getUniqueId(),new HashSet<UUID>(Arrays.stream(resultSet.getString("ignores").split(",")).map(UUID::fromString).collect(Collectors.toList())));
            } catch (SQLException e) {
                channel=ChatConfiguration.getInstance().getDefaultChannel();
                e.printStackTrace();
            }
        });
    }

    public void save() {
        try {
            PreparedStatement preparedStatement = DatabaseIntegration.getConnection().prepareStatement("UPDATE player_data SET channel = ? AND ignores=? WHERE uuid = ?");
            preparedStatement.setString(1,channel);
            preparedStatement.setString(2, ChatConfiguration.getInstance().getPlayerIgnores().get(player.getUniqueId()).stream().map(UUID::toString).collect(Collectors.joining(",")));
            preparedStatement.setString(3,player.getUniqueId().toString());
            ChatConfiguration.getInstance().getPlayerIgnores().remove(player.getUniqueId());
            playerDataMap.remove(player.getUniqueId());
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            ChatPlus.getInstance().getLogger().severe("Failed to save player data for " + player.getUniqueId().toString()+"!");
        }
    }

    public static PlayerData get(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }



}
