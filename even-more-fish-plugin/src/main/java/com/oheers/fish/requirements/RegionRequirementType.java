package com.Austin-W-Music.fish.requirements;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.FishUtils;
import com.Austin-W-Music.fish.api.requirement.RequirementContext;
import com.Austin-W-Music.fish.api.requirement.RequirementType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RegionRequirementType implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementContext context, @NotNull List<String> values) {
        return FishUtils.checkRegion(context.getLocation(), values);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "REGION";
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
