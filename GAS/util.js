function isParameter(item) {
    return item != undefined && item != "";
}
function isIP(ip) {
    return ip.match(/^\d{1,3}(\.\d{1,3}){3}$/);
}

function getList(sheet, calls) {
    const cache = sheet.getRange(calls).getValues();

    return cache.map(item => {
        return item[0];
    });
}
function addList(sheet, calls, ip) {
    const cache = sheet.getRange(calls).getValues();

    // すでに許可されていたら false
    if (cache.some(item => item[0] == ip)) return false;

    let index_addResult = 0;
    for (const index in cache) {
        if (cache[index][0] == "") {
            cache[index][0] = ip;
            break;
        } else {
            index_addResult = index;
        }
    }

    //行が足りなかった場合
    if (index_addResult == (cache.length - 1)) {
        sheet.insertRowAfter(cache.length);
        cache.push([ip]);
    }
    console.log(cache)
    sheet.getRange(calls).setValues(cache);
    return true;
}