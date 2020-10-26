package homebrew.republic.listeners;

import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

public class PartyCreationMenu {
    List<Inventory> menus = new LinkedList<Inventory>();
    JavaPlugin plugin;
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
                    menus.remove(evt.getClickedInventory());
                } else if (evt.getCurrentItem().getItemMeta().getLore().contains("Click this to change the party name!")) {
                    menus.remove(evt.getClickedInventory());
                    evt.getWhoClicked().closeInventory();
                    AnvilGUI.Builder builder = new AnvilGUI.Builder();
                    builder.item(evt.getInventory().getItem(2));
                    builder.text("Rename me!");
                    builder.plugin(plugin);
                    builder.onComplete((player, text) -> {
                        Inventory inv = evt.getClickedInventory();
                        ItemStack item = inv.getItem(2);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(text);
                        item.setItemMeta(meta);
                        inv.setItem(2, item);
                        player.openInventory(inv);
                        menus.add(inv);
                        return AnvilGUI.Response.close();
                    });
                    builder.open((Player) evt.getWhoClicked());
                }
                evt.setCancelled(true);
            }
        }
    }

    public PartyCreationMenu(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new InventoryInteraction(), plugin);
    }

    public void addMenu(Inventory menu) {
        menus.add(menu);
    }

    public Inventory createMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, "Party Creation");
        List<String> lore = new LinkedList<>();

        // Thingy for renaming
        ItemStack anvil = new ItemStack(Material.ANVIL);
        ItemMeta meta = anvil.getItemMeta();
        meta.setDisplayName("Teset");
        lore.add("Click this to change the party name!");
        meta.setLore(lore);
        anvil.setItemMeta(meta);

        // Thing for representation
        ItemStack item = new ItemStack(Material.ACACIA_BOAT);
        meta = item.getItemMeta();
        meta.setDisplayName("Teset");
        lore.clear();
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
