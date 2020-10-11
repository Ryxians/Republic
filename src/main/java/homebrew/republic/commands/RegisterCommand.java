package homebrew.republic.commands;

import homebrew.republic.RepublicPlayer;
import homebrew.republic.party.Party;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Must be a Citizen (player) to register");
            return true;
        }

        Player p = (Player) sender;
        RepublicPlayer rp = new RepublicPlayer(p);

        if(rp.isCitizen()) {

        }
        return false;
    }
}
