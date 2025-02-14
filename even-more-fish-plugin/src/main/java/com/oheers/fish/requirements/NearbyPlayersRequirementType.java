package com.Austin-W-Music.fish.requirements;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.requirement.RequirementContext;
import com.Austin-W-Music.fish.api.requirement.RequirementType;
import com.Austin-W-Music.fish.config.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NearbyPlayersRequirementType implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementContext context, @NotNull List<String> values) {
        Player player = context.getPlayer();
        if (player == null) {
            String configLocation = context.getConfigPath();
            if (configLocation == null) {
                configLocation = "N/A";
            }
            DeepFishing.getInstance().getLogger().warning("Could not find a valid player for " + configLocation + ", returning false by " +
                    "default. The player may not have been given a fish if you see this message multiple times.");
            return false;
        }
        int range = MainConfig.getInstance().getNearbyPlayersRequirementRange();
        long nearbyPlayers = context.getPlayer().getNearbyEntities(range, range, range).stream().filter(entity -> entity instanceof Player).count();
        for (String value : values) {
            int nearbyRequirement;
            try {
                nearbyRequirement = Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                DeepFishing.getInstance().getLogger().severe(value + " is not a valid integer");
                return false;
            }
            if (nearbyPlayers >= nearbyRequirement) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "NEARBY-PLAYERS";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DeepFishing.getInstance();
    }

}
