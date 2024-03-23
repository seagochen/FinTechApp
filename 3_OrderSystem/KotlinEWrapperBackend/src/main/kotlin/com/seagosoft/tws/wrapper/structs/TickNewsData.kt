package com.seagosoft.tws.wrapper.structs


class TickNewsData(
    var tickerId: Int, var timeStamp: Long, var providerCode: String,
    var articleId: String, var headline: String, var extraData: String
)