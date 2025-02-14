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
import com.Austin-W-Music.fish.fishing.items.FishManager;
import com.Austin-W-Music.fish.fishing.items.Rarity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class SpecificRarityStrategy implements CompetitionStrategy {
    @Override
    public boolean begin(Competition competition) {
        return chooseRarity(competition);
    }

    @Override
    public void applyToLeaderboard(Fish fish, Player fisher, Leaderboard leaderboard, Competition competition) {
        Rarity compRarity = competition.getSelectedRarity();
        if (compRarity != null && !fish.getRarity().getId().equals(compRarity.getId())) {
            return; // Fish doesn't match the required rarity
        }

        CompetitionEntry entry = leaderboard.getEntry(fisher.getUniqueId());
        float increaseAmount = 1.0f;

        if (entry != null) {
            entry.incrementValue(increaseAmount);
            leaderboard.updateEntry(entry);
        } else {
            leaderboard.addEntry(new CompetitionEntry(fisher.getUniqueId(), fish, competition.getCompetitionType()));
        }

        if (entry != null && entry.getValue() >= competition.getNumberNeeded()) {
            competition.singleReward(fisher);
            competition.end(false);
        }
    }

    @Override
    public AbstractMessage getBeginMessage(@NotNull Competition competition, CompetitionType type) {
        return getTypeFormat(competition, ConfigMessage.COMPETITION_START);
    }

    @Override
    public @NotNull AbstractMessage getTypeFormat(@NotNull Competition competition, ConfigMessage configMessage) {
        final AbstractMessage message = CompetitionStrategy.super.getTypeFormat(competition, configMessage);
        message.setAmount(Integer.toString(competition.getNumberNeeded()));
        Rarity selectedRarity = competition.getSelectedRarity();
        if (selectedRarity == null) {
            DeepFishing.getInstance().getLogger().warning("Null rarity found. Please check your config files.");
            return message;
        }
        message.setRarityColour(selectedRarity.getColour());
        message.setRarity(selectedRarity.getDisplayName());

        return message;
    }

    private boolean chooseRarity(Competition competition) {
        List<Rarity> configRarities = competition.getCompetitionFile().getAllowedRarities();

        if (configRarities.isEmpty()) {
            DeepFishing.getInstance().getLogger().severe("No allowed-rarities list found in the " + competition.getCompetitionFile().getFileName() + " competition config file.");
            return false;
        }

        competition.setNumberNeeded(competition.getCompetitionFile().getNumberNeeded());

        try {
            Rarity rarity = configRarities.get(DeepFishing.getInstance().getRandom().nextInt(configRarities.size()));
            if (rarity != null) {
                competition.setSelectedRarity(rarity);
                return true;
            }
            competition.setSelectedRarity(FishManager.getInstance().getRandomWeightedRarity(null, 0, null, Set.copyOf(FishManager.getInstance().getRarityMap().values())));
            return true;
        } catch (IllegalArgumentException exception) {
            DeepFishing.getInstance()
                    .getLogger()
                    .severe("Could not load: " + competition.getCompetitionName() + " because a random rarity could not be chosen. \nIf you need support, please provide the following information:");
            DeepFishing.getInstance().getLogger().severe("rarities.size(): " + FishManager.getInstance().getRarityMap().size());
            DeepFishing.getInstance().getLogger().severe("configRarities.size(): " + configRarities.size());
            // Also log the exception
            DeepFishing.getInstance().getLogger().log(Level.SEVERE, exception.getMessage(), exception);
            return false;
        }
    }
}
