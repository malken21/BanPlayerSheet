package marumasa.player_sheet.command;

import marumasa.player_sheet.Minecraft;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.request;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class unsBan implements CommandExecutor, TabCompleter {
    private final Config cfg;
    private final Minecraft mc;
    private final Logger logger;

    public unsBan(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;
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
        final String BanPlayerUUID = request.getPlayerUUID(BanPlayerName);

        // プレイヤーが見つからなかった場合
        if (BanPlayerUUID == null) {
            sender.sendMessage(cfg.message.PlayerNotFound);
            return false;
        }

        logger.info("----------unsBan----------");
        logger.info("BanPlayerName: " + BanPlayerName);
        logger.info("BanPlayerUUID: " + BanPlayerUUID);
        logger.info("----------unsBan----------");


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            //BAN解除したいプレイヤー
            final OfflinePlayer[] players = mc.getServer().getOfflinePlayers();

            List<String> PlayerStringList = new ArrayList<>();
            for (OfflinePlayer player : players) PlayerStringList.add(player.getName());

            return PlayerStringList;
        } else {
            //その他
            return null;
        }
    }
}
