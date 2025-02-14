package com.Austin-W-Music.fish.requirements;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.requirement.RequirementContext;
import com.Austin-W-Music.fish.api.requirement.RequirementType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.List;

// TODO same as InGameTimeRequirementType
public class IRLTimeRequirementType implements RequirementType {

    @Override
    public boolean checkRequirement(@NotNull RequirementContext context, @NotNull List<String> values) {
        long currentTime = (Instant.now().getEpochSecond() / 60) % 1440 + 60;
        for (String value : values) {
            String[] split = value.split("-");
            int minTime;
            int maxTime;
            try {
                minTime = getDayMinute(split[0], 0);
                maxTime = getDayMinute(split[1], 1440);
            } catch (ArrayIndexOutOfBoundsException exception) {
                DeepFishing.getInstance().getLogger().severe(value + " is not a valid real time format. Using the defaults.");
                minTime = 0;
                maxTime = 1440;
            }
            if (currentTime >= minTime && currentTime < maxTime) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "IRL-TIME";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DevAustin";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return DeepFishing.getInstance();
    }

    /**
     * Splits the HHMM format by ":" and multiplies the first element by 60 before adding it to the second element.
     *
     * @param HHMMFormat The time in the format of HH:MM.
     * @param fallback   The number to be used in case the HHMM format has not been followed correctly and the minute could
     *                   not be fetched.
     * @return The number of minutes that will have passed in the day by the time the clock ticks round to match the HHMM
     * format. If the HHMM format is incorrect however, the fallback will be returned.
     */
    private int getDayMinute(@NotNull final String HHMMFormat, final int fallback) {
        String[] time = HHMMFormat.split(":");
        try {
            return (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]);
        } catch (IndexOutOfBoundsException | NumberFormatException exception) {
            DeepFishing.getInstance().getLogger().severe("FATAL error reading " + HHMMFormat + ", resorting to default value of " + fallback);
            return fallback;
        }
    }

}
