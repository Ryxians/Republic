package homebrew.republic;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Party implements Listener {
    static final int MAX_PARTIES = 9;
    public static List<String> registeredParties = new ArrayList<>();

    ConfigAccessor partiesConfig = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    ConfigurationSection root = partiesConfig.getConfig().getConfigurationSection("parties");
    Inventory inv;

    public void openGUI(Player p) {
       inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Party Selector");
       initializeItems();
       p.openInventory(inv);
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(ChatColor.AQUA + "Party Selector")) {
            if(!(e.getCurrentItem()==null)) {
                for (String partyName : root.getKeys(false)) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(partyName)) {
                        Player p = (Player) e.getWhoClicked();
                        joinParty(p);
                    }
                }
            }
        }
        e.setCancelled(true);
    }

    private void joinParty(Player p) {
        //TODO
    }

    private void initializeItems() {
            for(String partyName : root.getKeys(false)) {
                ItemStack item = new ItemStack(getPartyItem(partyName));
                ItemMeta im = item.getItemMeta();
                List<String> partyInfo = root.getStringList("parties." + partyName + ".policies");
                partyInfo.add(0, ChatColor.YELLOW + "Founder: " + getPartyFounder(partyName));
                partyInfo.add(1, ChatColor.DARK_PURPLE + "-------Policies-------");
                im.setDisplayName(partyName);
                im.setLore(partyInfo);
                item.setAmount(1);
                item.setItemMeta(im);
                inv.addItem(item);
            }
    }


    public void createParty(Player partyFounder, String partyName, List<String> policies, Material partyItem) {
        int counter = 0;
        for(String key : root.getKeys(false)) {
            counter++;
        }
        if(counter < 9) {
            root.createSection(partyName);
            root.set(partyName + ".founder", partyFounder.getName());
            root.set(partyName + ".material", partyItem.toString());
            for(String policy : policies) {
                root.getStringList("parties." + partyName + ".policies").add(policy);
            }
            partiesConfig.saveConfig();
        } else {
            partyFounder.sendMessage("Sorry, there are already 9 major parties.");
        }
    }

    public String getPartyFounder(String partyName) {
       return root.getString(partyName + ".founder");
    }

    public Material getPartyItem(String partyName) {
        Material mat = Material.getMaterial(root.getString(partyName + ".material"));
        return mat;
    }

    public boolean isRegistered(String partyName) {
        return registeredParties.contains(partyName);
    }
}
