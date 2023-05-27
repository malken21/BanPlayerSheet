package marumasa.player_sheet.config;

import org.bukkit.configuration.file.FileConfiguration;

public class message {

    public final String Kick;
    public final String ErrorDatabase;
    public message(FileConfiguration config) {

        //キックした時のメッセージ
        Kick = config.getString("Message.Kick");
        //データベースにアクセスできなかった時のメッセージ
        ErrorDatabase = config.getString("Message.ErrorDatabase");
    }
}
