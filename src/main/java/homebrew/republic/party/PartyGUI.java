package homebrew.republic.party;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PartyGUI implements Listener {

    Inventory inv;

    public void openGUI(Player p) {
       inv = Bukkit.createInventory(null, PartyManager.MAX_PARTIES, ChatColor.AQUA + "Party Selector");
       initializeItems();
       p.openInventory(inv);
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equals(ChatColor.AQUA + "Party Selector")) {
            if(!(e.getCurrentItem()==null)) {
                for (String partyName : PartyManager.getPartyConfigRoot().getKeys(false)) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals(partyName)) {
                        Player p = (Player) e.getWhoClicked();
                        //joinParty(p);
                    }
                }
            }
        }
        e.setCancelled(true);
    }


    private void initializeItems() {
            for(String partyName : PartyManager.getPartyConfigRoot().getKeys(false)) {
                Party p = new Party(partyName);
                ItemStack item = new ItemStack(p.getPartyItem());
                ItemMeta im = item.getItemMeta();
                List<String> partyInfo = p.thisPartyConf.getStringList(".policies");
                partyInfo.add(0, ChatColor.YELLOW + "Founder: " + p.getPartyFounder());
                partyInfo.add(1, ChatColor.DARK_PURPLE + "-------Policies-------");
                im.setDisplayName(partyName);
                im.setLore(partyInfo);
                item.setAmount(1);
                item.setItemMeta(im);
                inv.addItem(item);
            }
    }

}
