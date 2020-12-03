package homebrew.republic.interfaces;

import homebrew.republic.party.Party;

import java.util.HashMap;

public interface PartyConfig {
    public boolean saveParties(HashMap<String, Party> registeredParties);

    public void loadParties();

    public boolean deleteParty(Party party);

    public boolean updatePartyName(Party party, String name);
}
