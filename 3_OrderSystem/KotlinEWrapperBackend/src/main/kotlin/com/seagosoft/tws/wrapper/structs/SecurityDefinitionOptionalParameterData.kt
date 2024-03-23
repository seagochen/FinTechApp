package com.seagosoft.tws.wrapper.structs

class SecurityDefinitionOptionalParameterData(
    var reqId: Int,
    var exchange: String,
    var underlyingConId: Int,
    var tradingClass: String,
    var multiplier: String,
    var expirations: Set<*>,
    var strikes: Set<*>
)