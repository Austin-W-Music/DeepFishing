package com.Austin-W-Music.fish.events;

import com.Austin-W-Music.fish.competition.Competition;
import com.Austin-W-Music.fish.config.MainConfig;
import dev.aurelium.auraskills.api.event.loot.LootDropEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuraSkillsFishingEvent implements Listener {

    @EventHandler
    public void fishCatch(LootDropEvent event) {
        if (event.getCause() == LootDropEvent.Cause.FISHING_LUCK || event.getCause() == LootDropEvent.Cause.TREASURE_HUNTER || event.getCause() == LootDropEvent.Cause.FISHING_OTHER_LOOT || event.getCause() == LootDropEvent.Cause.EPIC_CATCH) {
            if (MainConfig.getInstance().disableAureliumSkills()) {
                if (MainConfig.getInstance().isCompetitionUnique()) {
                    if (Competition.isActive()) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

}
