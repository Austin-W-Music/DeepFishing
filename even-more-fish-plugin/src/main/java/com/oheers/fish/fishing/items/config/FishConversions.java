package com.Austin-W-Music.fish.fishing.items.config;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.config.ConfigBase;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FishConversions extends RarityConversions {

    @Override
    public void performCheck() {
        File fishFile = new File(DeepFishing.getInstance().getDataFolder(), "fish.yml");
        if (!fishFile.exists() || !fishFile.isFile()) {
            return;
        }
        DeepFishing.getInstance().getLogger().info("Performing automatic conversion of fish configs.");
        File raritiesDir = getRaritiesDirectory();
        if (!raritiesDir.exists()) {
            // Do nothing if the rarities directory does not exist.
            return;
        }
        ConfigBase config = new ConfigBase(fishFile, DeepFishing.getInstance(), false);
        Section fishSection = config.getConfig().getSection("fish");
        if (fishSection == null) {
            finalizeConversion(config);
            return;
        }
        for (String rarityKey : fishSection.getRoutesAsStrings(false)) {
            Section section = fishSection.getSection(rarityKey);
            if (section != null) {
                convertSectionToFile(section);
            }
        }
        finalizeConversion(config);
    }

    private void finalizeConversion(@NotNull ConfigBase fishConfig) {
        // Rename the file to fish.yml.old
        File file = fishConfig.getFile();
        file.renameTo(new File(DeepFishing.getInstance().getDataFolder(), "fish.yml.old"));
        file.delete();

        DeepFishing.getInstance().getLogger().severe("Your fish configs have been automatically converted to the new format.");
        DeepFishing.getInstance().getLogger().severe("They are now located in the rarity files.");
    }

    private void convertSectionToFile(@NotNull Section section) {
        String id = section.getNameAsString();
        if (id == null) {
            return;
        }
        File rarityFile = new File(DeepFishing.getInstance().getDataFolder(), "rarities/" + id + ".yml");
        // Do nothing if the file doesn't exist.
        if (!rarityFile.exists()) {
            return;
        }
        ConfigBase configBase = new ConfigBase(rarityFile, DeepFishing.getInstance(), false);
        Section fishSection = configBase.getConfig().createSection("fish");
        fishSection.setAll(section.getRouteMappedValues(true));
        configBase.save();
    }

}
