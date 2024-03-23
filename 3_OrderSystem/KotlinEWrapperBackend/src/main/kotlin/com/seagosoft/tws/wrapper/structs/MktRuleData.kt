package com.seagosoft.tws.wrapper.structs

import com.ib.client.PriceIncrement


class MktRuleData(var marketRuleId: Int, var priceIncrements: Array<PriceIncrement>)