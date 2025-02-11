package com.Austin-W-Music.fish;

import com.Austin-W-Music.fish.config.messages.ConfigMessage;
import com.Austin-W-Music.fish.permissions.AdminPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    private final DeepFishing plugin;
    private final int resourceID;


    public UpdateChecker(final DeepFishing plugin, final int resourceID) {
        this.plugin = plugin;
        this.resourceID = resourceID;
    }

    public String getVersion() {
        try (final Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/simple/0.1/index.php?action=getResource&id=" + resourceID).openStream())) {
            return ((JSONObject) new JSONParser().parse(scanner.nextLine())).get("current_version").toString();
        } catch (Exception ignored) {
            DeepFishing.getInstance().getLogger().warning("DeepFishing failed to check for updates against the spigot website, to check manually go to https://www.spigotmc.org/resources/evenmorefish.91310/updates");
            return plugin.getDescription().getVersion();
        }

    }
}

class UpdateNotify implements Listener {

    @EventHandler
    // informs admins with df.admin permission that the plugin needs updating
    public void playerJoin(PlayerJoinEvent event) {
        if (!DeepFishing.getInstance().isUpdateAvailable()) {
            return;
        }

        if (event.getPlayer().hasPermission(AdminPerms.UPDATE_NOTIFY)) {
            ConfigMessage.ADMIN_UPDATE_AVAILABLE.getMessage().send(event.getPlayer());
        }

    }
}

