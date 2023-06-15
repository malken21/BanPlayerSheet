// 指定したUUIDがBANされているかどうか
function isBan(uuid, sheet) {

    // UUIDにある "-" を削除
    uuid = uuid.replace(/-/g, "");

    // もし パラメーターが書かれていたら
    if (isParameter(uuid)) {

        // 全てのBANしたUUIDデータ取得
        const banList = getList(sheet, Range_UUID);

        // 一致するUUIDがあったら "result" を true にする (UUIDにある全ての"-"という文字は無視して検索する)
        return { "result": banList.some(item => item.replace(/-/g, "") == uuid), "type": "OK" };

    } else {//エラー
        return { "type": "Error" };
    }
}

// BANするUUIDを追加
function addBan(uuid, sheet) {

    // UUIDにある "-" を削除
    uuid = uuid.replace(/-/g, "");

    // もし パラメーターが書かれていたら
    if (isParameter(uuid)) {

        // 全てのBANしたUUIDデータ取得
        const banList = getList(sheet, Range_UUID);

        // 一致するUUIDがあったら "result" を true にする (UUIDにある全ての"-"という文字は無視して検索する)
        return { "result": banList.some(item => item.replace(/-/g, "") == uuid), "type": "OK" };

    } else {//エラー
        return { "type": "Error" };
    }
}

// BANしたUUIDを削除
function rmvBan(ip, sheet) {

    // UUIDにある "-" を削除
    uuid = uuid.replace(/-/g, "");

    // もし パラメーターが書かれていたら
    if (isParameter(uuid)) {

        // 全てのBANしたUUIDデータ取得
        const banList = getList(sheet, Range_UUID);

        // 一致するUUIDがあったら "result" を true にする (UUIDにある全ての"-"という文字は無視して検索する)
        return { "result": banList.some(item => item.replace(/-/g, "") == uuid), "type": "OK" };

    } else {//エラー
        return { "type": "Error" };
    }
}
