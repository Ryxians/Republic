package homebrew.republic.commands;

import homebrew.republic.Republic;
import homebrew.republic.listeners.PartyCreationMenu;
import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PartyCreate{

    Player player;
    String[] args;
    PartyCreationMenu pcm;

    public PartyCreate(Player player, String[] args) {
        this.player = player;
        this.args = args;
        pcm = Republic.getPartyMenu();

        Inventory partyCreation = pcm.createMenu();
        pcm.addMenu(partyCreation);
        player.openInventory(partyCreation);
        //PartyManager.registerParty(new Party(player, "Teset", Material.ACACIA_BOAT));
    }



}
