package marumasa.player_sheet;

import marumasa.player_sheet.command.sBan;
import marumasa.player_sheet.command.unsBan;
import marumasa.player_sheet.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minecraft extends JavaPlugin {

    @Override
    public void onEnable() {

        // config.yml 読み込み
        Config config = new Config(this);

        //イベント登録
        getServer().getPluginManager().registerEvents(new Events(config), this);

        // コマンド登録 sban
        getCommand("sban").setExecutor(new sBan(config, this));

        // コマンド登録 unsban
        getCommand("unsban").setExecutor(new unsBan(config, this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
