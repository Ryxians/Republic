package homebrew.republic.party;

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

import java.util.List;

public class Party extends PartyManager implements Listener {

    private String partyName;
    private String UUID;
    private Material mat;
    private Player founder;
    public ConfigurationSection thisPartyConf;

    public Party(String name) {
        this.partyName = name;
        for(String partyUUID : PartyManager.partyConfigRoot.getKeys(false)) {
            if (PartyManager.partyConfigRoot.getString(partyUUID + ".name").equals(this.partyName)) {
                this.UUID = partyUUID;
                thisPartyConf = PartyManager.partyConfigRoot.getConfigurationSection(UUID);
            }
        }
    }


    public void addMember() {
        //TODO
    }
    public void removeMember() {
        //TODO
    }

    //Party getters
    public String getPartyFounder() {
       return thisPartyConf.getString(".founder");
    }

    public Material getPartyItem() {
       return mat = Material.getMaterial(thisPartyConf.getString(".material"));
    }

    public String getName() {
        return partyName;
    }

    //Overloads method from PartyManager
    public String getPartyUUIDString() {
        return UUID;
    }

   /* public String[] getMembers() {
            //TODO
    }*/

    //Party setters

    public void setName(String name) {
        partyName = name;
        thisPartyConf.set(partyName, name);
        PartyManager.partiesConfigAccessor.saveConfig();

    }
}
