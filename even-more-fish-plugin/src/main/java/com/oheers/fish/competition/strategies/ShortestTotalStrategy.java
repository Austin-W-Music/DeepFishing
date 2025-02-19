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

public class ShortestTotalStrategy implements CompetitionStrategy {
    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        CompetitionEntry entry = leaderboard.getEntry(fisher.getUniqueId());
        float increaseAmount = fish.getLength();

        if (entry != null) {
            entry.incrementValue(increaseAmount);
            leaderboard.updateEntry(entry);
        } else {
            CompetitionEntry newEntry = new CompetitionEntry(fisher.getUniqueId(), fish, competition.getCompetitionType());
            newEntry.incrementValue(increaseAmount - 1); // Adjust for the first entry
            leaderboard.addEntry(newEntry);
        }
    }

    @Override
    public AbstractMessage getSingleConsoleLeaderboardMessage(@NotNull AbstractMessage message, @NotNull CompetitionEntry entry) {
        message.setMessage(ConfigMessage.LEADERBOARD_SHORTEST_TOTAL.getMessage());
        message.setAmount(getDecimalFormat().format(entry.getValue()));
        return message;
    }

    @Override
    public AbstractMessage getSinglePlayerLeaderboard(@NotNull AbstractMessage message, @NotNull CompetitionEntry entry) {
        message.setMessage(ConfigMessage.LEADERBOARD_SHORTEST_TOTAL.getMessage());
        message.setAmount(getDecimalFormat().format(entry.getValue()));
        return message;
    }
}
