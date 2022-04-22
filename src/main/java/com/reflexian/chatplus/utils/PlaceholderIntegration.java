package com.reflexian.chatplus.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderIntegration extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "chatplus";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Reflexian.com";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        // e.g chatplus_params
        return "";
    }
}
