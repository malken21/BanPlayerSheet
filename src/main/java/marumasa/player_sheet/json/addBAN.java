package marumasa.player_sheet.json;

public class addBAN {
    public static class req {
        public String name;
        public String uuid;
        public String ban_reason;
        public String type;


        public req(String name, String uuid, String ban_reason) {
            this.name = name;
            this.uuid = uuid;
            this.ban_reason = ban_reason;
            this.type = "addBAN";
        }
    }

    public static class res {
        public String type;
        public int result;

        public res(String type, int result) {
            this.type = type;
            this.result = result;
        }
    }
}
