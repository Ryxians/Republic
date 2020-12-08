package homebrew.republic.party;

import homebrew.republic.interfaces.Electable;
import org.bukkit.Bukkit;
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
    private int id;
    private String desc;
    private Material mat;
    private UUID founder;

    public Party(String name) {
        this(null, name, Material.STONE);
    }

    public Party(Player player, String name, Material mat) {
        this(PartyManager.getTOPID(), player, name, mat);
    }

    public Party(Player player, String name, Material mat, String desc) {
        this(PartyManager.getTOPID(), player.getUniqueId(), name, mat, desc);
    }

    public Party(int id, Player player, String name, Material mat) {
        this(id, player.getUniqueId(), name, mat, "A bad party.");
    }

    public Party(int id, UUID player, String name, Material mat, String desc) {
        partyName = name;
        this.id = id;
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
        lore.add("Founded by: " + getFounder().getDisplayName());
        meta.setLore(lore);
        partyItem.setItemMeta(meta);
        return partyItem;
    }

    public Material getMaterial() {
        return mat;
    }

    public Player getFounder() {
        return Bukkit.getPlayer(founder);
    }

    public UUID getFounderUUID() {
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
       return founder.toString();
    }

    public Material getPartyItem() {
       return mat;
    }

    public String getName() {
        return partyName;
    }

    //Overloads method from PartyManager
    public String getPartyIDString() {
        return String.valueOf(id);
    }

   /* public String[] getMembers() {
            //TODO
    }*/

    //Party setters

    public void setName(String name) {
        partyName = name;
        if (!PartyManager.getConfig().updatePartyName(this, name)) {
            System.out.println("Party name change failed.");
        }

    }

    @Override
    public int getID() {
        return id;
    }
}
