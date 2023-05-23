package marumasa.player_sheet;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public final String URL;
    public final String KickMessage;
    public final String ErrorMessage;

    public Config(final Minecraft plugin) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        URL = config.getString("URL");
        KickMessage = config.getString("KickMessage");
        ErrorMessage = config.getString("ErrorMessage");
    }
}
