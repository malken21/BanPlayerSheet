package marumasa.player_sheet.json;

public class isBAN {
    public static class req {
        public String type;
        public String uuid;

        public req(String uuid) {
            this.type = "isBAN";
            this.uuid = uuid;
        }
    }

    public static class res {
        public String type;
        public boolean result;

        public res(String type, boolean result) {
            this.type = type;
            this.result = result;
        }
    }
}
