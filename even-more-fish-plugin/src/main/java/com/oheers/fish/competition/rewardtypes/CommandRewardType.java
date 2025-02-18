package com.Austin-W-Music.fish.competition.rewardtypes;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.reward.RewardType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, Location hookLocation) {
        String inputCommand = value.replace("{player}", player.getName());
        if (DeepFishing.getInstance().isUsingPAPI()) inputCommand = PlaceholderAPI.setPlaceholders(player, inputCommand);
        if (hookLocation != null) {
            inputCommand = inputCommand
                    .replace("{x}", Double.toString(hookLocation.getX()))
                    .replace("{y}", Double.toString(hookLocation.getY()))
                    .replace("{z}", Double.toString(hookLocation.getZ()))
                    .replace("{world}", hookLocation.getWorld().getName());
        }

        // running the command
        String finalCommand = inputCommand;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "COMMAND";
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
