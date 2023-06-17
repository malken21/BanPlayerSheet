package marumasa.player_sheet.json;

public class rmvBAN {
    public static class req {
        public String type;
        public String uuid;

        public req(String uuid) {
            this.type = "rmvBAN";
            this.uuid = uuid;
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
