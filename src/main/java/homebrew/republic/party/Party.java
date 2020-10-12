package homebrew.republic.party;

import homebrew.republic.interfaces.Electable;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class Party implements Listener, Electable {

    private String partyName;
    private UUID id;
    //private String UUID;
    private Material mat;
    private Player founder;
    public ConfigurationSection thisPartyConf;

    public Party(String name, int disabled) {
        this.partyName = name;
        for(String partyUUID : PartyManager.partyConfigRoot.getKeys(false)) {
            if (PartyManager.partyConfigRoot.getString(partyUUID + ".name").equals(this.partyName)) {
                this.id = UUID.fromString(partyUUID);
                thisPartyConf = PartyManager.partyConfigRoot.getConfigurationSection(id.toString());
            }
        }
    }

    public Party(String name) {
        this(null, name, Material.STONE);
    }

    public Party(Player player, String name, Material mat) {
        this(UUID.randomUUID(), player, name, mat);
    }

    public Party(UUID uuid, Player player, String name, Material mat) {
        partyName = name;
        id = uuid;
        this.mat = mat;
        founder = player;
    }

    public Material getMaterial() {
        return mat;
    }

    public Player getFounder() {
        return founder;
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
