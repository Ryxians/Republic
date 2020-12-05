package homebrew.republic.party;

import homebrew.republic.ConfigAccessor;
import homebrew.republic.Election;
import homebrew.republic.Republic;
import homebrew.republic.interfaces.Electable;
import homebrew.republic.interfaces.PartyConfig;
import homebrew.republic.yml.PartyYML;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PartyManager {
    // STATIC
    private static boolean init = false;
    static final int MAX_PARTIES = 9;
    static HashMap<String, Party> registeredParties = new HashMap<>();
    static Election election;
    private static int TOPID;

    // Party Creation Inventories
    static List<Inventory> inventories = new LinkedList<Inventory>();

    private static PartyConfig config;


    public PartyManager() {
        init();
    }

    // Initialize without constructing.
    public static void init() {
        // Future implementation of other config types
        if (true) {
            config = new PartyYML();
        }
        //STATIC
        init = true;
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

        config.saveParties(registeredParties);
    }

    public static void loadParties() {

        config.loadParties();
    }


    public void deleteParty(Party party) {

        config.deleteParty(party);
    }


    public boolean isRegistered(Party party) {

        return registeredParties.containsKey(party);
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
        Bukkit.getServer().broadcastMessage("The winner: " + election.getBest());
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

    public static PartyConfig getConfig() {
        return config;
    }

    public static int getTOPID() {
        return getTopID(true);
    }

    public static int getTopID(boolean increment) {
        if (increment) {
            return TOPID++;
        } else {
            return TOPID;
        }
    }

    public static void setTOPID(int id) {
        TOPID = id;
    }

}
