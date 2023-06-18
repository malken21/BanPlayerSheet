package marumasa.player_sheet.main;

import com.google.gson.Gson;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.json.rmvBAN;
import marumasa.player_sheet.request;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class unsbanCommand extends Thread {

    private final Config cfg;
    private final Logger log;
    private final CommandSender sender;
    private final Gson gson;
    private final String[] args;

    public unsbanCommand(Config config, Logger logger, CommandSender commandSender, Gson gson, String[] args) {
        cfg = config;
        log = logger;
        sender = commandSender;
        this.gson = gson;
        this.args = args;
    }

    public void run() {
        if (args.length == 0) {
            // 引数がひとつもない場合
            sender.sendMessage(cfg.message.unsBan);
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

        log.info("----------unsBan----------");
        log.info("BanPlayerName: " + BanPlayerName);
        log.info("BanPlayerUUID: " + BanPlayerUUID);
        log.info("----------unsBan----------");

        final rmvBAN.req postJSON = new rmvBAN.req(BanPlayerUUID);

        // JSON 変換
        String json = gson.toJson(postJSON);

        // Post通信で データを送信する
        json = request.postJSON(cfg.URL, json);

        if (json == null) {// null だったら
            sender.sendMessage(cfg.message.ErrorDatabase);
            return;
        }

        // JSON 変換
        final rmvBAN.res response = gson.fromJson(json, rmvBAN.res.class);

        if (response == null || response.type.equals("Error")) {// エラーだったら
            sender.sendMessage(cfg.message.ErrorDatabase);
            return;
        }

        switch (response.result) {
            case 0 -> sender.sendMessage(cfg.message.rmvBAN);// 0 の場合は 成功メッセージ
            case 1 -> sender.sendMessage(cfg.message.notBAN);// 1 の場合は BANされていない
        }
    }
}