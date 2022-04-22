package com.reflexian.chatplus.utils;

import com.reflexian.chatplus.ChatPlus;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseIntegration {

    @Getter private static DatabaseIntegration instance = new DatabaseIntegration();

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    private static Connection connection;

    private DatabaseIntegration() {
        FileConfiguration fileConfiguration = ChatPlus.getInstance().getConfig();
        instance=this;
        host=fileConfiguration.getString("db.host", "127.0.0.1");
        port=fileConfiguration.getInt("db.port", 3306);
        username=fileConfiguration.getString("db.username", "root");
        password=fileConfiguration.getString("db.password", "password");
        database=fileConfiguration.getString("db.database", "database");
        connection=init();
    }

    private static Connection init() {
        try {
            connection= DriverManager.getConnection("jdbc:mysql://" + instance.host + ":"+ instance.port +"/" + instance.database,instance.username,instance.password);
            getConnection().prepareStatement("create table if not exists player_data\n" +
                    "(\n" +
                    "    uuid    varchar(100)  null,\n" +
                    "    ignores varchar(4000) null,\n" +
                    "    channel varchar(100)  null\n" +
                    ");\n" +
                    "\n").executeUpdate();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            ChatPlus.getInstance().onDisable();
        }
        return null;
    }

    @SneakyThrows
    public static Connection getConnection() {
        if (connection.isClosed())return init();
        return connection;
    }
}
