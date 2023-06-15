function doGet(e) {
    try {
        const type = e.parameter.type;
        const uuid = e.parameter.UUID;

        return ContentService.createTextOutput(JSON.stringify(main(type, uuid)));
    } catch (err) {
        console.log(err);
        return ContentService.createTextOutput(JSON.stringify({ "type": "Error" }));
    }
}

function main(type, uuid) {
    if (isParameter(type)) {

        //-----メイン処理-----start
        const spreadsheet = SpreadsheetApp.openByUrl(SpreadsheetURL);
        const sheet = spreadsheet.getSheetByName(SheetName);

        //リクエストパラメーター "type" が
        switch (type) {

            //"isBAN" だったら プレイヤーがBANされているかどうか取得
            case "isBAN":
                return isBan(uuid, sheet);

            //"addBAN"だったら BANプレイヤー追加
            case "addBAN":
                return addBan(uuid, sheet);

            //"rmvBAN"だったら BANプレイヤー削除
            case "rmvBAN":
                return rmvBan(uuid, sheet);

            //どれにも当てはまらなかったら エラー
            default:
                return { "type": "Error" };
        }
        //-----メイン処理-----end

    } else {//エラー
        return { "type": "Error" };
    }
}

//テスト用
function test() {
    console.log(main("isBAN", "069a79f4-44e9-4726-a5be-fca90e38aaf5"))
}