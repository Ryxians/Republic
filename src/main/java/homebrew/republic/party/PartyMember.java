package homebrew.republic.party;

import homebrew.republic.RepublicPlayer;
import org.bukkit.entity.Player;

public class PartyMember extends RepublicPlayer {

    public PartyMember(Player player) {
        super(player);
    }


    public boolean isMemberOf(Party party) {
        return true;
    }

    public boolean isFounder(Party party) {
        return (player.getName().equals(party.getPartyFounder()));
    }

    /*TODO
    public Party getPlayerParty(Player player) {

   }*/


}
