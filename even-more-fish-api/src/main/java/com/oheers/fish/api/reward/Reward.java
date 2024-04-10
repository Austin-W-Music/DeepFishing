package com.oheers.fish.api.reward;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Reward {

    private @NotNull String key;
    private @NotNull String value;
    private RewardType rewardType = null;
    private Vector fishVelocity;

    public Reward(@NotNull String identifier) {
        String[] split = identifier.split(":");
        if (split.length < 2) {
            RewardManager.getInstance().getLogger().warning(value + " is not formatted correctly. It won't be given as a reward");
            this.key = "";
            this.value = "";
        } else {
            this.key = split[0];
            this.value = String.join(":", Arrays.copyOfRange(split, 1, split.length));
        }
        for (RewardType rewardType : RewardManager.getInstance().getRegisteredRewardTypes()) {
            if (rewardType.isApplicable(this.key)) {
                this.rewardType = rewardType;
                return;
            }
        }
    }

    public RewardType getRewardType() {
        return this.rewardType;
    }

    public void rewardPlayer(@NotNull Player player, Location hookLocation) {
        if (getRewardType() == null) {
            EMFRewardEvent event = new EMFRewardEvent(this, player, fishVelocity, hookLocation);
            Bukkit.getPluginManager().callEvent(event);
            return;
        }
        getRewardType().doReward(player, this.key, this.value, hookLocation);
    }

    public void setFishVelocity(Vector fishVelocity) {
        this.fishVelocity = fishVelocity;
    }

}
