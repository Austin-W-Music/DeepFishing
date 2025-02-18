package com.Austin-W-Music.fish.competition.strategies;

import com.Austin-W-Music.fish.competition.Competition;
import com.Austin-W-Music.fish.competition.CompetitionStrategy;
import com.Austin-W-Music.fish.competition.leaderboard.Leaderboard;
import com.Austin-W-Music.fish.fishing.items.Fish;
import org.bukkit.entity.Player;


public class NullStrategy implements CompetitionStrategy {

    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        //do nothing duh
    }
}
