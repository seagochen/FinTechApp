- [Order Management](#order-management)
  - [Commission Report](#commission-report)
  - [Execution Details](#execution-details)
    - [The Execution Object](#the-execution-object)
    - [Request Execution Details](#request-execution-details)
    - [Receive Execution Details](#receive-execution-details)
  - [Open Orders](#open-orders)
  - [Order Status](#order-status)
    - [Understanding Order Status](#understanding-order-status)
  - [Requesting Currently Active Orders](#requesting-currently-active-orders)
    - [API client's orders](#api-clients-orders)
    - [All submitted orders](#all-submitted-orders)
    - [Manually Submitted TWS Orders](#manually-submitted-tws-orders)
    - [Order Binding Notification](#order-binding-notification)


# Order Management

## Commission Report

当订单完全或部分成交时，`IBApi.EWrapper.execDetails` 和 `IBApi.EWrapper.commissionReport` 事件将传递 `IBApi.Execution` 和 `IBApi.CommissionReport` 对象。这允许您获取订单执行的完整情况以及产生的佣金。

执行分配订单的顾问（Advisors）将接收到分配订单本身的执行细节和佣金。要接收特定子账户的分配细节和佣金，可以使用 `IBApi.EClient.reqExecutions`。

`EWrapper.commissionReport` 方法的结构如下：

- `commissionReport: CommissionReport`：返回包含执行 ID（execId）、佣金（commission）、货币（currency）、实现的盈亏（realizedPnl）、收益率（yield）和收益兑现日期（yieldRedemptionDate）字段的佣金报告对象。

此方法提供了与一个执行相关的佣金报告。这对于了解每笔交易的成本非常重要，尤其是对于那些关注交易费用和绩效的用户。通过接收佣金报告，用户可以准确地了解交易产生的费用，并将这些信息纳入其整体交易策略和绩效评估中。

例如，了解特定交易的佣金可以帮助用户评估交易策略的成本效益，特别是在进行大量交易或使用复杂策略时。此外，对于财务顾问和管理多个账户的用户来说，能够接收特定子账户的执行细节和佣金信息对于透明地管理客户资金和评估各个账户的绩效至关重要。

```python
def commissionReport(self, commissionReport: CommissionReport):
    print("CommissionReport.", commissionReport)
```

## Execution Details

IBApi.Execution和IBApi.CommissionReport可以通過IBApi.EClient.reqExecutions方法按需請求，該方法接收一個IBApi.ExecutionFilter對象作為參數，以獲取符合給定條件的執行。可以傳遞一個空的IBApi.ExecutionFilter對象以獲取所有先前的執行。

一旦所有匹配的執行都已交付，將觸發一個IBApi.EWrapper.execDetailsEnd事件。

重要提示：默認情況下，只會交付自當天午夜以來發生的執行。如果您想要請求過去7天的執行，則需要調整TWS（交易工作站）的“顯示交易記錄…”設置以滿足您的要求。請注意，IB Gateway將無法更改交易日誌的設置，因此僅能交付自午夜以來的執行。

請注意，如果對執行進行更正，則將作為額外的IBApi.EWrapper.execDetails回調接收，所有參數都相同，唯一不同的是執行對象中的execID。execID只會在最後一個句點之後的數字不同。

### The Execution Object

執行物件用於維護與用戶交易訂單相關的所有數據。這可以用於查詢執行細節和導航接收到的數據。提供的詳細信息將顯示與執行有關的所有信息，包括填充了多少股份、執行的價格以及發生的時間。

Execution()
OrderId：int。API客戶端的訂單ID。可能對一個帳戶來說不是唯一的。

ClientId：int。發起此執行的訂單的API客戶端標識符。

ExecId：String。執行的標識符。每個部分填充都有一個單獨的ExecId。一個與先前ExecId僅在最後一個句號之後的數字不同的ExecId表示一次更正，例如以“.02”結尾的ExecId將是對先前以“.01”結尾的執行的更正。

Time：String。執行的服務器時間。

AcctNumber：String。分配訂單的賬戶。

Exchange：String。執行發生的交易所。

Side：String。指定交易是購買還是出售 BOT代表買入，SLD代表賣出。

Shares：decimal。填充的股份數量。

Price：double。訂單的執行價格，不包括傭金。

PermId：int。TWS訂單標識符。來自IB外部的交易的PermId可以為0。

Liquidation：int。標識是否因IB發起的清算而發生執行。

CumQty：decimal。累計數量。用於常規交易、組合交易及組合的腿部。

AvgPrice：double。平均價格。用於常規交易、組合交易及組合的腿部。不包括傭金。

OrderRef：String。OrderRef是一個用戶可自定義的字符串，可以通過API或TWS設置，並將與訂單關聯其生命周期。

EvRule：String。經濟價值規則名稱及其相應的可選參數。這兩個值應由冒號分隔。例如，aussieBond:YearsToExpiration=3。當可選參數不存在時，第一個值後將跟隨一個冒號。

EvMultiplier：double。告訴你如果價格變化1，合約的市值大約會變化多少。不能用於通過將價格乘以大約乘數來獲得市值。

ModelCode：String。模型代碼

LastLiquidity：Liquidity。執行的流動性類型。

pendingPriceRevision：bool。描述執行是否仍在等待價格修訂。

鑒於執行的附加結構不斷演變，建議查閱您的編程語言中相關的Execution類，以全面審查可用字段。


```python
class Execution(Object):
    def __init__(self):
        self.execId = ""
        self.time =  ""
        self.acctNumber =  ""
        self.exchange =  ""
        self.side = ""
        self.shares = UNSET_DECIMAL
        self.price = 0. 
        self.permId = 0
        self.clientId = 0
        self.orderId = 0
        self.liquidation = 0
        self.cumQty = UNSET_DECIMAL
        self.avgPrice = 0.
        self.orderRef =  ""
        self.evRule =  ""
        self.evMultiplier = 0.
        self.modelCode =  ""
        self.lastLiquidity = 0

    def __str__(self):
        return "ExecId: %s, Time: %s, Account: %s, Exchange: %s, Side: %s, Shares: %s, Price: %s, PermId: %s, " \
                "ClientId: %s, OrderId: %s, Liquidation: %s, CumQty: %s, AvgPrice: %s, OrderRef: %s, EvRule: %s, " \
                "EvMultiplier: %s, ModelCode: %s, LastLiquidity: %s" % (self.execId, self.time, self.acctNumber, 
                self.exchange, self.side, decimalMaxString(self.shares), floatMaxString(self.price), intMaxString(self.permId), 
                intMaxString(self.clientId), intMaxString(self.orderId), intMaxString(self.liquidation),
                decimalMaxString(self.cumQty), floatMaxString(self.avgPrice), self.orderRef, self.evRule, floatMaxString(self.evMultiplier), 
                self.modelCode, intMaxString(self.lastLiquidity))


class ExecutionFilter(Object):
    # Filter fields
    def __init__(self):
        self.clientId = 0
        self.acctCode = ""
        self.time = ""
        self.symbol = ""
        self.secType = ""
        self.exchange = "" 
        self.side = ""
```

### Request Execution Details

`EClient.reqExecutions` 方法用于请求当前日（自午夜起）的执行情况和符合过滤器标准的佣金报告。该方法仅能检索当天的执行情况。其参数包括：

- `reqId: int`：请求的唯一标识符。

- `filter: ExecutionFilter`：用于确定返回哪些执行报告的过滤标准。

通过使用 `reqExecutions` 方法，您可以获取当天的执行情况和佣金报告，这些报告将符合您通过过滤器设定的特定标准。值得注意的是，只有当天的执行数据才能被检索，这对于实时监控交易执行和管理佣金成本非常重要。

```python
self.reqExecutions(10001, ExecutionFilter())
```

### Receive Execution Details

`EWrapper.execDetails` 方法用于提供在过去24小时内发生的执行情况。此方法的参数包括：

- `reqId: int`：请求的标识符。

- `contract: Contract`：订单的合约信息。

- `execution: Execution`：执行的详细情况。

通过使用 `execDetails` 方法，您可以获取过去24小时内的执行信息，包括合约的详细内容和执行的具体情况。这对于跟踪和分析近期的交易活动非常有用，特别是对于那些需要及时了解最新执行情况以做出快速决策的交易者。

```python
def execDetails(self, reqId: int, contract: Contract, execution: Execution):
    print("ExecDetails. ReqId:", reqId, "Symbol:", contract.symbol, "SecType:", contract.secType, "Currency:", contract.currency, execution)
```

----

`EWrapper.execDetailsEnd` 方法用于指示执行信息接收的结束。该方法的参数包括：

- `reqId: int`：请求的标识符。

此方法标志着特定执行信息请求的结束，通常用于在接收到所有相关的执行信息后，表明没有更多的数据将被发送。这对于确保应用程序或交易系统正确处理和解析接收到的执行数据非常重要，特别是在处理大量或复杂的交易执行数据时。通过使用 `execDetailsEnd` 方法，您可以有效地管理数据流，确保已经完整地接收了所有请求的执行信息。

```python
def execDetailsEnd(self, reqId: int):
    print("ExecDetailsEnd. ReqId:", reqId)
```

## Open Orders

`EWrapper.openOrder` 方法用于提供当前打开的订单信息。该方法的参数包括：

- `orderId: int`：订单的唯一ID。

- `contract: Contract`：订单的合约信息。

- `order: Order`：当前激活的订单。

- `orderState: OrderState`：订单的状态。

此方法主要用于接收和展示当前开放的订单数据，包括订单的详细合约信息、订单本身的信息以及订单的状态。这对于实时监控和管理活跃的交易订单非常重要，特别是在动态变化的交易市场中，了解当前开放订单的详细情况对于做出及时的交易决策至关重要。通过 `openOrder` 方法，交易者和开发者能够有效追踪和管理他们的订单流。

```python
def openOrder(self, orderId: OrderId, contract: Contract, order: Order, orderState: OrderState):
    print(orderId, contract, order, orderState)
```

----

`EWrapper.openOrderEnd` 方法用于通知接收开放订单信息的结束。这个方法没有参数。

它主要用于标志所有当前开放订单信息的接收完成。在处理大量或复杂的订单信息时，此方法的使用尤为重要，因为它可以帮助确保所有相关的开放订单数据已被完全接收和处理。通过 `openOrderEnd` 方法，交易系统或应用程序能够了解何时完成了所有开放订单的数据传输，从而可以进行后续的处理或分析。

```python
def openOrderEnd(self):
    print("OpenOrderEnd")
```

## Order Status

`EWrapper.orderStatus` 方法用于提供订单每次变更时的最新信息。该方法的参数包括：

- `orderId: int`：订单的客户端ID。

- `status: String`：订单的当前状态。

- `filled: decimal`：已成交的头寸数量。

- `remaining: decimal`：剩余的头寸数量。

- `avgFillPrice: double`：平均成交价格。

- `permId: int`：由TWS用于识别订单的订单permId。

- `parentId: int`：父订单的ID。用于括号订单和自动追踪止损订单。

- `lastFillPrice: double`：最后成交头寸的价格。

- `clientId: int`：提交订单的API客户端。

- `whyHeld: String`：此字段用于识别TWS尝试定位卖空股票时被保留的订单。表示这一点的值是‘locate’。

- `mktCapPrice: double`：如果订单已被设定上限，这表示当前的上限价格。

`orderStatus` 方法对于实时追踪订单状态非常重要，尤其是在订单状态频繁变更的情况下。需要注意的是，通常会有重复的 `orderStatus` 消息。这对于交易者来说是重要的，因为它允许他们跟踪订单的每一个变化，确保对市场动态和订单执行情况有充分的了解。

```python
def orderStatus(self, orderId: OrderId, status: str, filled: Decimal, remaining: Decimal, avgFillPrice: float, permId: int, parentId: int, lastFillPrice: float, clientId: int, whyHeld: str, mktCapPrice: float):
    super().orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld, mktCapPrice)
```

### Understanding Order Status


| Status Code   | Description                                                                                                                                                                                                                                                              |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| PendingSubmit | indicates that you have transmitted the order, but have not yet received confirmation that it has been accepted by the order destination.                                                                                                                                |
| PendingCancel | indicates that you have sent a request to cancel the order but have not yet received cancel confirmation from the order destination. At this point, your order is not confirmed canceled. It is not guaranteed that the cancellation will be successful.                 |
| PreSubmitted  | indicates that a simulated order type has been accepted by the IB system and that this order has yet to be elected. The order is held in the IB system until the election criteria are met. At that time the order is transmitted to the order destination as specified. |
| Submitted     | indicates that your order has been accepted by the system.                                                                                                                                                                                                               |
| ApiCancelled  | after an order has been submitted and before it has been acknowledged, an API client client can request its cancelation, producing this state.                                                                                                                           |
| Cancelled     | indicates that the balance of your order has been confirmed canceled by the IB system. This could occur unexpectedly when IB or the destination has rejected your order.                                                                                                 |
| Filled        | indicates that the order has been completely filled. Market orders executions will not always trigger a Filled status.                                                                                                                                                   |
| Inactive      | indicates that the order was received by the system but is no longer active because it was rejected or canceled.                                                                                                                                                         |

## Requesting Currently Active Orders

在订单仍然活跃的情况下，可以通过TWS API检索它。通过TWS API提交的订单总是与提交它们的客户端应用程序（即客户端ID）绑定，这意味着只有提交订单的客户端才能修改已下的订单。为了提供最大的灵活性，提供了三种不同的方法。活跃订单将通过 `IBApi.EWrapper.openOrder` 和 `IBApi.EWrapper.orderStatus` 方法返回，如之前在“openOrder回调”和“orderStatus回调”部分所述。

需要注意的是，无法获取已取消或已全部成交的订单。这是因为一旦订单被取消或完全成交，它就不再被视为活跃订单，因此不会通过上述的回调方法返回。交易者需要利用TWS API中提供的这些方法来有效地追踪和管理他们的活跃订单，同时了解一旦订单状态改变（如被取消或成交），这些订单就不再通过API可检索。

### API client's orders

`IBApi.EClient.reqOpenOrders` 方法允许获取由与发送订单时完全相同的客户端ID连接的客户端应用程序提交的所有活跃订单。如果客户端0调用 `reqOpenOrders`，它将导致当前通过TWS手动下的开放订单被“绑定”，即被分配一个订单ID，使得它们可以被API客户端0修改或取消。

当订单被API客户端0绑定时，会有回调到 `IBApi::EWrapper::orderBound`。这表明了API订单ID与permID之间的映射。`IBApi.EWrapper.orderBound` 回调响应新绑定的订单，指示permID（在整个账户中唯一）与API订单ID（特定于API客户端）之间的映射。在全局配置中的API设置里，默认勾选了“使用负数绑定自动订单”，这将指定手动TWS订单如何被分配API订单ID。

`EClient.reqOpenOrders ()`
请求由这个特定API客户端（通过API客户端ID标识）下的所有开放订单。对于客户端ID为0的情况，这将绑定之前的手动TWS订单。

此功能对于那些希望通过API管理和修改通过TWS手动提交的订单的用户来说是非常重要的。它提供了一个机制来整合手动交易和自动化交易策略，允许更高效地管理交易操作。

```python
self.reqOpenOrders()
```

### All submitted orders

`EClient.reqAllOpenOrders` 方法用于请求与当前客户端关联账户中所有当前打开的订单。现有订单将通过 `openOrder` 和 `orderStatus` 事件接收到。开放订单只会返回一次；该函数不会启动订阅。

此功能对于想要获取所有关联账户中当前开放订单的全景视图的用户来说是非常有用的。它允许交易者和投资者一次性查看他们在所有关联账户中的活跃订单状态，包括订单的详细信息和当前状态。这对于进行全面的交易管理和决策至关重要，尤其是在涉及多个账户和多种交易策略的情况下。

通过使用 `reqAllOpenOrders` 方法，用户能够有效地监控和评估他们的整体交易头寸，确保对任何开放的或未解决的订单都有清晰的了解。这有助于优化交易策略和提高交易效率。

```python
self.reqAllOpenOrders()
```

### Manually Submitted TWS Orders

`EClient.reqAutoOpenOrders` 方法用于请求有关从TWS下达的未来订单的状态更新。该方法的参数包括：

- `autoBind: bool`：如果设置为true，新创建的订单将被分配一个API订单ID，并隐式地与此客户端关联。如果设置为false，未来的订单将不会这样做。

此方法只能与客户端ID为0的应用程序一起使用。

重要的是：只有那些使用客户端ID为0连接的应用程序才能接管手动提交的订单。

使用 `reqAutoOpenOrders` 方法时，如果 `autoBind` 参数设置为true，那么所有新创建的订单都将自动与发起请求的API客户端关联。这意味着这些订单可以由相同的API客户端进行管理和修改。这项功能对于那些希望通过API自动处理和监控通过TWS手动下达的订单的用户来说非常有价值，尤其是在需要在自动化交易策略中快速集成手动交易决策的情况下。

请注意，这个功能的主要限制是它仅适用于客户端ID为0的应用程序，这是因为TWS默认将客户端ID为0视为主API客户端，拥有接管和管理手动提交订单的能力。

```python
self.reqAutoOpenOrders(True)
```

### Order Binding Notification

`EWrapper.orderBound` 方法是对API绑定订单控制消息的响应。该方法的参数包括：

- `orderId: long`：IBKR的permId（永久ID）。

- `apiClientId: int`：API客户端ID。

- `apiOrderId: int`：API订单ID。

当一个订单通过API被绑定时，`orderBound` 方法被触发，提供了关于订单的关键信息。这包括订单在IBKR中的唯一永久ID（permId）、发出绑定请求的API客户端ID，以及由API分配给该订单的唯一订单ID。

`orderBound` 方法主要用于在订单被API客户端接管时通知用户，这在管理和跟踪通过TWS手动提交的订单时尤为重要。它确保API客户端能够识别和操作这些订单，特别是在需要对订单进行修改或取消时。此方法是在综合交易系统中实现自动化和手动交易策略的有效工具，使用户能够在一个统一的界面内管理所有交易活动。

```python
def orderBound(self, orderId: int, apiClientId: int, apiOrderId: int):
    print("OrderBound.", "OrderId:", intMaxString(orderId), "ApiClientId:", intMaxString(apiClientId), "ApiOrderId:", intMaxString(apiOrderId))
```