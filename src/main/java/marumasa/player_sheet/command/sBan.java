package marumasa.player_sheet.command;

import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.Minecraft;
import marumasa.player_sheet.request;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class sBan implements CommandExecutor, TabCompleter {
    private final Config cfg;

    private final Minecraft mc;
    private final Server server;

    public sBan(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;
        server = minecraft.getServer();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player senderPlayer = (Player) sender;

        final Player BanPlayer = server.getPlayer(args[0]);

        final String BanReason = args[1];

        final String getJSON = request.get(
                cfg.URL + "?type=BAN&UUID=" + BanPlayer.getUniqueId() + "&Name=" + BanPlayer.getName() + "&BanReason=" + BanReason
        );

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            final Collection<? extends Player> onlinePlayers = mc.getServer().getOnlinePlayers();

            List<String> PlayerStringList = new ArrayList<>();
            for (Player player : onlinePlayers) PlayerStringList.add(player.getName());

            return PlayerStringList;
        } else {
            return null;
        }
    }
}
