package marumasa.player_sheet.json.request;

public class sBAN {
    public String name;
    public String uuid;
    public String ban_reason;
    public boolean result;

    public sBAN(String name, String uuid, String ban_reason, boolean result) {
        this.name = name;
        this.uuid = uuid;
        this.ban_reason = ban_reason;
        this.result = result;
    }
}
