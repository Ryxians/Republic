package homebrew.republic.party;

import homebrew.republic.ConfigAccessor;
import homebrew.republic.Republic;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PartyManager {
    //STATIC
    static final int MAX_PARTIES = 9;
    static HashMap<String, Party> registeredParties = new HashMap<>();
    static final ConfigAccessor partiesConfigAccessor = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    static ConfigurationSection partyConfigRoot = partiesConfigAccessor.getConfig();

    // Party Creation Inventories
    static List<Inventory> inventories = new LinkedList<Inventory>();


    public PartyManager() {
    }

    public static boolean registerParty(Party party) {
        boolean rc = false;
        if (registeredParties.size() < MAX_PARTIES) {
            registeredParties.put(party.getName(), party);
            rc = true;
        }
        return rc;
    }

    public static void saveParties() {
        registeredParties.forEach((i, j) -> {
            String str = j.getUniqueId().toString();
            partyConfigRoot.set(str + ".founder", j.getFounder());
            partyConfigRoot.set(str + ".name", j.getName());
            partyConfigRoot.set(str + ".material", j.getMaterial().toString());
        });
        partiesConfigAccessor.saveConfig();
    }

    public static void loadParties() {
        partyConfigRoot.getKeys(false).forEach((i) -> {
            String str = i.toString();
            Player founder = ((OfflinePlayer) partyConfigRoot.get(str + ".founder")).getPlayer();
            String name = (String) partyConfigRoot.get(str + ".name");
            Material mat = Material.getMaterial((String) partyConfigRoot.get(str + ".material"));
            Party party = new Party(UUID.fromString(i), founder, name, mat);
            registerParty(party);
        });
    }


    public void deleteParty(Party party) {
        //TODO remove players from party, then delete

        getPartyConfigRoot().set(party.getPartyUUIDString(), null);
        partiesConfigAccessor.saveConfig();
    }


    public boolean isRegistered(Party party) {
        return registeredParties.containsKey(party);
    }



    public static ConfigurationSection getPartyConfigRoot() {
        return partyConfigRoot;
    }

    public static Inventory getPartyView() {
        Inventory inv = Bukkit.createInventory(null, 9);
        registeredParties.forEach((i, j) -> {
            inv.addItem(j.getItem());
                }
        );
        return inv;
    }

}
