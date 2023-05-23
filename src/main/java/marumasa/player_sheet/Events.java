package marumasa.player_sheet;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import java.util.UUID;

public class Events implements Listener {

    private final Config cfg;
    private final Minecraft mc;

    public Events(Config config, Minecraft minecraft) {
        cfg = config;
        mc = minecraft;
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {

        final String LoginUUID = event.getUniqueId().toString();

        Bukkit.getLogger().info(cfg.URL + "?type=isAllow&UUID=" + LoginUUID);

        final String getJSON = request.get(cfg.URL + "?type=isAllow&UUID=" + LoginUUID);
        if (getJSON == null) {
            event.disallow(Result.KICK_OTHER, cfg.ErrorMessage);
            sendLogger(LoginUUID, cfg.ErrorMessage);
        } else if (getJSON.contains("false")) {
            event.disallow(Result.KICK_OTHER, cfg.KickMessage);
            sendLogger(LoginUUID, cfg.KickMessage);
        }
    }

    private String getIP(String data) {
        return data.split("/")[1].split(":")[0];
    }

    private void sendLogger(String LoginIP, String text) {
        mc.getLogger().info("[" + LoginIP + "] " + text);
    }
}
