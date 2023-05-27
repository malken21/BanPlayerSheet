package marumasa.player_sheet.command;

import marumasa.player_sheet.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unsBan implements CommandExecutor {
    private final Config cfg;

    public unsBan(Config config) {
        cfg = config;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player senderPlayer = (Player) sender;
        return true;
    }
}
