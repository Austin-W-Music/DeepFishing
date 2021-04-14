package com.oheers.fish.selling;

import com.oheers.fish.EvenMoreFish;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class SellGUI {

    private final Player player;

    private Inventory menu;

    public boolean modified;

    public double value;

    public int fishCount;

    private ItemStack sellIcon, filler, confirmIcon;

    public SellGUI(Player p) {
        this.player = p;
        this.modified = false;
        makeMenu();
        addFiller();
        setSellItem();
        this.player.openInventory(menu);
    }

    private void makeMenu() {
        menu = Bukkit.createInventory(null, 36, ChatColor.translateAlternateColorCodes('&', EvenMoreFish.msgs.getWorthGUIName()));
    }

    public Player getPlayer() {
        return player;
    }

    public void addFiller() {
        // the gray glass panes at the bottom
        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(ChatColor.RESET + "");
        fill.setItemMeta(fillMeta);

        // sets it as a default menu item that won't be dropped in a .close() request
        this.filler = WorthNBT.attributeDefault(fill);

        menu.setItem(27, fill);
        menu.setItem(28, fill);
        menu.setItem(29, fill);
        menu.setItem(30, fill);
        // Sell icon
        menu.setItem(32, fill);
        menu.setItem(33, fill);
        menu.setItem(34, fill);
        menu.setItem(35, fill);
    }

    public void setSellItem() {
        ItemStack sIcon = new ItemStack(Material.GOLD_INGOT);
        ItemMeta sellMeta = sIcon.getItemMeta();
        sellMeta.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "SELL");
        sellMeta.setLore(Arrays.asList(
                "" + ChatColor.YELLOW + ChatColor.BOLD + "Value: " + ChatColor.YELLOW + getTotalWorth(),
                ChatColor.GRAY + "LEFT CLICK to sell the fish.",
                ChatColor.GRAY + "RIGHT CLICK to cancel."
        ));

        sIcon.setItemMeta(sellMeta);
        glowify(sIcon);

        this.sellIcon = WorthNBT.attributeDefault(sIcon);
        menu.setItem(31, this.sellIcon);
    }

    public ItemStack getSellIcon() {
        return this.sellIcon;
    }

    public ItemStack getConfirmIcon() {
        return this.confirmIcon;
    }

    public void createConfirmIcon() {
        ItemStack confirm = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta cMeta = confirm.getItemMeta();
        cMeta.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "CONFIRM");
        cMeta.setLore(Arrays.asList(
                "" + ChatColor.YELLOW + ChatColor.BOLD + "Value: " + ChatColor.YELLOW + getTotalWorth(),
                ChatColor.GRAY + "LEFT CLICK to sell the fish.",
                ChatColor.GRAY + "RIGHT CLICK to cancel."
        ));

        confirm.setItemMeta(cMeta);
        glowify(confirm);

        this.confirmIcon = WorthNBT.attributeDefault(confirm);
    }

    public void setConfirmIcon() {
        this.menu.setItem(31, null);
        this.menu.setItem(31, this.confirmIcon);
    }

    public String getTotalWorth() {
        if (this.menu == null) return Double.toString(0.0d);

        double val = 0.0d;
        int count = 0;

        for (ItemStack is : this.menu.getContents()) {
            // -1.0 is given when there's no worth NBT value
            if (WorthNBT.getValue(is) != -1.0) {
                val += (WorthNBT.getValue(is) * is.getAmount());
                count += is.getAmount();
            }
        }

        this.value = val;
        this.fishCount = count;

        return "$" + NumberFormat.getInstance(Locale.US).format(val);
    }

    // will drop only non-fish items if the method is called from selling, and everything if it's just a gui close
    public void close(boolean selling) {
        EvenMoreFish.guis.remove(this);
        player.closeInventory();

        if (selling) rescueNonFish();
        else rescueAllItems();
    }

    public ItemStack getFiller() {
        return this.filler;
    }

    public void setMenu(Inventory inv) {
        this.menu = inv;
    }

    public void setModified(boolean mod) {
        this.modified = mod;
    }

    public boolean getModified() {
        return this.modified;
    }

    public double getSellPrice() {
        return this.value;
    }

    // for each item in the menu, if it isn't a default menu item, it's dropped at the player's feet
    private void rescueAllItems() {
        for (ItemStack i : this.menu) {
            if (i != null) {
                if (!WorthNBT.isDefault(i)) {
                    this.player.getLocation().getWorld().dropItem(this.player.getLocation(), i);
                }
            }
        }
    }

    private void rescueNonFish() {
        for (ItemStack i : this.menu) {
            if (i != null) {
                if (!(WorthNBT.isDefault(i)) && !(WorthNBT.isFish(i))) {
                    this.player.getLocation().getWorld().dropItem(this.player.getLocation(), i);
                }
            }
        }
    }

    private void glowify(ItemStack i) {

        // plops on the unbreaking 1 enchantment to make it glow
        i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta meta = i.getItemMeta();

        // hides the unbreaking 1 enchantment from showing in the lore
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);
    }
}
