package com.Austin-W-Music.fish.requirements;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.requirement.RequirementContext;
import com.Austin-W-Music.fish.api.requirement.RequirementType;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupRequirementType implements RequirementType {

    private final @NotNull Permission permission;

    public GroupRequirementType(@NotNull Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean checkRequirement(@NotNull RequirementContext context, @NotNull List<String> values) {
        Player player = context.getPlayer();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            if (permission.playerInGroup(player, value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "GROUP";
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
