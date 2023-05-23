package marumasa.player_sheet;

import org.bukkit.plugin.java.JavaPlugin;

public final class Minecraft extends JavaPlugin {

    @Override
    public void onEnable() {

        // config.yml 読み込み
        Config config = new Config(this);

        //イベント登録
        getServer().getPluginManager().registerEvents(new Events(config), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
