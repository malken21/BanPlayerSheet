//Postリクエスト
function doPost(e) {
    try {

        // POST Bodyの内容を取得 & JSON化
        const data = JSON.parse(e.postData.contents);
        //JSONをコンソール出力
        console.log(data);

        return ContentService.createTextOutput(JSON.stringify(main(data)));

    } catch (err) {
        //エラーの場合の処理

        //エラー内容をコンソール出力
        console.log(err);
        //レスポンス
        return ContentService.createTextOutput(JSON.stringify({ "type": "Error" }));
    }
}

function main(data) {

    //-----メイン処理-----start
    const spreadsheet = SpreadsheetApp.openByUrl(SpreadsheetURL);
    const sheet = spreadsheet.getSheetByName(SheetName);

    //"type" が
    switch (data.type) {

        //"isBAN" だったら プレイヤーがBANされているかどうか取得
        case "isBAN":
            return isBan(data, sheet);

        //"addBAN"だったら BANプレイヤー追加
        case "addBAN":
            return addBan(data, sheet);

        //"rmvBAN"だったら BANプレイヤー削除
        case "rmvBAN":
            return rmvBan(data, sheet);

        //どれにも当てはまらなかったら エラー
        default:
            return { "type": "Error" };
    }
    //-----メイン処理-----end
}