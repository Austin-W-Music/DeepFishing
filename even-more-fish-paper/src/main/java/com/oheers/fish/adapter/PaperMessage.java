package com.oheers.fish.adapter;

import com.oheers.fish.api.adapter.AbstractMessage;
import com.oheers.fish.api.adapter.PlatformAdapter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;

public class PaperMessage extends AbstractMessage {

    private static final LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
            .hexColors()
            .character('&')
            .build();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    protected PaperMessage(@NotNull String message, @NotNull PlatformAdapter platformAdapter) {
        super(message, platformAdapter);
    }

    protected PaperMessage(@NotNull List<String> messageList, @NotNull PlatformAdapter platformAdapter) {
        super(messageList, platformAdapter);
    }

    @Override
    public String formatColours(@NotNull String message) {
        String checkStr = ChatColor.translateAlternateColorCodes('&', message);
        // If the message contains legacy, convert to MiniMessage
        if (checkStr.contains("§")) {
            Component legacyComponent = legacySerializer.deserialize(message);
            message = miniMessage.serialize(legacyComponent);
        }
        return message;
    }

    @Override
    public void broadcast() {
        send(Bukkit.getConsoleSender());
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

    @Override
    public void send(@NotNull CommandSender target) {
        if (getRawMessage().isEmpty() || silentCheck()) {
            return;
        }

        String originalMessage = getRawMessage();

        if (target instanceof Player player) {
            setPlayer(player);
        }

        target.sendMessage(getComponentMessage());

        setMessage(originalMessage);
    }

    public Component getComponentMessage() {
        return miniMessage.deserialize(getRawMessage());
    }

    @Override
    public String getLegacyMessage() {
        String legacyAmpersandMessage = legacySerializer.serialize(getComponentMessage());
        return ChatColor.translateAlternateColorCodes('&', legacyAmpersandMessage);
    }

    @Override
    public void formatPlaceholderAPI() {
        if (!isPAPIEnabled()) {
            return;
        }
        String message = getRawMessage();
        Matcher matcher = PlaceholderAPI.getPlaceholderPattern().matcher(message);
        while (matcher.find()) {
            // Find matched String
            String matched = matcher.group();
            // Convert to Legacy Component and into a MiniMessage String
            String parsed = formatColours(PlaceholderAPI.setPlaceholders(getRelevantPlayer(), matched));
            // Escape matched String so we don't have issues
            String safeMatched = Matcher.quoteReplacement(matched);
            // Replace all instances of the matched String with the parsed placeholder.
            message = message.replaceAll(safeMatched, parsed);
        }
        setMessage(message);
    }

}
