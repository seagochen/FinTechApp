package com.seagosoft.tws.wrapper.structs

import com.ib.client.TickAttrib


// You would also need a class to represent the data structure
class TickPriceData(var reqId: Int, var field: Int, var price: Double, var attrib: TickAttrib)