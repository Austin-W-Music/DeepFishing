package com.Austin-W-Music.fish.competition.strategies;


import com.Austin-W-Music.fish.competition.Competition;
import com.Austin-W-Music.fish.competition.CompetitionEntry;
import com.Austin-W-Music.fish.competition.CompetitionStrategy;
import com.Austin-W-Music.fish.competition.leaderboard.Leaderboard;
import com.Austin-W-Music.fish.fishing.items.Fish;
import org.bukkit.entity.Player;

public class MostFishStrategy implements CompetitionStrategy {


    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        CompetitionEntry entry = leaderboard.getEntry(fisher.getUniqueId());
        float increaseAmount = 1.0f;

        if (entry != null) {
            entry.incrementValue(increaseAmount);
            leaderboard.updateEntry(entry);
        } else {
            leaderboard.addEntry(new CompetitionEntry(fisher.getUniqueId(), fish, competition.getCompetitionType()));
        }
    }
}
