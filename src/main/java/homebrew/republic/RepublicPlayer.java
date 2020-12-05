package homebrew.republic;

import homebrew.republic.interfaces.Electable;
import homebrew.republic.party.Party;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RepublicPlayer {

    protected Player player;
    public RepublicPlayer(Player player) {
        this.player = player;
    }


    //If player has Citizen group (may or may not belong to party)
    public boolean isCitizen() {
        return player.hasPermission("republic.citizen");
    }

    public boolean isRegistered() {
        //If player belongs a party
        return true;
    }

    public boolean isAdmin() {
        return false;
    }
}
