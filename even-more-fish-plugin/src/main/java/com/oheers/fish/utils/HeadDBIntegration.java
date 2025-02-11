package com.Austin-W-Music.fish.utils;

import com.Austin-W-Music.fish.DeepFishing;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HeadDBIntegration implements Listener {

    @EventHandler
    public void onHDBLoad(DatabaseLoadEvent event) {
        DeepFishing.getInstance().setHDBapi(new HeadDatabaseAPI());
    }
}
