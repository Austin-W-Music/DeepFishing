package com.Austin-W-Music.fish.competition.strategies;


import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.adapter.AbstractMessage;
import com.Austin-W-Music.fish.competition.Competition;
import com.Austin-W-Music.fish.competition.CompetitionEntry;
import com.Austin-W-Music.fish.competition.CompetitionStrategy;
import com.Austin-W-Music.fish.competition.CompetitionType;
import com.Austin-W-Music.fish.competition.leaderboard.Leaderboard;
import com.Austin-W-Music.fish.config.messages.ConfigMessage;
import com.Austin-W-Music.fish.fishing.items.Fish;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomStrategy implements CompetitionStrategy {
    private CompetitionType randomType;
    @Override
    public boolean begin(Competition competition) {
        competition.setCompetitionType(getRandomType());
        this.randomType = competition.getCompetitionType();
        competition.setOriginallyRandom(true);
        return true;
    }

    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        randomType.getStrategy().applyToLeaderboard(fish,fisher,leaderboard,competition);
    }

    @Override
    public AbstractMessage getSingleConsoleLeaderboardMessage(@NotNull AbstractMessage message, @NotNull CompetitionEntry entry) {
        return randomType.getStrategy().getSingleConsoleLeaderboardMessage(message, entry);
    }

    @Override
    public AbstractMessage getBeginMessage(Competition competition, CompetitionType type) {
        return randomType.getStrategy().getBeginMessage(competition, type);
    }

    @Override
    public AbstractMessage getSinglePlayerLeaderboard(@NotNull AbstractMessage message, @NotNull CompetitionEntry entry) {
        return randomType.getStrategy().getSinglePlayerLeaderboard(message, entry);
    }

    @Override
    public @NotNull AbstractMessage getTypeFormat(@NotNull Competition competition, ConfigMessage configMessage) {
        return randomType.getStrategy().getTypeFormat(competition, configMessage);
    }

    public CompetitionType getRandomType() {
        // -1 from the length so that the RANDOM isn't chosen as the random value.
        int type = DeepFishing.getInstance().getRandom().nextInt(CompetitionType.values().length - 1);
        return CompetitionType.values()[type];
    }
}
