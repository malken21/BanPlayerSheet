function doGet(e) {
    try {
        const type = e.parameter.type;
        const ip = e.parameter.ip;

        return ContentService.createTextOutput(JSON.stringify(main(type, ip)));
    } catch (err) {
        console.log(err);
        return ContentService.createTextOutput(JSON.stringify({ "type": "Error" }));
    }
}

function main(type, ip) {
    if (isParameter(type)) {

        //-----メイン処理-----start
        const spreadsheet = SpreadsheetApp.openById(SpreadsheetID);
        const sheet = spreadsheet.getSheetByName(SheetName);
        switch (type) {
            case "isAllow":
                return ip_isAllow(ip, sheet);
            case "addAllow":
                return ip_addAllow(ip, sheet);
            default:
                return { "type": "Error" };
        }
        //-----メイン処理-----end

    } else {//エラー
        return { "type": "Error" };
    }
}
function test() {
    console.log(main("isAllow", "192.168.0.1"))
}