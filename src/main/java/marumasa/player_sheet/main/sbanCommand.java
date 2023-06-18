package marumasa.player_sheet.main;

import com.google.gson.Gson;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.json.addBAN;
import marumasa.player_sheet.request;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class sbanCommand extends Thread {

    private final Config cfg;
    private final Logger log;
    private final CommandSender sender;
    private final Gson gson;
    private final String[] args;

    private final Server server;

    public sbanCommand(Config config, Logger logger, CommandSender commandSender, Gson gson, String[] args, Server server) {
        cfg = config;
        log = logger;
        sender = commandSender;
        this.gson = gson;
        this.args = args;
        this.server = server;
    }

    public void run() {

        if (args.length == 0) {
            sender.sendMessage(cfg.message.sBan);
            // 引数がひとつもない場合
            return;
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
            return;
        }

        // BAN理由
        final String BanReason = String.join(" ", argList);

        log.info("----------sBan----------");
        log.info("BanPlayerName: " + BanPlayerName);
        log.info("BanPlayerUUID: " + BanPlayerUUID);
        log.info("BanReason: " + BanReason);
        log.info("----------sBan----------");

        final addBAN.req postJSON = new addBAN.req(BanPlayerName, BanPlayerUUID, BanReason);

        // JSON 変換
        String json = gson.toJson(postJSON);

        // Post通信で データを送信する
        json = request.postJSON(cfg.URL, json);

        if (json == null) {// null だったら
            sender.sendMessage(cfg.message.ErrorDatabase);
            return;
        }

        // JSON 変換
        final addBAN.res response = gson.fromJson(json, addBAN.res.class);

        if (response == null || response.type.equals("Error")) {// エラーだったら
            sender.sendMessage(cfg.message.ErrorDatabase);
            return;
        }

        switch (response.result) {
            case 0 -> {
                sender.sendMessage(cfg.message.addBAN);// 0 の場合は 成功メッセージ
                final Player BanPlayer = server.getPlayer(BanPlayerName);
                if (BanPlayer == null) return;
                BanPlayer.kickPlayer(cfg.message.Kick);
            }
            case 1 -> sender.sendMessage(cfg.message.alreadyBAN);// 1 の場合は 既にBANしている
        }
    }
}
