package marumasa.player_sheet.request;

import com.google.gson.Gson;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.json.addBAN;
import org.bukkit.command.CommandSender;

public class sbanCommand extends Thread {

    private final String BanPlayerName;
    private final String BanPlayerUUID;
    private final String BanReason;
    private final Gson gson;
    private final Config cfg;
    private final CommandSender sender;

    public sbanCommand(String BanPlayerName, String BanPlayerUUID, String BanReason, Gson gson, Config config, CommandSender sender) {
        this.BanPlayerName = BanPlayerName;
        this.BanPlayerUUID = BanPlayerUUID;
        this.BanReason = BanReason;
        this.gson = gson;
        this.cfg = config;
        this.sender = sender;
    }

    public void run() {

        final addBAN.req postJSON = new addBAN.req(BanPlayerName, BanPlayerUUID, BanReason);

        // JSON 変換
        String json = gson.toJson(postJSON);

        // Post通信で データを送信する
        json = http.postJSON(cfg.URL, json);

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
            case 0 -> sender.sendMessage(cfg.message.addBAN);// 0 の場合は 成功メッセージ
            case 1 -> sender.sendMessage(cfg.message.alreadyBAN);// 1 の場合は 既にBANしている
        }
    }
}
