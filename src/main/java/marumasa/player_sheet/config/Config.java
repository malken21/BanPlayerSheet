package marumasa.player_sheet.config;

import marumasa.player_sheet.Minecraft;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public final String URL;
    public final message message;

    public Config(final Minecraft plugin) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        URL = config.getString("URL");

        message = new message(config);
    }
}
