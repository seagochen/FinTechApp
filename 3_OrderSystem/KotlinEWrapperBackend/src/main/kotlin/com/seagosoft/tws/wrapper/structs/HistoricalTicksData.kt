package com.seagosoft.tws.wrapper.structs

import com.ib.client.HistoricalTick


class HistoricalTicksData(var reqId: Int, var ticks: List<HistoricalTick>, var done: Boolean)