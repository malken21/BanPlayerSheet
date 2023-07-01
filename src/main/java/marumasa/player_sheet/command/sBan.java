package marumasa.player_sheet.command;

import com.google.gson.Gson;
import marumasa.player_sheet.Minecraft;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.request.http;
import marumasa.player_sheet.request.sbanCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Logger;

public class sBan implements CommandExecutor, TabCompleter {
    private final Config cfg;
    private final Minecraft mc;
    private final Logger logger;
    private final Gson gson = new Gson();
    private final Server server;

    public sBan(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;
        logger = mc.getLogger();
        server = mc.getServer();
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {

        if (args.length == 0) {
            sender.sendMessage(cfg.message.sBan);
            // 引数がひとつもない場合
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

        // BAN理由
        final String BanReason = String.join(" ", argList);

        logger.info("----------sBan----------");
        logger.info("BanPlayerName: " + BanPlayerName);
        logger.info("BanPlayerUUID: " + BanPlayerUUID);
        logger.info("BanReason: " + BanReason);
        logger.info("----------sBan----------");

        new sbanCommand(BanPlayerName, BanPlayerUUID, BanReason, gson, cfg, sender).start();

        final Player BanPlayer = server.getPlayer(BanPlayerName);
        if (BanPlayer == null) return true;
        BanPlayer.kickPlayer(cfg.message.Kick);
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
        } else if (args.length == 2) {
            //BAN理由
            return Collections.singletonList(cfg.message.BanReason);
        } else {
            //その他
            return null;
        }
    }
}
