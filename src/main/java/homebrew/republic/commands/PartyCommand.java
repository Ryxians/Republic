package homebrew.republic.commands;

import homebrew.republic.party.Party;
import homebrew.republic.party.PartyManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if(args.length < 1) {
            sender.sendMessage(ChatColor.BLUE + "#--------------|Party Help|--------------#");
            sender.sendMessage("/party join");
            sender.sendMessage("/party create");
            sender.sendMessage("/party leave");
        } else {
            switch (args[0]) {
                case "join":
                    player.openInventory(PartyManager.getPartyView());
                    break;
                case "create":
                    new PartyCreate(player, args);
                    break;
                case "test":
                default:
                    sender.sendMessage(ChatColor.RED + "Not a valid command.");
            }
        }
        return true;

    }
}
