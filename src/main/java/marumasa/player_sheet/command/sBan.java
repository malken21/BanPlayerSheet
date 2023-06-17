package marumasa.player_sheet.command;

import com.google.gson.Gson;
import marumasa.player_sheet.Minecraft;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.json.addBAN;
import marumasa.player_sheet.request;
import marumasa.player_sheet.run.sbanCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class sBan implements CommandExecutor, TabCompleter {
    private final Config cfg;
    private final Minecraft mc;
    private final Logger logger;
    private final Gson gson = new Gson();

    public sBan(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;
        logger = mc.getLogger();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        new sbanCommand(cfg, logger, sender, gson, args).start();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            //BANしたいプレイヤー
            final OfflinePlayer[] players = mc.getServer().getOfflinePlayers();

            List<String> PlayerStringList = new ArrayList<>();
            for (OfflinePlayer player : players) PlayerStringList.add(player.getName());

            return PlayerStringList;
        } else if (args.length == 2) {
            //BAN理由
            return Collections.singletonList(cfg.message.BanReason);
        } else {
            //その他
            return null;
        }
    }
}
