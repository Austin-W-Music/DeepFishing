package com.Austin-W-Music.fish.competition;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.api.adapter.AbstractMessage;
import com.Austin-W-Music.fish.config.MainConfig;
import com.Austin-W-Music.fish.config.messages.ConfigMessage;
import com.Austin-W-Music.fish.database.DataManager;
import com.Austin-W-Music.fish.database.model.FishReport;
import com.Austin-W-Music.fish.database.model.UserReport;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinChecker implements Listener {

    /**
     * Reads all the database information for the user specified.
     *
     * @param userUUID The user UUID having their data read.
     * @param userName the in-game username of the user having their data read.
     */
    public void databaseRegistration(@NotNull UUID userUUID, @NotNull String userName) {
        if (!MainConfig.getInstance().isDatabaseOnline()) {
            return;
        }


        DeepFishing.getScheduler().runTaskAsynchronously(() -> {
            List<FishReport> fishReports;


            if (DeepFishing.getInstance().getDatabase().hasUserLog(userUUID)) {
                fishReports = DeepFishing.getInstance().getDatabase().getFishReportsForPlayer(userUUID);
            } else {
                fishReports = new ArrayList<>();
                //todo, bug here, if user joins, but doesn't participate in any comp, and then leaves, we get to this point again.
                DeepFishing.dbVerbose(userName + " has joined for the first time, creating new data handle for them.");
            }


            UserReport userReport;

            userReport = DeepFishing.getInstance().getDatabase().readUserReport(userUUID);
            if (userReport == null) {
                DeepFishing.getInstance().getDatabase().createUser(userUUID);
                userReport = DeepFishing.getInstance().getDatabase().readUserReport(userUUID);
            }

            if (fishReports != null && userReport != null) {
                DataManager.getInstance().cacheUser(userUUID, userReport, fishReports);
            } else {
                DeepFishing.getInstance().getLogger().severe("Null value when fetching data for user (" + userName + "),\n" +
                        "UserReport: " + (userReport == null) +
                        ",\nFishReports: " + (fishReports != null && !fishReports.isEmpty()));
            }
        });

    }

    // Gives the player the active fishing bar if there's a fishing event cracking off
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Competition activeComp = Competition.getCurrentlyActive();
        if (activeComp != null) {
            activeComp.getStatusBar().addPlayer(event.getPlayer());
            AbstractMessage startMessage = activeComp.getStartMessage();
            if (startMessage != null) {
                startMessage.setMessage(ConfigMessage.COMPETITION_JOIN.getMessage());
                DeepFishing.getScheduler().runTaskLater(() -> startMessage.send(event.getPlayer()), 20L * 3);
            }
        }

        EvenMoreFish.getScheduler().runTaskAsynchronously(() -> databaseRegistration(event.getPlayer().getUniqueId(), event.getPlayer().getName()));
    }

    // Removes the player from the bar list if they leave the server
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Competition activeComp = Competition.getCurrentlyActive();
        if (activeComp != null) {
            activeComp.getStatusBar().removePlayer(event.getPlayer());
        }

        if (!MainConfig.getInstance().isDatabaseOnline()) {
            return;
        }


        DeepFishing.getScheduler().runTaskAsynchronously(() -> {
            UUID userUUID = event.getPlayer().getUniqueId();

            if (!DeepFishing.getInstance().getDatabase().hasUser(userUUID)) {
                DeepFishing.getInstance().getDatabase().createUser(userUUID);
            }

            List<FishReport> fishReports = DataManager.getInstance().getFishReportsIfExists(userUUID);
            if (fishReports != null) {
                DeepFishing.getInstance().getDatabase().writeFishReports(userUUID, fishReports);
            }

            UserReport userReport = DataManager.getInstance().getUserReportIfExists(userUUID);
            if (userReport != null) {
                DeepFishing.getInstance().getDatabase().writeUserReport(userUUID, userReport);
            }

            DataManager.getInstance().uncacheUser(userUUID);
        });
        
    }
}
