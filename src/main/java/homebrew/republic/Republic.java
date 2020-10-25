package homebrew.republic;

import homebrew.republic.commands.PartyCommand;
import homebrew.republic.listeners.PartyCreationMenu;
import homebrew.republic.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Republic extends JavaPlugin {
    private static PartyManager pm;
    private static PartyCreationMenu pcm;

    @Override
    public void onEnable() {
        //Registers all parties in parties.yml; initializes objects in HashMap<Party party, String uuid>
        pm = new PartyManager();
        PartyManager.loadParties();

        pcm = new PartyCreationMenu(this);
        //pm.registerParties();
        // Plugin startup logic
        getCommand("party").setExecutor(new PartyCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PartyManager.saveParties();
    }

    public static JavaPlugin getInstance() {
        return (Republic) Bukkit.getPluginManager().getPlugin("Republic");
    }

    public static PartyCreationMenu getPartyMenu() {
        return pcm;
    }

}
