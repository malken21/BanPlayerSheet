// 指定したUUIDがBANされているかどうか
function isBan(data, sheet) {

    // UUIDにある "-" を削除
    const uuid = data.uuid.replace(/-/g, "");

    // 全てのBANしたUUIDデータ取得
    const banListUUID = sheet.getRange(RangeUUID).getValues().map(item => {
        return item[0];
    });

    // 一致するUUIDがあったら "result" を true にする (UUIDにある全ての"-"という文字は無視して検索する)
    return { "result": banListUUID.some(item => item.replace(/-/g, "") == uuid), "type": "OK" };
}

// BANするUUIDを追加
function addBan(data, sheet) {

    // UUIDにある "-" を削除
    const uuid = data.uuid.replace(/-/g, "");

    const range = sheet.getRange(Range);

    // 全てのBANデータ取得
    const banList = range.getValues();

    // 一致するUUIDがあったら result を 1にして return
    if (banList.some(item => item[1].replace(/-/g, "") == uuid))
        return { "result": 1, "type": "OK" };


    // 追加するBANデータの配列
    const banData = [
        data.name,
        uuid.replace(/^(\w{8})(\w{4})(\w{4})(\w{4})(\w{12})$/, '$1-$2-$3-$4-$5'),
        data.ban_reason
    ];


    let index_addResult = 0;
    for (const index in banList) {
        if (banList[index][0] == "") {
            // BANデータを追加
            banList[index] = banData;
            break;
        } else {
            index_addResult = index;
        }
    }

    //行が足りなかった場合 追加
    if (index_addResult == (banList.length - 1)) {
        sheet.insertRowAfter(banList.length);
        // BANデータを追加
        banList.push(banData);
    }

    console.log(banList);
    range.setValues(banList);

    return { "result": 0, "type": "OK" };
}

// BANしたUUIDを削除
function rmvBan(data, sheet) {

    // UUIDにある "-" を削除
    const uuid = data.uuid.replace(/-/g, "");

    const range = sheet.getRange(Range);

    // 全てのBANデータ取得
    let banList = range.getValues();

    let isChange = false;

    // 一致するUUIDがあったら 書いてあるもの削除
    banList = banList.map(item => {
        if (item[1].replace(/-/g, "") == uuid) {
            isChange = true;
            return ["", "", ""];
        }
        return item;
    });

    if (isChange) {
        range.setValues(banList);
        return { "result": 0, "type": "OK" };
    }
    return { "result": 1, "type": "OK" };
}
