package homebrew.republic.party;

import homebrew.republic.ConfigAccessor;
import homebrew.republic.Republic;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class PartyManager {
    //STATIC
    static final int MAX_PARTIES = 9;
    static HashMap<String, Party> registeredParties = new HashMap<>();
    static final ConfigAccessor partiesConfigAccessor = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    static ConfigurationSection partyConfigRoot = partiesConfigAccessor.getConfig();


    public PartyManager() {
    }

    public static void registerParty(Party party) {
        registeredParties.put(party.getName(), party);
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
            Player founder = (Player) partyConfigRoot.get(str + ".founder");
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


    public void joinParty(Player p) {
        //TODO
    }


    public static ConfigurationSection getPartyConfigRoot() {
        return partyConfigRoot;
    }

}
