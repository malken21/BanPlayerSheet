package marumasa.player_sheet.command;

import com.google.gson.Gson;
import marumasa.player_sheet.Minecraft;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.request.http;
import marumasa.player_sheet.request.unsbanCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class unsBan implements CommandExecutor, TabCompleter {
    private final Config cfg;
    private final Minecraft mc;
    private final Server server;
    private final Logger logger;
    private final Gson gson = new Gson();

    public unsBan(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;
        server = mc.getServer();
        logger = mc.getLogger();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

        if (args.length == 0) {
            // 引数がひとつもない場合
            sender.sendMessage(cfg.message.unsBan);
            return false;
        }

        final ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));

        // BANプレイヤーの名前
        // remove を することで 同時に "argList" から BANプレイヤーの名前 を削除
        final String BanPlayerName = argList.remove(0);

        // BANプレイヤーのUUID
        final String BanPlayerUUID = http.getPlayerUUID(BanPlayerName);

        // プレイヤーが見つからなかった場合
        if (BanPlayerUUID == null) {
            sender.sendMessage(cfg.message.PlayerNotFound);
            return false;
        }

        logger.info("----------unsBan----------");
        logger.info("BanPlayerName: " + BanPlayerName);
        logger.info("BanPlayerUUID: " + BanPlayerUUID);
        logger.info("----------unsBan----------");

        new unsbanCommand(BanPlayerUUID, cfg, sender, gson).start();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            //BANしたいプレイヤー

            List<String> PlayerStringList = new ArrayList<>();

            //オフライン プレイヤー
            final OfflinePlayer[] offlinePlayers = server.getOfflinePlayers();
            for (OfflinePlayer player : offlinePlayers) PlayerStringList.add(player.getName());

            //オンライン プレイヤー
            final Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();
            for (Player player : onlinePlayers) {
                final String name = player.getName();
                if (!PlayerStringList.contains(name)) PlayerStringList.add(name);
            }

            return PlayerStringList;
        } else {
            //その他
            return null;
        }
    }
}
