package com.Austin-W-Music.fish.utils;

import com.Austin-W-Music.fish.FishUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class AntiCraft implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        for (ItemStack craftItem : event.getInventory().getMatrix()) {
            if (craftItem == null) continue;
            if (FishUtils.isFish(craftItem)) event.setCancelled(true);
            else if (FishUtils.isBaitObject(craftItem)) event.setCancelled(true);
        }
    }
}
