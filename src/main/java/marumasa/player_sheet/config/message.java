package marumasa.player_sheet.config;

import org.bukkit.configuration.file.FileConfiguration;

public class message {

    public final String BanReason;
    public final String Kick;
    public final String ErrorDatabase;
    public final String sBan;
    public final String unsBan;
    public final String PlayerNotFound;
    public message(FileConfiguration config) {

        //BAN理由
        BanReason = config.getString("Message.BanReason");
        //キックした時のメッセージ
        Kick = config.getString("Message.Kick");
        //データベースにアクセスできなかった時のメッセージ
        ErrorDatabase = config.getString("Message.ErrorDatabase");

        // /sban の使い方
        sBan = config.getString("Message.sBan");
        // /unsban の使い方
        unsBan = config.getString("Message.unsBan");

        // プレイヤーが見つからなかった
        PlayerNotFound = config.getString("Message.PlayerNotFound");
    }
}
