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
import java.util.concurrent.atomic.AtomicInteger;

public class PartyYML implements PartyConfig {

    private final ConfigAccessor partiesConfigAccessor = new ConfigAccessor(Republic.getInstance(), "Parties.yml");
    private ConfigurationSection partyConfigRoot = partiesConfigAccessor.getConfig();

    public PartyYML() {
    }

    @Override
    public boolean saveParties(HashMap<String, Party> registeredParties) {
        registeredParties.forEach((i, j) -> {
            int id = j.getID();
            partyConfigRoot.set(id + ".description", j.getDescription());
            partyConfigRoot.set(id + ".founder", j.getFounderUUID().toString());
            partyConfigRoot.set(id + ".name", j.getName());
            partyConfigRoot.set(id + ".material", j.getMaterial().toString());
        });
        partiesConfigAccessor.saveConfig();
        return true;
    }

    @Override
    public void loadParties() {

        partyConfigRoot.getKeys(false).forEach((i) -> {
            int id = Integer.parseInt(i);
            UUID founder = UUID.fromString((String) partyConfigRoot.get(id + ".founder"));
            String name = (String) partyConfigRoot.get(id + ".name");
            Material mat = Material.getMaterial((String) partyConfigRoot.get(id + ".material"));
            String desc = (String) partyConfigRoot.get(id + ".description");
            Party party = new Party(id, founder, name, mat, desc);
            PartyManager.registerParty(party);
            if (PartyManager.getTopID(false) < id) {
                PartyManager.setTOPID(id);
            }
        });

    }

    @Override
    public boolean deleteParty(Party party) {
        //TODO remove players from party, then delete
        partyConfigRoot.set(party.getPartyIDString(), null);
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
