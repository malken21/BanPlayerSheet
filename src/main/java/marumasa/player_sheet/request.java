package marumasa.player_sheet;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class request {

    // Getリクエスト 通信エラーなどで 接続できなかった場合は null を return する
    public static String get(String url) {
        try {

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(5000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");

            boolean redirect = false;

            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            if (redirect) {

                final String newUrl = conn.getHeaderField("Location");
                final String cookies = conn.getHeaderField("Set-Cookie");

                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setRequestProperty("Cookie", cookies);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");

            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            final StringBuilder html = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
            return html.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


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
