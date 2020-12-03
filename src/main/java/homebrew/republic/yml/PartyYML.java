package homebrew.republic.yml;

import homebrew.republic.ConfigAccessor;
import homebrew.republic.Republic;
import homebrew.republic.interfaces.PartyConfig;
import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.UUID;

public class PartyYML implements PartyConfig {

    private final ConfigAccessor partiesConfigAccessor = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    private ConfigurationSection partyConfigRoot = partiesConfigAccessor.getConfig();

    public PartyYML() {
    }

    @Override
    public boolean saveParties(HashMap<String, Party> registeredParties) {
        registeredParties.forEach((i, j) -> {
            String str = j.getUniqueId().toString();
            partyConfigRoot.set(str + ".description", j.getDescription());
            partyConfigRoot.set(str + ".founder", j.getFounderUUID().toString());
            partyConfigRoot.set(str + ".name", j.getName());
            partyConfigRoot.set(str + ".material", j.getMaterial().toString());
        });
        partiesConfigAccessor.saveConfig();
        return true;
    }

    @Override
    public void loadParties() {
        partyConfigRoot.getKeys(false).forEach((i) -> {
            String str = i.toString();
            UUID founder = UUID.fromString((String) partyConfigRoot.get(str + ".founder"));
            String name = (String) partyConfigRoot.get(str + ".name");
            Material mat = Material.getMaterial((String) partyConfigRoot.get(str + ".material"));
            String desc = (String) partyConfigRoot.get(str + ".description");
            Party party = new Party(UUID.fromString(i), founder, name, mat, desc);
            PartyManager.registerParty(party);
        });
    }

    @Override
    public boolean deleteParty(Party party) {
        //TODO remove players from party, then delete
        partyConfigRoot.set(party.getPartyUUIDString(), null);
        partiesConfigAccessor.saveConfig();
        return true;
    }

    @Override
    public boolean updatePartyName(Party party, String name) {
        // TODO Update Party Name
        // partyConfigRoot.set();
        return false;
    }
}
