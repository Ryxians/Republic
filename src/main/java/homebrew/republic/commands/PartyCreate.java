package homebrew.republic.commands;

import homebrew.republic.Republic;
import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PartyCreate{

    Player player;
    String[] args;
    public PartyCreate(Player player, String[] args) {
        this.player = player;
        this.args = args;
        PartyManager.registerParty(new Party(player, "Teset", Material.ACACIA_BOAT));
    }



}
