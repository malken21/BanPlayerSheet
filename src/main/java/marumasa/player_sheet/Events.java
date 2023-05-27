package marumasa.player_sheet;

import com.google.gson.Gson;
import marumasa.player_sheet.config.Config;
import marumasa.player_sheet.json.isBAN;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class Events implements Listener {

    private final Config cfg;

    public Events(Config config) {
        cfg = config;
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {

        // ログインしたプレイヤーのUUID 取得
        final String LoginUUID = event.getUniqueId().toString();

        // スプレッドシートのBANリストに入っているか Getリクエストする
        final String getJSON = request.get(cfg.URL + "?type=isBAN&UUID=" + LoginUUID);

        // もし 通信エラーなどでアクセスできなかったら
        if (getJSON == null) {
            event.disallow(Result.KICK_OTHER, cfg.message.ErrorDatabase);
            return;
        }

        // Gson を使って JSONを 変換
        final isBAN getObject = new Gson().fromJson(getJSON, isBAN.class);


        if (getObject.type.equals("Error"))
            //もし Getリクエストから 送られてきた情報に エラーだと書かれていたら
            event.disallow(Result.KICK_OTHER, cfg.message.ErrorDatabase);

        else if (getObject.result)
            //もし Getリクエストから 送られてきた情報に BANリストに入っていると書かれていたら
            event.disallow(Result.KICK_OTHER, cfg.message.Kick);

    }
}
