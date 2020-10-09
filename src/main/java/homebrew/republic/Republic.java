package homebrew.republic;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Republic extends JavaPlugin {

    @Override
    public void onEnable() {
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
