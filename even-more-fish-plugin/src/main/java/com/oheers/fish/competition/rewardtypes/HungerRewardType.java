package com.Austin-W-Music.fish.competition.rewardtypes;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.reward.RewardType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class HungerRewardType implements RewardType {


    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, Location hookLocation) {
        int rewardHunger;
        try {
            rewardHunger = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            DeepFishing.getInstance().getLogger().warning("Invalid number specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        player.setFoodLevel(player.getFoodLevel() + rewardHunger);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "HUNGER";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DevAustin";
    }

    @Override
    public @NotNull JavaPlugin getPlugin() {
        return DeepFishing.getInstance();
    }
}
