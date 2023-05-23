function ip_isAllow(ip, sheet) {
    if (isParameter(ip)) {
        const allow_IP_list = getList(sheet, Cells); // 全ての許可したIPデータ取得
        return { "result": allow_IP_list.some(item => item == ip), "type": "OK" };

    } else {//エラー
        return { "type": "Error" };
    }
}

function ip_addAllow(ip, sheet) {
    if (!AddIP) return { "type": "Error" };
    if (isParameter(ip)) { // 許可するIPを追加
        if (isIP(ip) && addList(sheet, Cells, ip)) {
            return { "type": "OK" };
        } else {//エラー
            return { "type": "Error" };
        }

    } else {//エラー
        return { "type": "Error" };
    }
}
