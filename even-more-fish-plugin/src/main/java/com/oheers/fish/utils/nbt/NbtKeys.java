package com.Austin-W-Music.fish.utils.nbt;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public class NbtKeys {
    private NbtKeys() {
        throw new UnsupportedOperationException();
    }

    public static final String DF_COMPOUND = JavaPlugin.getProvidingPlugin(NbtUtils.class).getName().toLowerCase(Locale.ROOT);
    public static final String DF_FISH_PLAYER = "df-fish-player";
    public static final String DF_FISH_RARITY = "df-fish-rarity";
    public static final String DF_FISH_LENGTH = "df-fish-length";
    public static final String DF_FISH_NAME = "df-fish-name";
    public static final String DF_FISH_RANDOM_INDEX = "df-fish-random-index";
    public static final String DF_BAIT = "df-bait";
    public static final String DF_APPLIED_BAIT = "df-applied-bait";

    public static final String DF_ROD_NBT = "df-rod-nbt";

    public static final String PUBLIC_BUKKIT_VALUES = "PublicBukkitValues";
    public static final String DEFAULT_GUI_ITEM = "default-gui-item";
}
