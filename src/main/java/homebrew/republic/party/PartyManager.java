package homebrew.republic.party;

import homebrew.republic.ConfigAccessor;
import homebrew.republic.Election;
import homebrew.republic.Republic;
import homebrew.republic.interfaces.Electable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PartyManager {
    //STATIC
    static final int MAX_PARTIES = 9;
    static HashMap<String, Party> registeredParties = new HashMap<>();
    static final ConfigAccessor partiesConfigAccessor = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    static ConfigurationSection partyConfigRoot = partiesConfigAccessor.getConfig();
    static Election election;

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
            partyConfigRoot.set(str + ".description", j.getDescription());
            partyConfigRoot.set(str + ".founder", j.getFounderUUID().toString());
            partyConfigRoot.set(str + ".name", j.getName());
            partyConfigRoot.set(str + ".material", j.getMaterial().toString());
        });
        partiesConfigAccessor.saveConfig();
    }

    public static void loadParties() {
        partyConfigRoot.getKeys(false).forEach((i) -> {
            String str = i.toString();
            UUID founder = UUID.fromString((String) partyConfigRoot.get(str + ".founder"));
            String name = (String) partyConfigRoot.get(str + ".name");
            Material mat = Material.getMaterial((String) partyConfigRoot.get(str + ".material"));
            String desc = (String) partyConfigRoot.get(str + ".description");
            Party party = new Party(UUID.fromString(i), founder, name, mat, desc);
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

    private static Election createElection() {
        Election election = new Election(Republic.getInstance());
        registeredParties.forEach((i, j) -> {
            election.register(j);
        });
        return election;
    }

    public static void addVote(Party party) {
        if (election == null) {
            election = createElection();
        }
        election.vote(party);
        getWinner();
    }

    public static void getWinner() {
        Bukkit.getServer().broadcastMessage("The winner: " + election.getBest().toString());
    }

    public static Party getParty(String name) {
        Party rc = null;
        for (Map.Entry<String, Party> entry : registeredParties.entrySet()) {
            if (entry.getKey().equals(name)) {
                rc = entry.getValue();
                break;
            }
        }
        return rc;
    }

}
