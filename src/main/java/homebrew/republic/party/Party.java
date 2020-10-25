package homebrew.republic.party;

import homebrew.republic.interfaces.Electable;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Party implements Listener, Electable {

    private String partyName;
    private UUID id;
    private String desc;
    private Material mat;
    private Player founder;
    public ConfigurationSection thisPartyConf;

    public Party(String name) {
        this(null, name, Material.STONE);
    }

    public Party(Player player, String name, Material mat) {
        this(UUID.randomUUID(), player, name, mat);
    }

    public Party(UUID uuid, Player player, String name, Material mat) {
        this(uuid, player, name, mat, "A bad party.");
    }

    public Party(UUID uuid, Player player, String name, Material mat, String desc) {
        partyName = name;
        id = uuid;
        this.mat = mat;
        founder = player;
        this.desc = desc;
    }

    public ItemStack getItem() {
        ItemStack partyItem = new ItemStack(mat);
        ItemMeta meta = partyItem.getItemMeta();
        meta.setDisplayName(partyName);
        // about variable will be used for Party descriptions.
        List<String> lore = new LinkedList<>();
        lore.add(desc);
        lore.add("Founded by: " + founder.getDisplayName());
        meta.setLore(lore);
        partyItem.setItemMeta(meta);
        return partyItem;
    }

    public Material getMaterial() {
        return mat;
    }

    public Player getFounder() {
        return founder;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
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
        return id.toString();
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

    @Override
    public java.util.UUID getUniqueId() {
        return id;
    }
}
