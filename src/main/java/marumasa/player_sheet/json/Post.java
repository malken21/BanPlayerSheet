package marumasa.player_sheet.json;

public class Post {
    public String name;
    public String uuid;
    public String ban_reason;
    public String type;

    public Post(String name, String uuid, String ban_reason, String type) {
        this.name = name;
        this.uuid = uuid;
        this.ban_reason = ban_reason;
        this.type = type;
    }
}
