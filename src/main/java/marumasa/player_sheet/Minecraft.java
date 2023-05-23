package marumasa.player_sheet;

import org.bukkit.plugin.java.JavaPlugin;

public final class Minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config(this);
        getServer().getPluginManager().registerEvents(new Events(config, this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
