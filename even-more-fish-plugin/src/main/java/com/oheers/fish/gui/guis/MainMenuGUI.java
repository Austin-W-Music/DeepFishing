package com.Austin-W-Music.fish.gui.guis;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.config.GUIConfig;
import com.Austin-W-Music.fish.config.GUIFillerConfig;
import com.Austin-W-Music.fish.gui.GUIUtils;
import de.themoep.inventorygui.InventoryGui;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class MainMenuGUI implements DFGUI {

    private final InventoryGui gui;
    private final HumanEntity viewer;

    public MainMenuGUI(@NotNull HumanEntity viewer) {
        this.viewer = viewer;
        Section section = GUIConfig.getInstance().getConfig().getSection("main-menu");
        gui = GUIUtils.createGUI(section);
        if (section == null) {
            DeepFishing.getInstance().getLogger().log(Level.SEVERE, "Could not find the config for the Main Menu GUI!");
            return;
        }
        gui.setFiller(GUIUtils.getFillerItem(section.getString("filler"), Material.BLACK_STAINED_GLASS_PANE));
        gui.addElements(GUIUtils.getElements(section, this, null));
        gui.addElements(GUIFillerConfig.getInstance().getDefaultFillerElements());
    }

    @Override
    public void open() {
        gui.show(this.viewer);
    }

    @Override
    public void doRescue() {}

    @Override
    public InventoryGui getGui() {
        return this.gui;
    }

}
