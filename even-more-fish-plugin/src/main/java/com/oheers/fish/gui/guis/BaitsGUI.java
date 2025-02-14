package com.Austin-W-Music.fish.gui.guis;

import com.Austin-W-Music.fish.DeepFishing;
import com.Austin-W-Music.fish.FishUtils;
import com.Austin-W-Music.fish.baits.BaitManager;
import com.Austin-W-Music.fish.config.GUIConfig;
import com.Austin-W-Music.fish.config.GUIFillerConfig;
import com.Austin-W-Music.fish.gui.GUIUtils;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class BaitsGUI implements DFGUI {

    private final InventoryGui gui;
    private final HumanEntity viewer;

    public BaitsGUI(@NotNull HumanEntity viewer) {
        this.viewer = viewer;
        Section section = GUIConfig.getInstance().getConfig().getSection("baits-menu");
        gui = GUIUtils.createGUI(section);
        if (section == null) {
            DeepFishing.getInstance().getLogger().log(Level.SEVERE, "Could not find the config for the Baits GUI!");
            return;
        }
        gui.setFiller(GUIUtils.getFillerItem(section.getString("filler"), Material.BLACK_STAINED_GLASS_PANE));
        gui.addElements(GUIUtils.getElements(section, this, null));
        gui.addElements(GUIFillerConfig.getInstance().getDefaultFillerElements());
        gui.addElement(getBaitsGroup(section));
    }

    private DynamicGuiElement getBaitsGroup(Section section) {
        char character = FishUtils.getCharFromString(section.getString("bait-character", "b"), 'b');

        return new DynamicGuiElement(character, who -> {
            GuiElementGroup group = new GuiElementGroup(character);
            BaitManager.getInstance().getBaitMap().values()
                    .forEach(bait -> group.addElement(new StaticGuiElement(character, bait.create(Bukkit.getOfflinePlayer(viewer.getUniqueId())))));
            return group;
        });
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
