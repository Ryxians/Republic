package homebrew.republic.party;

import homebrew.republic.ConfigAccessor;
import homebrew.republic.Republic;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PartyManager {
    //STATIC
    static final int MAX_PARTIES = 9;
    static HashMap<String, Party> registeredParties = new HashMap<>();
    static final ConfigAccessor partiesConfigAccessor = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    static ConfigurationSection partyConfigRoot = partiesConfigAccessor.getConfig().getConfigurationSection("parties");


    public PartyManager() {
        registerParties();
    }


    public void registerParties() {
        for(String partyUUIDString : partyConfigRoot.getKeys(false)) {
            registeredParties.put(partyUUIDString, new Party(partyConfigRoot.getString(partyUUIDString + ".name")));
        }
    }


    private void registerParty(String partyUUIDString, Party party) {
        registeredParties.put(partyUUIDString, party);
    }


    public void createParty(Player partyFounder, String partyName, List<String> policies, Material partyItem) {
        int counter = 0;
        ConfigurationSection partySec;
        for(String party : partyConfigRoot.getKeys(false)) {
            partySec = partiesConfigAccessor.getConfig().getConfigurationSection("parties." + party);
            if(partySec.getString("name").equals(partyName)) {
                partyFounder.sendMessage(ChatColor.RED + "A party already exists with that name.");
                return;
            }
            counter++;
        }
        if(counter < 9) {
            UUID partyUUID = UUID.randomUUID();
            String partyUUIDString = partyUUID.toString();
            partyConfigRoot.createSection(partyUUIDString);
            partyConfigRoot.set(partyUUIDString + ".name", partyName);
            partyConfigRoot.set(partyUUIDString + ".founder", partyFounder.getName());
            partyConfigRoot.set(partyUUIDString + ".material", partyItem.toString());
            for(String policy : policies) {
                partyConfigRoot.getStringList(partyUUIDString + ".policies").add(policy);
            }
            partiesConfigAccessor.saveConfig();
            registerParty(partyUUIDString, new Party(partyConfigRoot.getString(partyUUIDString + ".name")));
        } else {
            partyFounder.sendMessage(ChatColor.RED + "Sorry, there are already 9 major parties.");
        }
    }


    public void deleteParty(Party party) {
        //TODO remove players from party, then delete

        getPartyConfigRoot().set(party.getPartyUUIDString(), null);
        partiesConfigAccessor.saveConfig();
    }


    public boolean isRegistered(Party party) {
        return registeredParties.containsKey(party);
    }


    //Getters
    //Return party object from party name
    public Party getParty(String name) {
        ConfigurationSection partySec;
        for(String partyUUIDString : partyConfigRoot.getKeys(false)) {
            partySec = partiesConfigAccessor.getConfig().getConfigurationSection(partyUUIDString);
            if(partySec.getString("name").equals(name)) {
                return registeredParties.get(partyUUIDString);
            }
        }
        return null;
    }


    public String getPartyUUIDString(Party party) {
        for(Map.Entry<String, Party> entry : registeredParties.entrySet()) {
            if(entry.getValue().equals(party)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public void joinParty(Player p) {
        //TODO
    }


    public static ConfigurationSection getPartyConfigRoot() {
        return partyConfigRoot;
    }

}
