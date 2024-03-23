package com.seagosoft.tws.wrapper.structs

import com.ib.client.HistoricalTickLast


class HistoricalTicksLastData(var reqId: Int, var ticks: List<HistoricalTickLast>, var done: Boolean)