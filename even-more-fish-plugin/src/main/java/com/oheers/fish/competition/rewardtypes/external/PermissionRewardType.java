package com.Austin-W-Music.fish.competition.rewardtypes.external;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.reward.RewardType;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PermissionRewardType implements RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, Location hookLocation) {
        Permission permission = DeepFishing.getInstance().getPermission();
        if (permission != null) {
            permission.playerAdd(player.getPlayer(), value);
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "PERMISSION";
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
