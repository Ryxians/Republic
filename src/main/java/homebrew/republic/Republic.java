package homebrew.republic;

import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Republic extends JavaPlugin {

    @Override
    public void onEnable() {
        //Registers all parties in parties.yml; initializes objects in HashMap<Party party, String uuid>
        PartyManager pm = new PartyManager();
        pm.registerParties();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() {
        return (Republic) Bukkit.getPluginManager().getPlugin("Republic");
    }

}
