# Profit & Loss (PnL)


可以发起请求以接收有关账户的日收益与亏损（P&L）和未实现收益与亏损的实时更新，或者针对个别持仓的收益与亏损。财务顾问还可以请求“所有”子账户的收益与亏损数据，或者针对一个投资组合模型的收益与亏损。
此外，还可以扩展到包括账户或个别持仓层面的已实现收益与亏损信息。

下面演示的 P&L API 功能返回在 TWS 当前版本的投资组合窗口中显示的数据。因此，收益与亏损值是基于在 TWS 全局配置中指定的重置计划（默认为特定于工具的重置计划）计算的，这个设置也会影响发送到相关 API 功能的值。
另外，在 TWS 中，如果且仅当账户窗口的虚拟外汇部分展开时，来自虚拟外汇头寸的收益与亏损数据才会包含在账户的收益与亏损中。

## Request P&L for individual positions

使用 `IBApi::EClient::reqPnLSingle` 函数订阅收益与亏损（P&L）更新。这个函数不能用于配置为按需查找且账户为“所有”的 IBroker 账户。目前，收益与亏损的更新大约每秒返回一次到 `IBApi.EWrapper.pnlSingle`。

如果对无效的合约 ID（conId）或账户中不存在的合约发出收益与亏损订阅请求，将不会有响应。和 API 的其他地方一样，最大双精度浮点数值将表示一个“未设置”的值。这对应于 TWS 中的空单元格。介绍经纪人账户如果没有大量的子账户（<50），可以通过指定账户为“所有”来接收汇总数据。

*不能用于配置为按需查找且账户为“所有”的 IBroker 账户。
*未来可能发生变化。

`EClient.reqPnLSingle` 方法的参数包括：

- `reqId: int`：请求标识符，用于跟踪数据。
- `account: String`：存在持仓的账户。
- `modelCode: String`：存在持仓的模型。
- `conId: int`：希望接收日收益与亏损更新的合约的合约 ID（conId）。注意：如果输入了无效的 conId，不会返回消息。

该请求用于获取个别持仓的日收益与亏损的实时更新。

```python
    self.reqPnLSingle(requestId, "U1234567", "", 265598)
```

## Receive P&L for individual positions

`EWrapper.pnlSingle` 方法用于接收单个持仓的日收益与亏损（PnL）值的实时更新。该方法的参数包括：

- `reqId: int`：用于跟踪的请求标识符。
- `pos: decimal`：持仓的当前规模。
- `dailyPnL: double`：持仓的日收益与亏损。
- `unrealizedPnL: double`：持仓的总未实现收益与亏损（自开仓以来），实时更新。
- `realizedPnL: double`：持仓的总已实现收益与亏损（自开仓以来），实时更新。
- `value: double`：持仓的当前市场价值。

这个方法提供了对于每个单独持仓的日收益与亏损、未实现和已实现收益与亏损的实时数据，这对于投资者实时监控和分析他们的持仓非常重要。

```python
    def pnlSingle(self, reqId: int, pos: Decimal, dailyPnL: float, unrealizedPnL: float, realizedPnL: float, value: float):
        print("Daily PnL Single. ReqId:", reqId, "Position:", pos, "DailyPnL:", dailyPnL, "UnrealizedPnL:", unrealizedPnL, "RealizedPnL:", realizedPnL, "Value:", value)
```

## Cancel P&L request for individual positionsCopy Location

`EClient.cancelPnLSingle` 方法用于取消收益与亏损（P&L）订阅。该方法的参数包括：

- `reqId: int`：用于跟踪的请求标识符。

```python
    self.cancelPnLSingle(requestId)
```

## Request P&L for accounts

使用 `IBApi::EClient::reqPnL` 函数订阅。更新会发送到 `IBApi.EWrapper.pnl`。

拥有不到50个子账户的介绍经纪人账户可以通过指定“所有”作为账户代码来接收所有子账户的汇总收益与亏损。对于拥有许多子账户和/或持仓的顾问账户，计算和返回聚合收益与亏损可能需要几秒钟。对于账户收益与亏损数据，TWS 设置“下载持仓时准备投资组合收益与亏损数据”必须被勾选。

`EClient.reqPnL` 方法的参数包括：

- `reqId: int`：请求 ID，用于跟踪数据。
- `account: String`：要接收收益与亏损更新的账户。
- `modelCode: String`：指定要请求特定模型的收益与亏损更新。

此方法创建了对日收益与亏损和未实现收益与亏损的实时更新的订阅。这对于那些需要及时了解其账户或特定模型收益与亏损状况的用户来说非常有用。

```python
    self.reqPnL(reqId, "U1234567", "")
```

## Receive P&L for accounts

`EWrapper.pnl` 方法用于接收账户的日收益与亏损（PnL）更新。该方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。
- `dailyPnL: double`：账户的日收益与亏损实时更新。
- `unrealizedPnL: double`：账户的总未实现收益与亏损实时更新。
- `realizedPnL: double`：账户的总已实现收益与亏损实时更新。

这个方法提供实时更新，有助于投资者跟踪和管理他们账户的整体收益与亏损状况，包括每日变动、未实现和已实现的收益与亏损。这对于及时做出交易决策和长期投资规划非常重要。

```python
    def pnl(self, reqId: int, dailyPnL: float, unrealizedPnL: float, realizedPnL: float):
        print("Daily PnL. ReqId:", reqId, "DailyPnL:", dailyPnL, "UnrealizedPnL:", unrealizedPnL, "RealizedPnL:", realizedPnL)
```

## Cancel P&L subscription requests for accounts

`EClient.cancelPnL` 方法用于取消实时更新的日收益与亏损（PnL）订阅。该方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。

此方法通过指定请求标识符来取消之前通过 `EClient.reqPnL` 发起的实时日收益与亏损更新订阅。这对于停止接收不再需要的或相关性较低的数据更新非常有用。

```python
    self.cancelPnL(reqId)
```
