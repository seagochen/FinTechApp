package com.seagosoft.tws.wrapper.structs

import com.ib.client.HistogramEntry


class HistogramData(var requestId: Int, var list: List<HistogramEntry>)