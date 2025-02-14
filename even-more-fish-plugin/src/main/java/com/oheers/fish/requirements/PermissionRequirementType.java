package com.Austin-W-Music.fish.requirements;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.requirement.RequirementContext;
import com.Austin-W-Music.fish.api.requirement.RequirementType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PermissionRequirementType implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementContext context, @NotNull List<String> values) {
        Player player = context.getPlayer();
        if (player == null) {
            return false;
        }
        for (String value : values) {
            if (player.hasPermission(value)) {
                return true;
            }
        }
        return false;
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
    public @NotNull Plugin getPlugin() {
        return DeepFishing.getInstance();
    }

}
