package com.seagosoft.tws.wrapper.structs

import com.ib.client.Contract
import com.ib.client.Execution


class ExecDetailsData(var reqId: Int, var contract: Contract, var execution: Execution)