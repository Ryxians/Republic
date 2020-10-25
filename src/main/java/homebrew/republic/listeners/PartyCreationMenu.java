package homebrew.republic.listeners;

import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

public class PartyCreationMenu {
    List<Inventory> menus = new LinkedList<Inventory>();
    private class InventoryInteraction implements Listener {
        @EventHandler
        public void inventoryInteraction(InventoryClickEvent evt) {
            if (menus.contains(evt.getClickedInventory())) {
                if (evt.getCurrentItem().getType().equals(Material.EMERALD)) {
                    ItemStack[] contents = evt.getInventory().getContents();
                    ItemMeta meta = contents[2].getItemMeta();
                    String name = meta.getDisplayName();
                    Material mat = contents[2].getType();
                    PartyManager.registerParty(new Party(((Player)evt.getWhoClicked()), name, mat));
                    evt.getWhoClicked().closeInventory();
                }
                evt.setCancelled(true);
            }
        }
    }

    public PartyCreationMenu(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new InventoryInteraction(), plugin);
    }

    public void addMenu(Inventory menu) {
        menus.add(menu);
    }

    public Inventory createMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, "Party Creation");

        // Thingy for renaming
        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemMeta meta = anvil.getItemMeta();
        meta.setDisplayName("Teset");
        anvil.setItemMeta(meta);

        // Thing for representation
        ItemStack item = new ItemStack(Material.ACACIA_BOAT);
        meta = item.getItemMeta();
        meta.setDisplayName("Teset");
        List<String> lore = new LinkedList<>();
        lore.add("The grand first party.");
        meta.setLore(lore);
        item.setItemMeta(meta);

        // Thingy for done
        ItemStack complete = new ItemStack(Material.EMERALD);
        meta = complete.getItemMeta();
        meta.setDisplayName("Complete");
        complete.setItemMeta(meta);

        inv.setItem(0, anvil);
        inv.setItem(2, item);
        inv.setItem(8, complete);
        return inv;
    }
}
