package homebrew.republic.listeners;

import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

public class PartyCreationMenu implements Listener {
    JavaPlugin plugin;
    Player player;
    ItemStack anvil;
    ItemStack ref;
    ItemStack complete;
    boolean isClosed = false;

        @EventHandler
        public void inventoryInteraction(InventoryClickEvent evt) {
            if (!evt.isCancelled()) {
                if (player == null) System.out.println("NO PLAYER");
                if (player.equals(evt.getWhoClicked())) {
                    ItemStack item = evt.getCurrentItem();
                    if (item == null) {

                    } else if (item.equals(anvil)) {
                        player.closeInventory();
                        AnvilGUI.Builder builder = new AnvilGUI.Builder();
                        builder.item(ref);
                        builder.text("Rename me!");
                        builder.plugin(plugin);
                        builder.onComplete((player, text) -> {
                            ItemMeta meta = ref.getItemMeta();
                            meta.setDisplayName(text);
                            ref.setItemMeta(meta);
                            player.openInventory(recreateMenu());
                            return AnvilGUI.Response.close();
                        });
                        builder.open(player);
                    } else if (item.equals(ref)) {
                        player.sendMessage("This is the party reference");
                    } else if (item.equals(complete)) {
                        ItemStack[] contents = evt.getInventory().getContents();
                        ItemMeta meta = ref.getItemMeta();
                        String name = meta.getDisplayName();
                        Material mat = ref.getType();
                        PartyManager.registerParty(new Party(player, name, mat));
                        player.closeInventory();
                    }
                    evt.setCancelled(true);
                }
            }
        }

        @EventHandler
        public void closeInventory(InventoryCloseEvent evt) {
            if (player.equals(evt.getPlayer())) {
                if (isClosed) {
                    closeCreateMenu();

                    player.sendMessage("Party Closed.");
                }
            }
        }

    public PartyCreationMenu(JavaPlugin plugin, Player player) {
        if (player == null) {
            System.out.println("No Player Passed!");
        } else {
            this.plugin = plugin;
            this.player = player;
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    public void closeCreateMenu() {
        HandlerList.unregisterAll(this);
    }

    public Inventory createMenu() {
        return createMenu("Teset");
    }

    public Inventory recreateMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, "Party Creation");

        inv.setItem(0, anvil);
        inv.setItem(2, ref);
        inv.setItem(8, complete);
        return inv;
    }
    public Inventory createMenu(String name) {
        //Inventory inv = Bukkit.createInventory(null, 9, "Party Creation");
        List<String> lore = new LinkedList<>();

        // Thingy for renaming
        anvil = new ItemStack(Material.ANVIL);
        ItemMeta meta = anvil.getItemMeta();
        meta.setDisplayName(name);
        lore.add("Click this to change the party name!");
        meta.setLore(lore);
        anvil.setItemMeta(meta);

        // Thing for representation
        ref = new ItemStack(Material.ACACIA_BOAT);
        meta = ref.getItemMeta();
        meta.setDisplayName(name);
        lore.clear();
        lore.add("The grand first party.");
        meta.setLore(lore);
        ref.setItemMeta(meta);

        // Thingy for done
        complete = new ItemStack(Material.EMERALD);
        meta = complete.getItemMeta();
        meta.setDisplayName("Complete");
        complete.setItemMeta(meta);

        //inv.setItem(0, anvil);
        //inv.setItem(2, ref);
        //inv.setItem(8, complete);
        return recreateMenu();
    }
}
