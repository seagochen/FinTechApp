# Positions


`IBApi.EClient.reqAccountUpdates` 函数的一个限制是它一次只能用于单个账户。为了创建来自多个账户的持仓更新的订阅，可以使用 `IBApi.EClient.reqPositions` 函数。

**注意：reqPositions** 函数在介绍经纪人或财务顾问主账户中不可用，如果这些账户拥有大量的子账户（>50），这是为了优化 TWS/IB Gateway 的性能。
相反，可以使用 reqPositionsMulti 函数来订阅来自个别子账户的更新。此外，配置为按需账户查找的 IBroker 账户也无法使用此功能。

最初调用 reqPositions 后，将返回所有关联账户中所有持仓的信息，随后会有 IBApi::EWrapper::positionEnd 回调。
此后，当某个持仓发生变化时，更新将返回到 IBApi::EWrapper::position 函数。要取消 reqPositions 订阅，请调用 IBApi::EClient::cancelPositions。

## Request Positions

EClient.reqPositions()

订阅所有可访问账户的持仓更新。最初会发送所有持仓信息，之后只有在持仓发生变化时才会发送更新。

## Receive Positions

`EWrapper.position` 方法提供了投资组合的未平仓持仓信息。在最初的回调（仅此一次）发送所有持仓信息之后，将触发 `IBApi.EWrapper.positionEnd` 函数。

`EWrapper.position` 方法的参数包括：

- `account: String`：持有该持仓的账户。
- `contract: Contract`：持仓的合约。
- `pos: decimal`：持有的持仓数量。`avgCost` 是持仓的平均成本。
- `avgCost: double`：当前持有持仓的所有交易的总平均成本。

对于期货，由于某些期货在多个交易所交易，因此在持仓回调中不会填充交易所字段。这意味着在处理期货持仓时，可能需要额外的步骤来识别相关交易所。

```python
    def position(self, account: str, contract: Contract, position: Decimal, avgCost: float):
        print("Position.", "Account:", account, "Contract:", contract, "Position:", position, "Avg cost:", avgCost)
```

-----

`Ewrapper.positionEnd()` 方法用于指示所有的持仓信息已经传输完成。这个方法仅在 `EWrapper.position` 的最初回调之后返回，标志着所有当前持仓信息的传输过程已经结束。这对于理解何时完成了持仓数据的接收尤为重要，特别是在处理大量数据或多账户时。

```python
    def positionEnd(self):
        print("PositionEnd")
```

## Cancel Positions RequestCopy Location

EClient.cancelPositions()

取消之前通过 `EClient.reqPositions()` 方法发起的持仓订阅请求。

# Positions By Model

`IBApi.EClient.reqPositionsMulti` 函数可用于任何账户结构，用于订阅多个账户和/或模型的持仓更新。如果没有多个账户或模型可用，账户和模型参数是可选的。使用这个函数来针对特定的账户子集进行操作比使用 `IBApi.EClient.reqPositions` 更为高效。在账户参数中，可以接受配置文件名称代替组（group）。

## Request Positions By Model

`EClient.reqPositionsMulti` 方法用于请求账户和/或模型的持仓订阅。该方法的参数包括：

- `requestId: int`：请求的标识符。
- `account: String`：如果提供了账户 ID，只会传送属于指定模型的该账户的持仓。
- `modelCode: String`：我们感兴趣的模型的持仓代码。

此方法最初会返回所有持仓，然后实时返回任何持仓变化的更新。这对于实时监控和管理多个账户或模型中的持仓非常有用，特别是在需要跟踪特定模型或账户持仓的场景中。

```python
    self.reqPositionsMulti(requestid, "U1234567", "")
```

## Receive Positions By Model

`EWrapper.positionMulti` 方法提供了投资组合中的未平仓持仓信息。该方法的参数包括：

- `requestId: int`：请求的 ID。
- `account: String`：持有该持仓的账户。
- `modelCode: String`：持有该持仓的模型代码。
- `contract: Contract`：持仓的合约。
- `pos: decimal`：持有的持仓数量。
- `avgCost: double`：持仓的平均成本。

这个方法允许用户获取关于他们投资组合中的开放持仓的详细信息，包括账户和模型代码、相关合约、持仓数量以及平均成本。这对于管理和分析多账户或多模型投资组合非常有用。

```python
    def positionMulti(self, reqId: int, account: str, modelCode: str, contract: Contract, pos: Decimal, avgCost: float):
        print("PositionMulti. RequestId:", reqId, "Account:", account, "ModelCode:", modelCode, "Contract:", contract, ",Position:", pos, "AvgCost:", avgCost)
```

----

`EWrapper.positionMultiEnd` 方法指示傳輸已完陳。

```python
    def positionMultiEnd(self, reqId: int):
        print("PositionMultiEnd. RequestId:", reqId)
```

## Cancel Positions By Model Request

`EClient.cancelPositionsMulti` 方法用于取消之前通过 `EClient.reqPositionsMulti()` 方法发起的持仓订阅请求。该方法的参数包括：

- `requestId: int`：请求的标识符。

```python
    self.cancelPositionsMulti(requestid)
```