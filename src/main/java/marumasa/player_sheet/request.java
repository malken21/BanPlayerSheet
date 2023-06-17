package marumasa.player_sheet;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class request {


    // Postリクエスト(JSON) 通信エラーなどで 接続できなかった場合は null を return する
    public static String postJSON(String url, String json) {

        try {
            // URLオブジェクトの作成
            URL obj = new URL(url);
            // HttpURLConnectionオブジェクトの取得
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            // リクエストヘッダーの設定
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            // リクエストボディの送信を許可
            con.setDoOutput(true);

            // リクエストボディの書き込み
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // レスポンスボディの読み込み
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Bukkit.getLogger().info(response.toString());
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPlayerUUID(String playerName) {

        // MojangAPI の URL
        String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;

        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            JsonElement element = JsonParser.parseReader(reader);
            return element.getAsJsonObject().get("id").getAsString().replaceFirst(
                    // UUID ハイフン 追加
                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                    "$1-$2-$3-$4-$5"
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
