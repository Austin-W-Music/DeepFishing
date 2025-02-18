package com.Austin-W-Music.fish.competition.strategies;


import com.Austin-W-Music.fish.api.adapter.AbstractMessage;
import com.Austin-W-Music.fish.competition.Competition;
import com.Austin-W-Music.fish.competition.CompetitionEntry;
import com.Austin-W-Music.fish.competition.CompetitionStrategy;
import com.Austin-W-Music.fish.competition.leaderboard.Leaderboard;
import com.Austin-W-Music.fish.config.messages.ConfigMessage;
import com.Austin-W-Music.fish.fishing.items.Fish;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LargestFishStrategy implements CompetitionStrategy {

    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        if (fish.getLength() <= 0) return;

        CompetitionEntry entry = leaderboard.getEntry(fisher.getUniqueId());

        if (entry != null) {
            if (fish.getLength() > entry.getFish().getLength()) {
                leaderboard.removeEntry(entry);
                leaderboard.addEntry(new CompetitionEntry(fisher.getUniqueId(), fish, competition.getCompetitionType()));
            }
        } else {
            leaderboard.addEntry(new CompetitionEntry(fisher.getUniqueId(), fish, competition.getCompetitionType()));
        }
    }

    @Override
    public AbstractMessage getSingleConsoleLeaderboardMessage(@NotNull AbstractMessage message, @NotNull CompetitionEntry entry) {
        Fish fish = entry.getFish();
        message.setRarityColour(fish.getRarity().getColour());
        message.setLength(getDecimalFormat().format(entry.getValue()));
        message.setRarity(fish.getRarity().getDisplayName());
        message.setFishCaught(fish.getDisplayName());
        message.setMessage(ConfigMessage.LEADERBOARD_LARGEST_FISH.getMessage());
        return message;
    }

    @Override
    public AbstractMessage getSinglePlayerLeaderboard(@NotNull AbstractMessage message, @NotNull CompetitionEntry entry) {
        Fish fish = entry.getFish();
        message.setRarityColour(fish.getRarity().getColour());
        message.setLength(getDecimalFormat().format(entry.getValue()));
        message.setRarity(fish.getRarity().getDisplayName());
        message.setFishCaught(fish.getDisplayName());
        message.setMessage(ConfigMessage.LEADERBOARD_LARGEST_FISH.getMessage());
        return message;
    }
}
