- [Orders](#orders)
  - [The Order Object](#the-order-object)
  - [Exercise Options](#exercise-options)
  - [Minimum Price Increment](#minimum-price-increment)
    - [Request Market Rule](#request-market-rule)
    - [Receive Market Rule](#receive-market-rule)
  - [Place Order](#place-order)
  - [Modifying Orders](#modifying-orders)
  - [Cancelling an Order](#cancelling-an-order)
    - [Cancel Individual Order](#cancel-individual-order)
    - [Cancel All Open Orders](#cancel-all-open-orders)
  - [Test Order Impact (WhatIf)](#test-order-impact-whatif)
  - [Trigger Methods](#trigger-methods)
  - [MiFIR Transaction Reporting Fields](#mifir-transaction-reporting-fields)

# Orders

## The Order Object

订单对象是TWS API的一个核心部分，用于下达和管理订单。它主要由不断增加的属性范围构建，用以创建尽可能最佳的订单。值得注意的是，右侧的值代表放置或引用任何订单所需的字段。请记住，还有许多其他属性可以并且应该被引用。

Order()
- `action: String`：决定合约是买入（BUY）还是卖出（SELL）。

- `auxPrice: double`：用于确定对于STP（停止）、STP LMT（限价停止）和TRAIL（追踪）订单的停止价格。

- `lmtPrice: double`：用于确定对于LMT（限价）、STP LMT和TRAIL订单的限价。

- `orderType: String`：指定要下达的订单类型。例如，MKT（市价）、LMT（限价）、STP（停止）。

- `tif: String`：订单的有效时间。默认的tif是DAY（日内）。

- `totalQuantity: decimal`：订单的总量。

鉴于订单的额外结构不断演变，建议审查您所使用的编程语言中相关订单类，以全面了解可用的字段。这些信息对于理解和利用TWS API进行有效的订单管理至关重要，尤其是在面对复杂的交易策略和市场条件时。

> For more information, please refer ibapi\order.py


## Exercise Options

`EClient.exerciseOptions` 函数用于通过API执行或放弃期权。

期权执行将以订单状态侧为“BUY”和限价为0显示，但仅在请求发出时显示
通过价格=0可以区分期权执行
`EClient.exerciseOptions` 方法的参数包括：

- `tickerId: int`：执行请求的标识符。

- `contract: Contract`：要执行的期权合约。

- `exerciseAction: int`：设置为1以执行期权，设置为2以让期权失效。

- `exerciseQuantity: int`：要执行的合约数量。

- `account: String`：目标账户。

- `ovrd: int`：指定您的设置是否会覆盖系统的自然动作。设置为1以覆盖，设置为0则不覆盖。

例如，如果您的动作是“执行”，并且期权不在价内，按自然动作，期权将不会执行。如果您将覆盖设置为“是”，则自然动作将被覆盖，价外期权将被执行。

- `manualOrderTime: String`：指定执行期权的时间。空字符串将假定为当前时间。要求TWS API 10.26或更高版本。

此函数用于执行一个期权合约。

请注意：此功能受TWS设置的影响，该设置指定是否必须最终确定执行请求。

```python
self.exerciseOptions(5003, contract, 1, 1, self.account, 1, "")
```

## Minimum Price Increment

最小增量是合约可以交易的价格级别之间的最小差异。一些交易在所有价格级别上具有恒定的价格增量。然而，一些合约在它们交易的不同交易所上和/或不同价格级别上具有不同的最小增量。在 `contractDetails` 类中，有一个名为 `minTick` 的字段，它指定在任何交易所或价格上遇到的最小可能最小增量。要获得关于最小价格增量结构的完整信息，可以使用IB合约和证券搜索网站，或者使用API函数 `EClient.reqMarketRule`。

当使用合约对象使用函数 `EClient.reqContractDetails` 时，它将返回一个 `contractDetails` 对象给 `contractDetails` 函数，其中包含了该工具交易的有效交易所列表。此外，在 `contractDetails` 对象中还有一个名为 `marketRuleIDs` 的字段，其中包含了一个“市场规则”的列表。市场规则被定义为给定价格的最小价格增量规则。在 `contractDetails` 返回的市场规则列表中，市场规则的列表与有效交易所的列表顺序相同。这样，可以确定特定交易所上合约的市场规则ID。

外汇和外汇差价合约的市场规则指示默认配置（1/2，而不是1/10点差）。它可以通过TWS或IB Gateway全局配置调整为1/10点差。
一些非美国证券，例如在SEHK交易所的，有最小手数限制。这些信息无法通过API获取，但可以从IB合约和证券搜索页面获得。如果订单不符合最小手数要求，也会在返回的错误消息中指出。
通过市场规则ID号码，可以使用API函数 `EClient.reqMarketRule` 找到相应的规则。规则被返回给 `EWrapper.marketRule` 函数。

对于外汇，TWS/IB Gateway配置中有一个选项，允许以1/10点差而不是1/5点差（默认值）进行交易。
TWS全局配置 -> 显示 -> 行情行 -> 允许以1/10点差进行外汇交易。

### Request Market Rule

`EClient.reqMarketRule` 函数用于请求有关特定市场规则的详细信息。特定交易所上某种工具的市场规则提供了有关最小价格增量如何随价格变化的详细信息。该方法的参数包括：

- `marketRuleId: int`：市场规则的ID。

通过对特定合约调用 `EClient.reqContractDetails`，可以获得市场规则ID的列表。返回的市场规则ID列表将为 `contractDetails` 中相应有效交易所列表中的工具提供市场规则ID。

这个功能对于理解和适应不同交易所的价格变化规则非常重要，尤其是在交易价格灵活性较大的金融工具时。了解和遵守这些市场规则对于确保订单符合市场标准和避免不必要的订单拒绝至关重要。

通过使用 `reqMarketRule` 方法，交易者可以获取关于特定市场规则的详细信息，例如最小价格变动单位在不同价格水平下的变化。这有助于更好地理解和适应市场运作方式，从而在制定交易策略和执行交易决策时做出更明智的选择。

```python
self.reqMarketRule(26)
```

### Receive Market Rule

`EWrapper.marketRule` 方法用于返回特定市场规则ID的最小价格增量结构。该方法的参数包括：

- `marketRuleId: int`：所请求的市场规则ID。

- `priceIncrements: PriceIncrement[]`：根据市场规则返回可用的价格增量。

这个方法主要用于提供有关特定市场规则下的价格变动单位的详细信息。每个市场规则ID对应于特定的价格增量规则，这些规则定义了在不同价格点上可用的最小价格变动单位。了解这些价格增量对于交易者在进行交易决策时至关重要，尤其是在需要精确定价的情况下。

市场规则ID可以通过调用特定合约的 `contractDetails` 对象来获取，其中包含了该工具在有效交易所的市场规则ID。通过 `marketRule` 方法获取的价格增量信息可以帮助交易者更好地理解并遵循特定市场的定价规则，从而在制定交易策略时做出更合适的决策。这对于确保订单的有效性和避免因不符合市场规则而被拒绝至关重要。

```python
def marketRule(self, marketRuleId: int, priceIncrements: ListOfPriceIncrements):
    print("Market Rule ID: ", marketRuleId)
    for priceIncrement in priceIncrements:
        print("Price Increment.", priceIncrement)
```

## Place Order

通过 `EClient.placeOrder` 方法提交订单。从下面的代码片段中注意，一个保存 `nextValidId` 的变量是如何自动增加的。

一旦订单正确提交后，TWS 将开始通过 `EWrapper.openOrder` 和 `EWrapper.orderStatus` 发送有关订单活动的事件。

执行分配订单的顾问将接收到分配订单本身的执行细节和佣金。要接收特定子账户的分配细节和佣金，可以使用 `EClient.reqExecutions`。

可以通过将订单类中的 `Order.Transmit` 标志设置为False，将订单发送到TWS但不传输到IB服务器。未传输的订单只会在该TWS会话中可用（不适用于其他用户名），并且在重启时将被清除。此外，它们可以通过API被取消或传输，但在“未传输”状态下无法查看。

`EClient.placeOrder` 方法的参数包括：

- `id: int`：订单的唯一标识符。如果使用小于或等于之前订单的订单ID来放置新订单，将会发生错误。

- `contract: Contract`：订单的合约。

- `order: Order`：订单对象。

用于放置或修改订单。

这个功能对于进行交易操作至关重要，允许交易者和自动化交易系统有效地管理订单。通过 `placeOrder` 方法，可以灵活地创建新订单或修改现有订单。同时，设置 `Order.Transmit` 标志为False，为交易者提供了更多控制，允许他们在将订单提交到市场前进行最终审核。

```python
self.placeOrder(orderId, contract, order)
```

## Modifying Orders

API订单的修改可以在API客户端连接到与TWS相同用户名的TWS会话时进行，并使用相同的API客户端ID。然后可以调用 `EClient.placeOrder` 函数，并使用与开放订单相同的字段，除了要修改的参数。这包括 `Order.OrderId`，必须与开放订单的 `Order.OrderId` 相匹配。通常不建议尝试更改除订单价格、大小和有效时间（例如，将DAY修改为IOC）之外的订单字段。要更改其他参数，最好是取消开放订单，并创建一个新的订单。

要修改或取消从TWS手动下达的个别订单，必须使用客户端ID 0连接，然后在尝试修改之前绑定订单。绑定过程为订单分配了一个API订单ID；在绑定之前，它将以API订单ID 0返回给API。API订单ID为0的订单无法通过API修改/取消。`reqOpenOrders` 函数绑定那一刻开放且尚未拥有API订单ID的订单，而 `reqAutoOpenOrders` 函数自动绑定未来的订单。`reqAllOpenOrders` 函数不会绑定订单。
在连接到不同的TWS会话（使用与原始订单不同的用户名登录）时修改API订单，需要首先以与手动TWS订单绑定相同的方式使用客户端ID 0绑定订单，然后才能进行修改。每个TWS用户的API订单ID的分配是独立的，所以同一个订单对于不同的用户可以有不同的API订单ID。在API订单类中由TWS分配的permID可以用来唯一标识账户中的一个订单。
从API绑定订单的过程会取消/重新提交在交易所执行的订单。这可能会影响订单在交易所队列中的位置。计划进行改进，以允许在不影响交易所队列优先级的情况下进行API绑定和修改。

## Cancelling an Order

订单可以通过 `EClient.cancelOrder` 和 `EClient::reqGlobalCancel` 函数从API中取消。

- `EClient.cancelOrder` 只能用于取消最初由具有相同客户端ID的客户端（或对于客户端ID 0的TWS）下达的订单。使用此方法时，需要提供要取消的特定订单的订单ID。这使得该方法适用于取消特定的、由API客户端管理的订单。

- `EClient.reqGlobalCancel` 将取消所有开放订单，无论它们最初是如何下达的。这是一个更广泛的命令，用于快速清除所有未执行的交易指令，无论是通过API还是TWS手动下达的。这个功能在紧急情况下非常有用，比如当市场条件突然变化时，需要迅速撤销所有挂单。

这两种取消订单的方法为API用户提供了灵活的选择，可以根据不同的情况选择最适合的操作。例如，如果需要取消由特定API客户端创建的特定订单，可以使用 `EClient.cancelOrder`。而在需要快速清除所有挂单的情况下，可以使用 `EClient.reqGlobalCancel`。这些功能在管理大量交易时非常关键，特别是在快节奏和多变的交易环境中。

### Cancel Individual Order

`EClient.cancelOrder` 函数用于取消由相同API客户端ID下达的活跃订单。该函数的参数包括：

- `orderId: int`：通过其标识符指定应该取消哪个订单。

- `manualOrderCancelTime: String`：指定订单应该被取消的时间。如果字符串为空，则立即取消订单。

这个函数使得API客户端能够取消它们自己创建的特定订单。这对于管理交易策略和应对市场变化非常重要，尤其是当某个订单不再符合交易策略或市场条件发生变化时。

需要注意的是，API客户端不能取消其他客户端创建的个别订单。在这种情况下，只有 `reqGlobalCancel` 函数可用，它可以取消所有开放订单，而不考虑它们是由哪个客户端或通过TWS手动下达的。这提供了一种全局控制机制，用于紧急情况下或需要快速撤销所有挂单的场景。

```python
self.cancelOrder(orderId, "")
```

### Cancel All Open Orders

`EClient.reqGlobalCancel` 函数用于取消所有开放订单。这个功能对于快速清除所有未执行的交易指令非常有用，无论是通过API还是TWS手动下达的。

```python
self.reqGlobalCancel()
```

## Test Order Impact (WhatIf)


通过API，可以实时检查特定交易执行预期如何改变账户的保证金要求。这是通过创建一个将 IBApi.Order.WhatIf 标志设置为true的订单对象来完成的。默认情况下，Order 中的 whatif 布尔值为false，但如果在传递给 IBApi.EClient.placeOrder 的订单对象中将其设置为True，而不是将订单发送到目的地，IB服务器将进行信用检查，以了解预期的交易后保证金要求。预估的交易后保证金要求将返回到 EWrapper.openOrder 回调中的 IBApi.OrderState 对象。

这相当于在TWS中创建一个订单票据，点击“预览”，并查看“保证金影响”面板中的信息。使用这个功能可以在执行实际交易之前评估其对账户保证金的潜在影响，这对于风险管理和资本分配策略非常重要。通过模拟订单的保证金影响，交易者可以在不实际提交订单的情况下，了解特定交易可能对其账户的财务状况造成的影响。这是一个非常有用的工具，尤其是在处理大额交易或在保证金要求较高的市场条件下。

## Trigger Methods

在 IBApi.Order 类中定义的触发方法指定了模拟止损、止损限价、跟踪止损和条件订单是如何被触发的。有效值包括：

0 – 该工具的默认方法。
1 – “双重买入/卖出”功能，其中止损订单基于两个连续的买入或卖出价格来触发。
2 – “最后价格”功能，其中止损订单基于最后价格来触发。
3 – “双重最后价格”功能。
4 – 买入/卖出功能。
7 – 最后价格或买入/卖出功能。
8 – 中点价格功能。

| secType     | Bid/Ask-driven (1, 4, 8) | Last-driven (2, 3) | Default behavior                    | Notes                                     |
| ----------- | ------------------------ | ------------------ | ----------------------------------- | ----------------------------------------- |
| STK         | yes                      | yes                | Last                                | The double bid/ask is used for OTC stocks |
| CFD         | yes                      | yes                | Last                                |                                           |
| CFD – Index | yes                      | n/a                | n/a                                 | Ex IBUS500                                |
| OPT         | yes                      | yes                | US OPT: Double bid/ask, Other: Last |                                           |
| FOP         | yes                      | yes                | Last                                |                                           |
| WAR         | yes                      | yes                | Last                                |                                           |
| IOPT        | yes                      | yes                | Last                                |                                           |
| FUT         | yes                      | yes                | Last                                |                                           |
| COMBO       | yes                      | yes                | Last                                |                                           |
| CASH        | yes                      | n/a                | Bid/ask                             |                                           |
| CMDTY       | yes                      | n/a                | Last                                |                                           |
| IND         | n/a                      | yes                | n/a                                 | For conditions only                       |

重要提示：

1. **不兼容的触发方法和证券类型**: 如果在您的API订单中使用了不兼容的触发方法（`triggerMethod`）和证券类型（`secType`），那么订单可能永远不会触发。在设计和实现API订单时，确保触发方法和证券类型相匹配是非常重要的，以避免执行问题。

2. **只适用于IB模拟的止损订单**: 这些触发方法仅适用于由IB（Interactive Brokers）模拟的止损订单。如果止损订单的变体由交易所本身处理（即在市场上原生处理），则指定的触发方法将被忽视。这意味着在某些情况下，即使您在API订单中指定了触发方法，它也可能不会按预期方式工作。

3. **参考止损订单页面**: 为了更深入地了解这些触发方法的细节和止损订单的行为，建议参考IB的官方“止损订单”页面。这个资源可以提供更多关于不同类型的止损订单、它们是如何工作的以及在何种情况下它们可能不按预期触发的信息。

了解这些细节对于确保通过API创建的订单能够按预期触发和执行至关重要，特别是在复杂的交易策略和多样化的证券类型涉及到的情况下。正确地应用触发方法和了解止损订单的本质将有助于提高交易策略的有效性和可靠性。

## MiFIR Transaction Reporting Fields

对于需要遵守MiFIR（欧洲市场基础设施法规）报告要求的欧洲经济区（EEA）投资公司，如果选择了丰富和委托交易报告，IBApi的Order类新增了四个订单属性，同时在TWS和IB Gateway的全球配置中也增加了几个新的预设。

新增的订单属性包括：

1. `IBApi.Order.Mifid2DecisionMaker`：用于发送“公司内部的投资决策”值（如果未使用 `IBApi.Order.Mifid2DecisionAlgo`）。
2. `IBApi.Order.Mifid2DecisionAlgo`：用于发送“公司内部的投资决策”值（如果未使用 `IBApi.Order.Mifid2DecisionMaker`）。
3. `IBApi.Order.Mifid2ExecutionTrader`：用于发送“公司内部的执行”值（如果未使用 `IBApi.Order.Mifid2ExecutionAlgo`）。
4. `IBApi.Order.Mifid2ExecutionAlgo`：用于发送“公司内部的执行”值（如果未使用 `IBApi.Order.Mifid2ExecutionTrader`）。

新的TWS和IB Gateway订单预设可以在全球配置的“Orders > MiFIR”页面中找到，包括TWS决策制定者默认值、API决策制定者默认值和执行交易者/算法预设。

以下是“公司内部的投资决策”属性 `IBApi.Order.Mifid2DecisionMaker` 和 `IBApi.Order.Mifid2DecisionAlgo` 的可用选择：

- 如果您使用TWS API传输订单，且投资决策总是由客户做出，并且这些客户中没有任何是选择了委托报告的EEA投资公司（“委托报告公司”），那么此字段无需报告。可以通过TWS全球配置的“Orders > MiFIR”页面配置预设以指示这一点。在这种情况下，专有账户的订单需要通过TWS下达。

- 如果您使用TWS API传输订单，且投资决策由委托报告公司内的一个人或一组人做出，并且有一个主要决策者，则您的TWS API程序可以在每个订单上使用字段 `IBApi.Order.Mifid2DecisionMaker` 传输决策者的IB分配的短代码。您可以通过IB账户管理定义决策者。要获取IB分配给这些人员的短代码，请联系IB客户服务。

- 如果您的TWS API程序无法传输上述字段，并且投资决策由一个人做出或批准，且该人可被视为主要投资决策者，则您可以预配置一个默认的投资决策者，用于那些未提供上述字段的订单。您必须在IB账户管理中定义投资决策者，并可以通过TWS全球配置的“Orders > MiFIR”页面配置默认的投资决策者。

- 如果您使用TWS API传输订单，且投资决策由算法做出，则您的TWS API程序可以在每个订单上使用字段 `IBApi.Order.Mifid2DecisionAlgo` 传输决策者的IB分配的短代码。您可以通过IB账户管理定义可以作为决策者的算法。要获取IB分配给这些算法的短代码，请联系IB客户服务。

- 如果您的TWS API程序无法传输上述字段，或者投资决策由一个或主要的决策者算法做出，则您可以预配置一个默认的投资决策者算法，用于那些未发送上述字段的订单。您必须在IB账户管理中定义投资决策者，并可以通过TWS全球配置的“Orders > MiFIR”页面配置默认的投资决策者。

注意：每个订单上只应提供一个投资决