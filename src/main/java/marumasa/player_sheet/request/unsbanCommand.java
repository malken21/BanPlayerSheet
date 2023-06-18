package marumasa.player_sheet.request;

import com.google.gson.Gson;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.json.rmvBAN;
import org.bukkit.command.CommandSender;

public class unsbanCommand extends Thread {

    private final String BanPlayerUUID;
    private final Config cfg;
    private final CommandSender sender;
    private final Gson gson;

    public unsbanCommand(String BanPlayerUUID, Config config, CommandSender commandSender, Gson gson) {
        this.BanPlayerUUID = BanPlayerUUID;
        cfg = config;
        sender = commandSender;
        this.gson = gson;
    }

    public void run() {
        final rmvBAN.req postJSON = new rmvBAN.req(BanPlayerUUID);

        // JSON 変換
        String json = gson.toJson(postJSON);

        // Post通信で データを送信する
        json = http.postJSON(cfg.URL, json);

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