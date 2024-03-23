package com.seagosoft.tws.wrapper.structs

class HistoricalScheduleData(
    var reqId: Int,
    var startDateTime: String,
    var endDateTime: String,
    var timeZone: String,
    var sessions: List<*>
)