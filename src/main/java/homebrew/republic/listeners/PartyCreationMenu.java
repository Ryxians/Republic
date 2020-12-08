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
    ItemStack rename;
    ItemStack desc;
    ItemStack ref;
    ItemStack complete;
    boolean isClosed = false;

        @EventHandler
        public void inventoryInteraction(InventoryClickEvent evt) {
            if (!evt.isCancelled()) {
                // In development sometimes a null player would exist
                // If this occurs before an error then clearly I haven't fixed it
                // :(
                if (player == null) System.out.println("NO PLAYER");
                if (player.equals(evt.getWhoClicked())) {
                    // The item the player has clicked
                    ItemStack item = evt.getCurrentItem();
                    if (item == null);
                    else if (item.equals(rename)) {
                        // For reading purposes, stored in method
                        rename();
                    } else if (item.equals(desc)) {
                        // For reading purposes, stored in method
                        description();
                    } else if (item.equals(ref)) {
                        player.sendMessage("This is the party reference");
                    } else if (item.equals(complete)) {
                        ItemMeta meta = ref.getItemMeta();
                        String name = meta.getDisplayName();
                        String description = desc.getItemMeta().getLore().get(0);
                        Material mat = ref.getType();
                        PartyManager.registerParty(new Party(player, name, mat, description));
                        closeCreateMenu();
                    }
                    evt.setCancelled(true);
                }
            }
        }

        private void rename() {
            isClosed = false;
            player.closeInventory();
            AnvilGUI.Builder builder = new AnvilGUI.Builder();
            builder.item(ref);
            builder.text("Rename me!");
            builder.plugin(plugin);
            builder.onComplete((player, text) -> {
                // Changes the Reference name to the name
                // chosen by the player
                ItemMeta meta = ref.getItemMeta();
                meta.setDisplayName(text);
                ref.setItemMeta(meta);

                // Changes the Anvil name to the name
                // Chosen by the player
                meta = rename.getItemMeta();
                meta.setDisplayName(text);
                rename.setItemMeta(meta);

                // Reopen a new and updated
                // Creation menu
                player.openInventory(recreateMenu());
                return AnvilGUI.Response.close();
            });
            builder.open(player);
        }

        private void description() {
            player.closeInventory();
            AnvilGUI.Builder builder = new AnvilGUI.Builder();
            builder.item(ref);
            builder.text("Change me!");
            builder.plugin(plugin);
            builder.onComplete((player, text) -> {
                // Changes the Reference description to the description
                // chosen by the player
                ItemMeta meta = ref.getItemMeta();
                List<String> lore = new LinkedList<>();
                lore.add(text);
                meta.setLore(lore);
                ref.setItemMeta(meta);

                // Changes the Anvil description to the description
                // Chosen by the player
                meta = desc.getItemMeta();
                meta.setLore(lore);
                desc.setItemMeta(meta);

                // Reopen a new and updated
                // Creation menu
                player.openInventory(recreateMenu());
                return AnvilGUI.Response.close();
            });
            builder.open(player);
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
        player.closeInventory();
    }

    public Inventory createMenu() {
        return createMenu("Teset");
    }

    public Inventory recreateMenu() {
        // Generate a 9 wide inventory, a slot for each party
        Inventory inv = Bukkit.createInventory(null, 9, "Party Creation");

        inv.setItem(0, rename);
        //inv.setItem(1, desc);
        inv.setItem(6, ref);
        inv.setItem(8, complete);
        return inv;
    }
    public Inventory createMenu(String name) {
        // Lore Linked List for Menu item descriptions
        List<String> lore = new LinkedList<>();

        // The Anvil Shall have the following characteristics
        // Name: {Party Name}, Default: Teset
        // Lore: Click this to change the party name!
        rename = new ItemStack(Material.ANVIL);
        ItemMeta meta = rename.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        lore.add("Click this to change the party name!");
        meta.setLore(lore);
        rename.setItemMeta(meta);

        // The Description Anvil shall have the following characteristics
        // Name: Description
        // Lore: Click this to change your party description!
        desc = new ItemStack(Material.ANVIL);
        meta = rename.getItemMeta();
        assert meta != null;
        meta.setDisplayName("Description");
        lore.clear();
        lore.add("Click this to change your party description!");
        meta.setLore(lore);
        desc.setItemMeta(meta);

        // A representation of the party being created
        // By default it will look like an Acacia Boat
        // Name: {Party Name}, Default: Teset
        // Lore: {Party Lore}, Default: The grand first party.
        ref = new ItemStack(Material.ACACIA_BOAT);
        meta = ref.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        lore.clear();
        lore.add("The grand first party.");
        meta.setLore(lore);
        ref.setItemMeta(meta);

        // The finish button
        // Name: Complete
        // Lore: Click this to create your party!
        complete = new ItemStack(Material.EMERALD);
        meta = complete.getItemMeta();
        assert meta != null;
        meta.setDisplayName("Complete");
        lore.clear();
        lore.add("Click this to create your party!");
        complete.setItemMeta(meta);

        return recreateMenu();
    }
}
