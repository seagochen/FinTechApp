- [Account \& Portfolio Data](#account--portfolio-data)
  - [Account Summary](#account-summary)
  - [Requesting Account Summary](#requesting-account-summary)
    - [Account Summary Tags](#account-summary-tags)
    - [Receiving Account Summary](#receiving-account-summary)
    - [Cancel Account Summary](#cancel-account-summary)
  - [Account Updates](#account-updates)
    - [Requesting Account Updates](#requesting-account-updates)
    - [Receiving Account Updates](#receiving-account-updates)
    - [Account Value Keys](#account-value-keys)
    - [Cancel Account Updates](#cancel-account-updates)
  - [Account Update by Model](#account-update-by-model)
    - [Requesting Account Update by Model](#requesting-account-update-by-model)
    - [Receiving Account Update by Model](#receiving-account-update-by-model)
    - [Cancel Account Update by Model](#cancel-account-update-by-model)
  - [Family Codes](#family-codes)
    - [Requesting Family Codes](#requesting-family-codes)
    - [Receiving Family Codes](#receiving-family-codes)
  - [Managed Accounts](#managed-accounts)
    - [Requesting Managed Accounts](#requesting-managed-accounts)
    - [Receiving Managed Accounts](#receiving-managed-accounts)
  - [Positions](#positions)
    - [Request Positions](#request-positions)
    - [Receive Positions](#receive-positions)
    - [Cancel Positions RequestCopy Location](#cancel-positions-requestcopy-location)
  - [Positions By Model](#positions-by-model)
    - [Request Positions By Model](#request-positions-by-model)
    - [Receive Positions By Model](#receive-positions-by-model)
    - [Cancel Positions By Model Request](#cancel-positions-by-model-request)
  - [Profit \& Loss (PnL)](#profit--loss-pnl)
    - [Request P\&L for individual positions](#request-pl-for-individual-positions)
    - [Receive P\&L for individual positions](#receive-pl-for-individual-positions)
    - [Cancel P\&L request for individual positionsCopy Location](#cancel-pl-request-for-individual-positionscopy-location)
    - [Request P\&L for accounts](#request-pl-for-accounts)
    - [Receive P\&L for accounts](#receive-pl-for-accounts)
    - [Cancel P\&L subscription requests for accounts](#cancel-pl-subscription-requests-for-accounts)
  - [White Branding User Info](#white-branding-user-info)
    - [Requesting White Branding InfoCopy Location](#requesting-white-branding-infocopy-location)
    - [Receiving White Branding Info](#receiving-white-branding-info)



# Account & Portfolio Data

`IBApi.EClient.reqAccountSummary` 方法用于创建 TWS 账户摘要窗口中显示的账户数据的订阅。它通常用于多账户结构。引入经纪商（IBroker）账户，如果拥有超过 50 个子账户或配置为按需账户查找，不能使用 group=”All” 的 `reqAccountSummary`。可以接受配置文件名代替 group。有关更多信息，请参见“组和配置文件的统一”。

TWS 提供了一个全面的账户和投资组合概览，通过其账户和投资组合窗口。这些信息可以通过 TWS API 通过三种不同类型的请求/操作获得：

1. **账户摘要（Account Summary）**：提供了关于账户的关键财务指标的概览，如总资产、资金余额、购买力等。这些信息对于监控账户的整体状况和绩效至关重要。

2. **投资组合更新（Portfolio Updates）**：提供有关投资组合持有资产的详细信息，如持仓、未实现盈亏、市值等。这对于跟踪个别投资和管理投资组合的多样性非常有用。

3. **账户更新（Account Updates）**：提供实时的账户活动信息，如交易、资金转移和其他账户活动。这对于保持对账户所有活动的即时了解非常重要。

通过这些请求，用户可以从 TWS API 获取与 TWS 账户和投资组合窗口中显示的类似的信息，从而进行有效的账户管理和投资决策。

## Account Summary

当首次调用 `reqAccountSummary` 时，将返回所有请求值的列表，之后每三分钟将返回那些发生变化的值。这种每三分钟更新一次的频率与 TWS 账户窗口相同，且无法更改。

这意味着：

1. **首次响应**：在最初调用 `reqAccountSummary` 后，API 将提供一次完整的账户摘要数据，包括所有请求的指标和值。

2. **定期更新**：在首次响应之后，API 将每三分钟自动更新变化的数据。这些更新将反映在账户摘要数据中的任何变化，例如账户余额、持仓价值或其他财务指标的变化。

3. **更新频率**：这种固定的更新频率确保了数据的一致性，并与 TWS 账户窗口中的信息同步。然而，它也意味着用户无法自定义更新频率以适应更频繁或更少的数据需求。

这种更新机制对于需要定期监控其账户状况的用户来说是有用的，尤其是那些管理多个账户或有复杂财务状况的用户。通过定期接收更新的账户摘要，用户可以保持对其财务状况的最新了解，从而做出及时和信息充分的决策。

## Requesting Account Summary

请求账户摘要（Requesting Account Summary）是一种获取特定账户摘要的方法。此方法订阅了 TWS 中“账户摘要”（Account Summary）标签页所展示的账户摘要信息。客户可以通过使用特定的标签值来指定他们接收的数据。有关可用选项，请参见“账户摘要标签”（Account Summary Tags）部分。

此外，许多编程语言提供了导入 `AccountSummaryTags` 的方法，以检索所有标签值。

`EClient.reqAccountSummary` 方法的使用如下：

- `reqId: int`：唯一的请求标识符。
- `group: String`：设置为 “All” 以返回所有账户的账户摘要数据，或者设置为在 TWS 全局配置中已创建的特定顾问账户组名称。
- `tags: String`：希望得到的标签的逗号分隔列表。

重要提示：同一时间只允许有两个活跃的摘要订阅！

此方法允许用户获取有关其账户的关键财务信息，如资产总额、现金余额、购买力等。这对于需要跟踪和管理多个账户或复杂财务情况的用户尤其重要。通过定制标签值，用户可以确保他们接收到的信息正是他们所需要的，从而更有效地监控和管理他们的投资组合和财务状况。

```python
EClient.reqAccountSummary(9001, "All", AccountSummaryTags.AllTags)
```

### Account Summary Tags

| Tag                         | Description          |
| --------------------------- | -------------------- |
| AccountType                 | 账户类型             |
| NetLiquidation              | 净清算价值           |
| TotalCashValue              | 总现金价值           |
| SettledCash                 | 已结算现金           |
| AccruedCash                 | 应计现金             |
| BuyingPower                 | 购买力               |
| EquityWithLoanValue         | 贷款价值             |
| PreviousEquityWithLoanValue | 上一次贷款价值       |
| GrossPositionValue          | 总持仓价值           |
| ReqTEquity                  | 请求的总权益         |
| ReqTMargin                  | 请求的总保证金       |
| SMA                         | 特殊保证金账户       |
| InitMarginReq               | 初始保证金要求       |
| MaintMarginReq              | 维持保证金要求       |
| AvailableFunds              | 可用资金             |
| ExcessLiquidity             | 超额流动性           |
| Cushion                     | 缓冲                 |
| FullInitMarginReq           | 完整的初始保证金要求 |
| FullMaintMarginReq          | 完整的维持保证金要求 |
| FullAvailableFunds          | 完整的可用资金       |
| FullExcessLiquidity         | 完整的超额流动性     |
| LookAheadNextChange         | 向前看下一个变化     |
| LookAheadInitMarginReq      | 向前看初始保证金要求 |
| LookAheadMaintMarginReq     | 向前看维持保证金要求 |
| LookAheadAvailableFunds     | 向前看可用资金       |
| LookAheadExcessLiquidity    | 向前看超额流动性     |
| HighestSeverity             | 最高严重程度         |
| DayTradesRemaining          | 剩余的日内交易       |
| Leverage                    | 杠杆                 |
| $LEDGER                     | $分类账              |
| $LEDGER:CURRENCY            | $分类账:货币         |
| $LEDGER:ALL                 | $分类账:全部         |

### Receiving Account Summary

接收账户摘要（Receiving Account Summary）涉及使用 `EWrapper.accountSummary` 方法来接收账户信息。此方法提供了与 TWS 中“账户摘要”窗口相似的账户信息。`EWrapper.accountSummary` 方法的参数包括：

- `reqId: int`：请求的唯一标识符。
- `account: String`：账户 ID。
- `tag: String`：正在接收的账户属性。
- `value: String`：账户属性的值。
- `currency: String`：表示值的货币单位。

当通过 `EClient.reqAccountSummary` 请求账户摘要信息后，`accountSummary` 方法将接收到的账户信息呈现出来。这些信息可能包括账户的总资产、现金余额、购买力、未实现盈亏等，每个属性都有相应的货币单位。

此方法允许用户以编程方式访问和分析其在 TWS 中可见的同等级别的账户信息。通过此方法接收到的数据可以用于自动化的财务分析、实时监控或集成到更广泛的投资管理系统中。对于管理多个账户或需要详细财务报告的用户来说，这个功能尤其有价值，因为它提供了快速、准确的账户状态更新。

```python
def accountSummary(reqId: int, account: str, tag: str, value: str,currency: str):
  print("AccountSummary. ReqId:", reqId, "Account:", account,"Tag: ", tag, "Value:", value, "Currency:", currency)
```

----

`EWrapper.accountSummaryEnd` 方法用于通知当所有账户的信息已经接收完毕。此方法的参数包括：

- `reqId: int`：请求的标识符。

在使用 `EClient.reqAccountSummary` 方法请求账户摘要信息后，当所有相关账户的信息被成功接收时，`accountSummaryEnd` 方法将被调用。这表示账户摘要信息的接收已经完成，用户可以确定他们已经收到了所有请求的数据。

请注意，接收 `accountSummaryEnd` 的能力要求使用 TWS 版本 967 或更高版本。在链接账户结构中接收 `accountSummaryEnd` 特别重要，因为它标志着多个账户信息的汇总接收过程的结束。

这个方法在自动化财务监控和分析的背景下非常有用，尤其是对于那些管理多个账户的用户。通过确定所有账户信息已经接收完毕，用户可以更有效地处理和分析这些数据，确保他们的财务分析和决策基于完整且最新的信息。

```python
def accountSummaryEnd(self, reqId: int):
    print("AccountSummaryEnd. ReqId:", reqId)
```

### Cancel Account Summary

当不再需要账户摘要的订阅时，可以通过 `IBApi::EClient::cancelAccountSummary` 方法来取消订阅：

- `EClient.cancelAccountSummary` 方法的参数包括：
  - `reqId: int`：先前执行的账户请求的标识符。

这个方法允许用户停止接收之前通过 `reqAccountSummary` 请求的账户摘要信息。这对于管理数据流和避免接收不必要的信息非常重要，尤其是在用户已经完成对账户数据的分析或当账户数据不再需要实时更新时。

取消账户摘要订阅有助于确保应用程序不会继续接收不必要的数据更新，从而优化数据流量并减轻服务器的负担。对于需要动态管理数据订阅的用户或系统来说，这个功能尤其有用。通过使用 `cancelAccountSummary` 方法，用户可以确保他们的系统仅处理当前和相关的数据，从而提高效率和性能。

```python
EClient.cancelAccountSummary(9001)
```

## Account Updates

IBApi.EClient.reqAccountUpdates 函数创建了一个订阅，通过该订阅可以接收到账户和投资组合信息。这些信息与 TWS 账户窗口中显示的信息完全相同。与 TWS 账户窗口一样，除非有仓位变动，否则这些信息每三分钟更新一次。

在用 IBApi.EClient.reqAccountUpdates 发出订阅请求后，未实现和已实现的盈亏将被发送到 API 函数 IBApi.EWrapper.updateAccountValue。这些信息对应于 TWS 账户窗口中的数据，并且有不同的信息来源、更新频率以及不同的重置计划，与 TWS 投资组合窗口和相关 API 函数（下文提及）中的盈亏数据不同。特别是，显示在 TWS 账户窗口中的未实现盈亏信息，将在以下两种情况下更新：（1）该特定工具发生交易时；或（2）每3分钟更新一次。TWS 账户窗口中的已实现盈亏数据每天重置为0一次。

需要注意的是，账户窗口和投资组合窗口中显示的盈亏数据有时会不同，因为它们有不同的信息来源和重置计划。

### Requesting Account Updates

订阅特定账户的信息和投资组合。通过这种方法，可以开始/停止单个账户的订阅。作为订阅的结果，将在 EWrapper::updateAccountValue、EWrapper::updateAccountPortfolio、EWrapper::updateAccountTime 分别接收到账户的信息、投资组合和最后更新时间。所有账户价值和持仓将最初返回，然后只有在某个持仓发生变化时，或者如果账户价值每3分钟发生变化时，才会有更新。一次只能订阅一个账户。当之前的一个订阅仍然活跃时，为另一个账户发出第二次订阅请求将导致第一个订阅被取消，转而订阅第二个账户。

`EClient.reqAccountUpdates` 方法用于订阅或取消订阅特定账户的实时更新。该方法的参数包括：

- `subscribe: bool`：设置为 true 以开始订阅，设置为 false 以停止订阅。
- `acctCode: String`：请求信息的账户 ID（例如 U123456）。

当您希望接收有关特定账户的实时更新时，可以使用此方法开始订阅。这可能包括账户余额、持仓、交易活动等信息的更新。如果您已经获取了所需信息或不再需要实时更新，可以通过将 `subscribe` 参数设置为 false 来取消订阅。

这个功能对于需要实时监控其账户状态的用户非常有用，尤其是在市场波动较大或需要密切关注账户活动的情况下。通过使用 `reqAccountUpdates`，用户可以确保他们及时了解账户的最新状况，从而做出迅速且信息充分的决策。

```python
self.reqAccountUpdates(True, self.account)
```

### Receiving Account Updates

由此产生的账户和投资组合信息将通过 IBApi.EWrapper.updateAccountValue、IBApi.EWrapper.updatePortfolio、IBApi.EWrapper.updateAccountTime 和 IBApi.EWrapper.accountDownloadEnd 这些方法传递。

`EWrapper.updateAccountValue` 方法用于接收订阅账户的信息。当通过 `EClient.reqAccountUpdates` 订阅特定账户的实时更新时，此方法提供有关账户的最新信息。该方法的参数包括：

- `key: String`：正在更新的值的键。
- `value: String`：最新的值。
- `currency: String`：值所用的货币单位。
- `accountName: String`：账户标识符。

此方法在以下情况下被调用：

1. **初始回调**：在最初订阅账户更新后，将接收到一次所有相关账户值的更新。
2. **变化触发的更新**：在初始回调之后，只有当某个值发生变化时才会触发回调。这可能是由于持仓变化或每三分钟进行的定期更新。

需要注意的是，这种更新的频率最多每三分钟一次，且无法调整。

此方法允许用户实时了解其账户的关键财务信息，如资产总额、现金余额、持仓价值等。这对于需要密切监控账户状态的用户来说非常重要。通过实时更新，用户可以及时做出基于当前市场情况和账户状况的交易和投资决策。同时，此功能确保用户仅在相关数据发生变化时接收更新，从而优化数据流量和系统性能。

```python
def updateAccountValue(self, key: str, val: str, currency: str,accountName: str):
    print("UpdateAccountValue. Key:", key, "Value:", val, "Currency:", currency, "AccountName:", accountName)
```

----

接收订阅账户的信息。一次只能订阅一个账户。在最初调用 updateAccountValue 后，只有在值发生变化时才会进行回调。这发生在仓位变化时，或者最多每3分钟一次。这个频率无法调整。

`EWrapper.updatePortfolio` 方法用于接收订阅账户的投资组合信息。当通过 `EClient.reqAccountUpdates` 订阅特定账户的实时更新时，此方法提供有关账户投资组合的最新信息。该方法的参数包括：

- `contract: Contract`：正在更新的合约。
- `position: float`：持仓数量。
- `marketPrice: float`：市场价格。
- `marketValue: float`：市场价值。
- `averageCost: float`：平均成本。
- `unrealizedPNL: float`：未实现盈亏。
- `realizedPNL: float`：已实现盈亏。
- `accountName: str`：账户标识符。

```python
def updatePortfolio(self, contract: Contract, position: float, marketPrice: float, marketValue: float, averageCost: float, unrealizedPNL: float, realizedPNL: float, accountName: str):
    print("UpdatePortfolio.", "Symbol:", contract.symbol, "SecType:", contract.secType, "Exchange:", contract.exchange, "Position:", position, "MarketPrice:", marketPrice, "MarketValue:", marketValue, "AverageCost:", averageCost, "UnrealizedPNL:", unrealizedPNL, "RealizedPNL:", realizedPNL, "AccountName:", accountName)
```

----

接收订阅账户的投资组合。这个函数只会接收订阅账户的投资组合。在最初调用 updatePortfolio 后，只有在仓位发生变化时才会进行回调。

`EWrapper.updateAccountTime` 方法用于接收订阅账户的最后更新时间。当通过 `EClient.reqAccountUpdates` 订阅特定账户的实时更新时，此方法提供有关账户的最后更新时间。该方法的参数包括：

- `timeStamp: str`：最后更新时间。

```python
def updateAccountTime(self, timeStamp: str):
    print("UpdateAccountTime. Time:", timeStamp)
```

----

接收订阅账户的最后更新时间。这个函数只会接收订阅账户的最后更新时间。在最初调用 updateAccountTime 后，只有在值发生变化时才会进行回调。这发生在仓位变化时，或者最多每3分钟一次。这个频率无法调整。

`EWrapper.accountDownloadEnd` 方法用于通知当所有账户的信息已经接收完毕。此方法的参数包括：

- `accountName: str`：账户标识符。

```python
def accountDownloadEnd(self, accountName: str):
    print("Account download finished:", accountName)
```

### Account Value Keys

当请求 reqAccountUpdates 时，客户将接收到与各种账户键/值对应的值。下表记录了潜在的响应及其含义。

通过 IBApi.EWrapper.updateAccountValue 传递的账户值可以按以下方式分类：

商品：后缀为 “-C”
证券：后缀为 “-S”
总计：无后缀




| Key                              | 描述                                                                                                                                                 |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| AccountCode                      | 账户ID号                                                                                                                                             |
| AccountOrGroup                   | “All”表示返回所有账户的账户概要数据，或设置为在TWS全局配置中已创建的特定顾问账户组名称                                                               |
| AccountReady                     | 仅供内部使用                                                                                                                                         |
| AccountType                      | 标识IB账户结构                                                                                                                                       |
| AccruedCash                      | 股票、商品和证券的总累计现金价值                                                                                                                     |
| AccruedCash-C                    | 反映到目前为止当月在商品部分累积的借记和贷记利息，每日更新                                                                                           |
| AccruedCash-S                    | 反映到目前为止当月在证券部分累积的借记和贷记利息，每日更新                                                                                           |
| AccruedDividend                  | 累积的股息的总投资组合价值                                                                                                                           |
| AccruedDividend-C                | 商品部分累积但未支付的股息                                                                                                                           |
| AccruedDividend-S                | 证券部分累积但未支付的股息                                                                                                                           |
| AvailableFunds                   | 这个值告诉你有多少可用于交易                                                                                                                         |
| AvailableFunds-C                 | 净清算价值 - 初始保证金                                                                                                                              |
| AvailableFunds-S                 | 贷款价值的股本 - 初始保证金                                                                                                                          |
| Billable                         | 国库券的总投资组合价值                                                                                                                               |
| Billable-C                       | 商品部分的国库券价值                                                                                                                                 |
| Billable-S                       | 证券部分的国库券价值                                                                                                                                 |
| BuyingPower                      | 现金账户：最小值（贷款价值的股本，前一天的贷款价值的股本）-初始保证金，标准保证金账户：最小值（贷款价值的股本，前一天的贷款价值的股本）-初始保证金*4 |
| CashBalance                      | 交易时认可的现金 + 期货盈亏                                                                                                                          |
| CorporateBondValue               | 非政府债券的价值，如公司债券和市政债券                                                                                                               |
| Currency                         | 按货币分组的未平仓头寸                                                                                                                               |
| Cushion                          | 净清算价值的超额流动性百分比                                                                                                                         |
| DayTradesRemaining               | 在检测到日内交易模式之前可以进行的开/平交易数量                                                                                                      |
| DayTradesRemainingT+1            | 明天之前可以进行的开/平交易数量，在检测到日内交易模式之前                                                                                            |
| DayTradesRemainingT+2            | 从今天起两天内可以进行的开/平交易数量，在检测到日内交易模式之前                                                                                      |
| DayTradesRemainingT+3            | 从今天起三天内可以进行的开/平交易数量，在检测到日内交易模式之前                                                                                      |
| DayTradesRemainingT+4            | 从今天起四天内可以进行的开/平交易数量，在检测到日内交易模式之前                                                                                      |
| EquityWithLoanValue              | 确定客户是否拥有启动或维持证券头寸所需资产的基础                                                                                                     |
| EquityWithLoanValue-C            | 现金账户：总现金价值 + 商品期权价值 - 期货维持保证金要求 + 最小值（0，期货盈亏）保证金账户：总现金价值 + 商品期权价值 - 期货维持保证金要求           |
| EquityWithLoanValue-S            | 现金账户：结算现金保证金账户：总现金价值 + 股票价值 + 债券价值 +（非美国和加拿大证券期权价值）                                                       |
| ExcessLiquidity                  | 这个值显示你的保证金余地，在清算之前                                                                                                                 |
| ExcessLiquidity-C                | 贷款价值的股本 - 维持保证金                                                                                                                          |
| ExcessLiquidity-S                | 净清算价值 - 维持保证金                                                                                                                              |
| ExchangeRate                     | 货币对您的基础货币的汇率                                                                                                                             |
| FullAvailableFunds               | 整个投资组合的可用资金，没有折扣或日内信贷                                                                                                           |
| FullAvailableFunds-C             | 净清算价值 - 全额初始保证金                                                                                                                          |
| FullAvailableFunds-S             | 贷款价值的股本 - 全额初始保证金                                                                                                                      |
| FullExcessLiquidity              | 整个投资组合的超额流动性，没有折扣或日内信贷                                                                                                         |
| FullExcessLiquidity-C            | 净清算价值 - 全额维持保证金                                                                                                                          |
| FullExcessLiquidity-S            | 贷款价值的股本 - 全额维持保证金                                                                                                                      |
| FullInitMarginReq                | 整个投资组合的初始保证金，没有折扣或日内信贷                                                                                                         |
| FullInitMarginReq-C              | 商品部分投资组合的初始保证金，没有折扣或日内信贷                                                                                                     |
| FullInitMarginReq-S              | 证券部分投资组合的初始保证金，没有折扣或日内信贷                                                                                                     |
| FullMaintMarginReq               | 整个投资组合的维持保证金，没有折扣或日内信贷                                                                                                         |
| FullMaintMarginReq-C             | 商品部分投资组合的维持保证金，没有折扣或日内信贷                                                                                                     |
| FullMaintMarginReq-S             | 证券部分投资组合的维持保证金，没有折扣或日内信贷                                                                                                     |
| FundValue                        | 基金价值（货币市场基金 + 互惠基金）                                                                                                                  |
| FutureOptionValue                | 期货期权的实时市值                                                                                                                                   |
| FuturesPNL                       | 自上次结算以来期货价值的实时变化                                                                                                                     |
| FxCashBalance                    | 相关IB-UKL账户中的现金余额                                                                                                                           |
| GrossPositionValue               | 证券部分的总头寸价值                                                                                                                                 |
| GrossPositionValue-S             | 长股价值 + 短股价值 + 长期权价值 + 短期权价值                                                                                                        |
| IndianStockHaircut               | IB-IN账户的保证金规则                                                                                                                                |
| InitMarginReq                    | 整个投资组合的初始保证金要求                                                                                                                         |
| InitMarginReq-C                  | 商品部分的初始保证金，以基础货币计算                                                                                                                 |
| InitMarginReq-S                  | 证券部分的初始保证金，以基础货币计算                                                                                                                 |
| IssuerOptionValue                | 发行期权的实时市值                                                                                                                                   |
| Leverage-S                       | 证券部分的GrossPositionValue / NetLiquidation                                                                                                        |
| LookAheadNextChange              | 向前看值生效的时间                                                                                                                                   |
| LookAheadAvailableFunds          | 这个值反映了下一个保证金变化时您的可用资金                                                                                                           |
| LookAheadAvailableFunds-C        | 净清算价值 - 向前看初始保证金                                                                                                                        |
| LookAheadAvailableFunds-S        | 贷款价值的股本 - 向前看初始保证金                                                                                                                    |
| LookAheadExcessLiquidity         | 这个值反映了下一个保证金变化时您的超额流动性                                                                                                         |
| LookAheadExcessLiquidity-C       | 净清算价值 - 向前看维持保证金                                                                                                                        |
| LookAheadExcessLiquidity-S       | 贷款价值的股本 - 向前看维持保证金                                                                                                                    |
| LookAheadInitMarginReq           | 下一期间保证金变化时整个投资组合的初始保证金要求                                                                                                     |
| LookAheadInitMarginReq-C         | 下一期间保证金变化时的初始保证金要求，以账户的基础货币计算                                                                                           |
| LookAheadInitMarginReq-S         | 下一期间保证金变化时的初始保证金要求，以账户的基础货币计算                                                                                           |
| LookAheadMaintMarginReq          | 下一期间的保证金变化时整个投资组合的维持保证金要求                                                                                                   |
| LookAheadMaintMarginReq          | 下一期间的保证金变化时整个投资组合的维持保证金要求                                                                                                   |
| LookAheadMaintMarginReq-C        | 下一期间的保证金变化时账户基础货币中的维持保证金要求                                                                                                 |
| LookAheadMaintMarginReq-S        | 下一期间的保证金变化时账户基础货币中的维持保证金要求                                                                                                 |
| MaintMarginReq                   | 整个投资组合的维持保证金要求                                                                                                                         |
| MaintMarginReq-C                 | 商品部分的维持保证金                                                                                                                                 |
| MaintMarginReq-S                 | 证券部分的维持保证金                                                                                                                                 |
| MoneyMarketFundValue             | 排除共同基金外的货币市场基金的市值                                                                                                                   |
| MutualFundValue                  | 排除货币市场基金外的共同基金的市值                                                                                                                   |
| NetDividend                      | 证券和商品部分的应付/应收红利总值                                                                                                                    |
| NetLiquidation                   | 确定账户资产价格的基础                                                                                                                               |
| NetLiquidation-C                 | 总现金价值 + 期货盈亏 + 商品期权价值                                                                                                                 |
| NetLiquidation-S                 | 总现金价值 + 股票价值 + 证券期权价值 + 债券价值                                                                                                      |
| NetLiquidationByCurrency         | 各个货币的净清算价值                                                                                                                                 |
| OptionMarketValue                | 期权的实时市价                                                                                                                                       |
| PASharesValue                    | 整个投资组合的个人账户股份价值                                                                                                                       |
| PASharesValue-C                  | 商品部分的个人账户股份价值                                                                                                                           |
| PASharesValue-S                  | 证券部分的个人账户股份价值                                                                                                                           |
| PostExpirationExcess             | 到期后总预计“超额流动性”                                                                                                                             |
| PostExpirationExcess-C           | 根据即将到期的合约在商品部分预计的到期后“超额流动性”                                                                                                 |
| PostExpirationExcess-S           | 根据即将到期的合约在证券部分预计的到期后“超额流动性”                                                                                                 |
| PostExpirationMargin             | 到期后总预计“保证金”                                                                                                                                 |
| PostExpirationMargin-C           | 根据即将到期的合约在商品部分预计的到期后保证金价值                                                                                                   |
| PostExpirationMargin-S           | 根据即将到期的合约在证券部分预计的到期后保证金价值                                                                                                   |
| PreviousDayEquityWithLoanValue   | 上一天16:00 ET时证券部分的可贷款股权价值                                                                                                             |
| PreviousDayEquityWithLoanValue-S | 上一天16:00 ET时的可贷款股权价值                                                                                                                     |
| RealCurrency                     | 按货币分组的未平仓头寸                                                                                                                               |
| RealizedPnL                      | 已平仓头寸的利润，即进场执行成本与出场执行成本之差                                                                                                   |
| RegTEquity                       | 通用账户的T规则股权                                                                                                                                  |
| RegTEquity-S                     | 证券部分的T规则股权                                                                                                                                  |
| RegTMargin                       | 通用账户的T规则保证金                                                                                                                                |
| RegTMargin-S                     | 证券部分的T规则保证金                                                                                                                                |
| SMA                              | T规则账户中证券市值增加时产生的信贷额度                                                                                                              |
| SMA-S                            | 证券部分的T规则特别备忘账户余额                                                                                                                      |
| SegmentTitle                     | 账户部分的名称                                                                                                                                       |
| StockMarketValue                 | 股票的实时市价                                                                                                                                       |
| TBondValue                       | 国库债券的价值                                                                                                                                       |
| TBillValue                       | 国库券的价值                                                                                                                                         | LookAheadMaintMarginReq         | 下一期间的保证金变化时整个投资组合的维持保证金要求            
| LookAheadMaintMarginReq-C        | 下一期间的保证金变化时账户基础货币中的维持保证金要求                                                                                                 |
| LookAheadMaintMarginReq-S        | 下一期间的保证金变化时账户基础货币中的维持保证金要求                                                                                                 |
| MaintMarginReq                   | 整个投资组合的维持保证金要求                                                                                                                         |
| MaintMarginReq-C                 | 商品部分的维持保证金                                                                                                                                 |
| MaintMarginReq-S                 | 证券部分的维持保证金                                                                                                                                 |
| MoneyMarketFundValue             | 排除共同基金外的货币市场基金的市值                                                                                                                   |
| MutualFundValue                  | 排除货币市场基金外的共同基金的市值                                                                                                                   |
| NetDividend                      | 证券和商品部分的应付/应收红利总值                                                                                                                    |
| NetLiquidation                   | 确定账户资产价格的基础                                                                                                                               |
| NetLiquidation-C                 | 总现金价值 + 期货盈亏 + 商品期权价值                                                                                                                 |
| NetLiquidation-S                 | 总现金价值 + 股票价值 + 证券期权价值 + 债券价值                                                                                                      |
| NetLiquidationByCurrency         | 各个货币的净清算价值                                                                                                                                 |
| OptionMarketValue                | 期权的实时市价                                                                                                                                       |
| PASharesValue                    | 整个投资组合的个人账户股份价值                                                                                                                       |
| PASharesValue-C                  | 商品部分的个人账户股份价值                                                                                                                           |
| PASharesValue-S                  | 证券部分的个人账户股份价值                                                                                                                           |
| PostExpirationExcess             | 到期后总预计“超额流动性”                                                                                                                             |
| PostExpirationExcess-C           | 根据即将到期的合约在商品部分预计的到期后“超额流动性”                                                                                                 |
| PostExpirationExcess-S           | 根据即将到期的合约在证券部分预计的到期后“超额流动性”                                                                                                 |
| PostExpirationMargin             | 到期后总预计“保证金”                                                                                                                                 |
| PostExpirationMargin-C           | 根据即将到期的合约在商品部分预计的到期后保证金价值                                                                                                   |
| PostExpirationMargin-S           | 根据即将到期的合约在证券部分预计的到期后保证金价值                                                                                                   |
| PreviousDayEquityWithLoanValue   | 上一天16:00 ET时证券部分的可贷款股权价值                                                                                                             |
| PreviousDayEquityWithLoanValue-S | 上一天16:00 ET时的可贷款股权价值                                                                                                                     |
| RealCurrency                     | 按货币分组的未平仓头寸                                                                                                                               |
| RealizedPnL                      | 已平仓头寸的利润，即进场执行成本与出场执行成本之差                                                                                                   |
| RegTEquity                       | 通用账户的T规则股权                                                                                                                                  |
| RegTEquity-S                     | 证券部分的T规则股权                                                                                                                                  |
| RegTMargin                       | 通用账户的T规则保证金                                                                                                                                |
| RegTMargin-S                     | 证券部分的T规则保证金                                                                                                                                |
| SMA                              | T规则账户中证券市值增加时产生的信贷额度                                                                                                              |
| SMA-S                            | 证券部分的T规则特别备忘账户余额                                                                                                                      |
| SegmentTitle                     | 账户部分的名称                                                                                                                                       |
| StockMarketValue                 | 股票的实时市价                                                                                                                                       |
| TBondValue                       | 国库债券的价值                                                                                                                                       |
| TBillValue                       | 国库券的价值                                                                                                                                         |
| LookAheadMaintMarginReq          | 下一期间的保证金变化时整个投资组合的维持保证金要求                                                                                                   |
| LookAheadMaintMarginReq-C        | 下一期间的保证金变化时账户基础货币中的维持保证金要求                                                                                                 |
| LookAheadMaintMarginReq-S        | 下一期间的保证金变化时账户基础货币中的维持保证金要求                                                                                                 |
| MaintMarginReq                   | 整个投资组合的维持保证金要求                                                                                                                         |
| MaintMarginReq-C                 | 商品部分的维持保证金                                                                                                                                 |
| MaintMarginReq-S                 | 证券部分的维持保证金                                                                                                                                 |
| MoneyMarketFundValue             | 排除共同基金外的货币市场基金的市值                                                                                                                   |
| MutualFundValue                  | 排除货币市场基金外的共同基金的市值                                                                                                                   |
| NetDividend                      | 证券和商品部分的应付/应收红利总值                                                                                                                    |
| NetLiquidation                   | 确定账户资产价格的基础                                                                                                                               |
| NetLiquidation-C                 | 总现金价值 + 期货盈亏 + 商品期权价值                                                                                                                 |
| NetLiquidation-S                 | 总现金价值 + 股票价值 + 证券期权价值 + 债券价值                                                                                                      |
| NetLiquidationByCurrency         | 各个货币的净清算价值                                                                                                                                 |
| OptionMarketValue                | 期权的实时市价                                                                                                                                       |
| PASharesValue                    | 整个投资组合的个人账户股份价值                                                                                                                       |
| PASharesValue-C                  | 商品部分的个人账户股份价值                                                                                                                           |
| PASharesValue-S                  | 证券部分的个人账户股份价值                                                                                                                           |
| PostExpirationExcess             | 到期后总预计“超额流动性”                                                                                                                             |
| PostExpirationExcess-C           | 根据即将到期的合约在商品部分预计的到期后“超额流动性”                                                                                                 |
| PostExpirationExcess-S           | 根据即将到期的合约在证券部分预计的到期后“超额流动性”                                                                                                 |
| PostExpirationMargin             | 到期后总预计“保证金”                                                                                                                                 |
| PostExpirationMargin-C           | 根据即将到期的合约在商品部分预计的到期后保证金价值                                                                                                   |
| PostExpirationMargin-S           | 根据即将到期的合约在证券部分预计的到期后保证金价值                                                                                                   |
| PreviousDayEquityWithLoanValue   | 上一天16:00 ET时证券部分的可贷款股权价值                                                                                                             |
| PreviousDayEquityWithLoanValue-S | 上一天16:00 ET时的可贷款股权价值                                                                                                                     |
| RealCurrency                     | 按货币分组的未平仓头寸                                                                                                                               |
| RealizedPnL                      | 已平仓头寸的利润，即进场执行成本与出场执行成本之差                                                                                                   |
| RegTEquity                       | 通用账户的T规则股权                                                                                                                                  |
| RegTEquity-S                     | 证券部分的T规则股权                                                                                                                                  |
| RegTMargin                       | 通用账户的T规则保证金                                                                                                                                |
| RegTMargin-S                     | 证券部分的T规则保证金                                                                                                                                |
| SMA                              | T规则账户中证券市值增加时产生的信贷额度                                                                                                              |
| SMA-S                            | 证券部分的T规则特别备忘账户余额                                                                                                                      |
| SegmentTitle                     | 账户部分的名称                                                                                                                                       |
| StockMarketValue                 | 股票的实时市价                                                                                                                                       |
| TBondValue                       | 国库债券的价值                                                                                                                                       |
| TBillValue                       | 国库券的价值                                                                                                                                         |
| TotalCashBalance                 | 包括未来盈亏的总现金余额                                                                                                                             |
| TotalCashValue                   | 股票、商品和证券的总现金价值                                                                                                                         |
| TotalCashValue-C                 | 商品部分的现金余额                                                                                                                                   |
| TotalCashValue-S                 | 证券部分的现金余额                                                                                                                                   |
| TradingType-S                    | 账户类型                                                                                                                                             |
| UnrealizedPnL                    | 未平仓头寸的当前市值与平均成本之差，或价值 - 平均成本                                                                                                |
| WarrantValue                     | 认股权证的价值                                                                                                                                       |
| WhatIfPMEnabled                  | 检查在投资组合保证金模型下的预计保证金要求                                                                                                           |

### Cancel Account Updates

当不再需要账户更新的订阅时，可以通过调用 `IBApi.EClient.reqAccountUpdates` 方法并将订阅标志设置为 False 来取消订阅。

- `EClient.reqAccountUpdates` 方法的参数包括：
  - `subscribe: bool`：设置为 true 以开始订阅，设置为 false 以停止订阅。
  - `acctCode: String`：请求信息的账户 ID（例如 U123456）。

需要注意的几个重要点：

1. **“accountReady”关键字**：在调用 `IBApi.EClient.reqAccountUpdates` 后，`IBApi.EWrapper.updateAccountValue` 中返回的一个重要关键字是布尔值 ‘accountReady’。如果返回的 `accountReady` 值为 false，这意味着 IB 服务器在那一刻正在重置，即账户“未就绪”。发生这种情况时，当前更新中返回到 `IBApi::EWrapper::updateAccountValue` 的后续键值可能是过时的或不正确的。

2. **一次仅一账户订阅**：一次只能订阅一个账户。尝试在未先取消一个活跃订阅的情况下进行第二个订阅不会产生任何错误消息，但它会用新账户覆盖已订阅的账户。对于财务顾问（FA）账户结构，有一种指定账户代码的替代方法，以便返回“所有”子账户的信息 - 这是通过在账户号码末尾添加字母 ‘A’ 来完成的，例如 `reqAccountUpdates(true, “F123456A”)`。

使用 `reqAccountUpdates` 方法取消订阅对于管理数据流和优化系统性能非常重要，尤其是当不再需要实时监控账户状态时。通过取消不必要的订阅，可以确保应用程序不会继续接收不必要的数据更新。

```python
reqAccountUpdates(true, "F123456A")。
```


## Account Update by Model

### Requesting Account Update by Model

`IBApi.EClient.reqAccountUpdatesMulti` 可以用于任何账户结构，以从一个或多个账户和/或模型中创建同时的账户价值订阅。与 IBApi.EClient.reqAccountUpdates 一样，返回的数据将与 TWS 账户窗口内显示的数据相匹配。

- `EClient.reqAccountUpdatesMulti` 方法的参数包括：
  - `reqId: int`：请求 ID。
  - `account: String`：请求信息的账户 ID（例如 U123456）。
  - `modelCode: String`：请求信息的模型代码（例如 “Model_1”）。
  - `ledgerAndNLV: bool`：如果设置为 true，则返回账户总余额和净清算价值。如果设置为 false，则返回账户总余额和净清算价值。

该方法用于请求账户和/或模型的账户更新。

需要注意的是，IBApi.EClient.reqAccountUpdatesMulti 不能与在 IBroker 账户中使用“所有”作为账户的情况一起使用，如果该账户拥有超过50个子账户。

对于财务顾问，账户参数中的组（group）可以被配置文件名称（profile name）所替代。

```python
self.reqAccountUpdatesMulti(reqId, self.account, "", True)
```

### Receiving Account Update by Model

通过 IBApi.EWrapper.accountUpdateMulti 和 IBApi.EWrapper.accountUpdateMultiEnd 方法，可以接收到账户和投资组合信息的更新。

`EWrapper.accountUpdateMulti` 方法用于接收订阅账户的信息。当通过 `EClient.reqAccountUpdatesMulti` 订阅特定账户的实时更新时，此方法提供有关账户的最新信息。该方法的参数包括：

- `reqId: int`：请求 ID。
- `account: String`：账户标识符。
- `modelCode: String`：模型代码。
- `key: String`：账户值的键。
- `value: String`：账户值。
- `currency: String`：货币。

这个方法提供账户的更新信息。当所有的更新信息都已发送完毕时，会调用 IBApi.EWrapper.accountUpdateMultiEnd 方法，其主要参数是 requestId，标识完成了哪个请求的更新。

```python
def accountUpdateMulti(self, reqId: int, account: str, modelCode: str, key: str, value: str, currency: str):
    print("AccountUpdateMulti. ReqId:", reqId, "Account:", account, "ModelCode:", modelCode, "Key:", key, "Value:", value, "Currency:", currency)
```

----

接收订阅账户的信息。一次只能订阅一个账户。在最初调用 accountUpdateMulti 后，只有在值发生变化时才会进行回调。这发生在仓位变化时，或者最多每3分钟一次。这个频率无法调整。

`EWrapper.accountUpdateMultiEnd` 方法用于通知当所有账户的信息已经接收完毕。此方法的参数包括：

- `reqId: int`：请求 ID。

```python
def accountUpdateMultiEnd(self, reqId: int):
    print("AccountUpdateMultiEnd. ReqId:", reqId)
```

### Cancel Account Update by Model

当不再需要账户更新的订阅时，可以通过调用 `IBApi.EClient.reqAccountUpdatesMulti` 方法并将订阅标志设置为 False 来取消订阅。

- `EClient.reqAccountUpdatesMulti` 方法的参数包括：
  - `reqId: int`：请求 ID。
  - `account: String`：请求信息的账户 ID（例如 U123456）。
  - `modelCode: String`：请求信息的模型代码（例如 “Model_1”）。
  - `ledgerAndNLV: bool`：如果设置为 true，则返回账户总余额和净清算价值。如果设置为 false，则返回账户总余额和净清算价值。


```python
self.reqAccountUpdatesMulti(reqId, self.account, "", False)
```

## Family Codes

通过 API 可以确定一个账户是否属于某个账户家族，并且可以使用 reqFamilyCodes 函数来找到该家族的代码。

例如，如果个人账户 U112233 属于账号为 F445566 的财务顾问下，当为账户 U112233 的用户调用 reqFamilyCodes 函数时，将返回家族代码 “F445566A”，这表明它属于那个账户家族。这个功能对于理解和管理属于财务顾问管理下的多个账户的组织结构非常有用。

### Requesting Family Codes

当需要请求一个账户的家族代码，例如判断它是否是财务顾问（FA）账户、IBroker账户，或者是相关联的账户时，可以使用特定的 API 函数。这个函数通常被设计用来识别账户的类型和它在更大的组织结构中的位置，这对于管理和监控多账户系统尤为重要。

例如，在交互式经纪人（Interactive Brokers, IB）的 API 中，这个功能可能是通过调用 reqFamilyCodes 函数实现的。当这个函数被调用时，它会返回与请求的账户相关的所有家族代码。这些家族代码有助于识别账户是独立账户、属于某个财务顾问的一部分，或者是IBroker系统中的其他类型账户。通过这些信息，用户或开发者可以更好地理解和管理他们的投资组合。

- `EClient.reqFamilyCodes` 方法的参数包括：
  - `familyCodes: ListOfFamilyCode`：家族代码列表。

```python
self.reqFamilyCodes()
```

### Receiving Family Codes

当调用 reqFamilyCodes 函数时，会调用 IBApi.EWrapper.familyCodes 方法，其主要参数是 familyCodes，标识了账户的家族代码。

```python
def familyCodes(self, familyCodes: ListOfFamilyCode):
    print("Family Codes:")
    for familyCode in familyCodes:
        print("FamilyCode.", familyCode)
```

## Managed Accounts

一个用户名可以处理多个账户。如在连接部分提到的，一旦连接建立，TWS（交易工作站）将自动发送一个被管理账户的列表。这个列表也可以通过 `IBApi.EClient.reqManagedAccts` 方法获取。

### Requesting Managed Accounts

当需要请求一个账户的家族代码，例如判断它是否是财务顾问（FA）账户、IBroker账户，或者是相关联的账户时，可以使用特定的 API 函数。这个函数通常被设计用来识别账户的类型和它在更大的组织结构中的位置，这对于管理和监控多账户系统尤为重要。

例如，在交互式经纪人（Interactive Brokers, IB）的 API 中，这个功能可能是通过调用 reqFamilyCodes 函数实现的。当这个函数被调用时，它会返回与请求的账户相关的所有家族代码。这些家族代码有助于识别账户是独立账户、属于某个财务顾问的一部分，或者是IBroker系统中的其他类型账户。通过这些信息，用户或开发者可以更好地理解和管理他们的投资组合。

- `EClient.reqManagedAccts` 方法的参数包括：
  - `account: String`：账户标识符。

```python
self.reqManagedAccts()
```

### Receiving Managed Accounts

当调用 reqManagedAccts 函数时，会调用 IBApi.EWrapper.managedAccounts 方法，其主要参数是 accountsList，标识了账户的家族代码。

```python
def managedAccounts(self, accountsList: str):
    print("Account list:", accountsList)
```

## Positions


`IBApi.EClient.reqAccountUpdates` 函数的一个限制是它一次只能用于单个账户。为了创建来自多个账户的持仓更新的订阅，可以使用 `IBApi.EClient.reqPositions` 函数。

**注意：reqPositions** 函数在介绍经纪人或财务顾问主账户中不可用，如果这些账户拥有大量的子账户（>50），这是为了优化 TWS/IB Gateway 的性能。
相反，可以使用 reqPositionsMulti 函数来订阅来自个别子账户的更新。此外，配置为按需账户查找的 IBroker 账户也无法使用此功能。

最初调用 reqPositions 后，将返回所有关联账户中所有持仓的信息，随后会有 IBApi::EWrapper::positionEnd 回调。
此后，当某个持仓发生变化时，更新将返回到 IBApi::EWrapper::position 函数。要取消 reqPositions 订阅，请调用 IBApi::EClient::cancelPositions。

### Request Positions

EClient.reqPositions()

订阅所有可访问账户的持仓更新。最初会发送所有持仓信息，之后只有在持仓发生变化时才会发送更新。

### Receive Positions

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

### Cancel Positions RequestCopy Location

EClient.cancelPositions()

取消之前通过 `EClient.reqPositions()` 方法发起的持仓订阅请求。

## Positions By Model

`IBApi.EClient.reqPositionsMulti` 函数可用于任何账户结构，用于订阅多个账户和/或模型的持仓更新。如果没有多个账户或模型可用，账户和模型参数是可选的。使用这个函数来针对特定的账户子集进行操作比使用 `IBApi.EClient.reqPositions` 更为高效。在账户参数中，可以接受配置文件名称代替组（group）。

### Request Positions By Model

`EClient.reqPositionsMulti` 方法用于请求账户和/或模型的持仓订阅。该方法的参数包括：

- `requestId: int`：请求的标识符。
- `account: String`：如果提供了账户 ID，只会传送属于指定模型的该账户的持仓。
- `modelCode: String`：我们感兴趣的模型的持仓代码。

此方法最初会返回所有持仓，然后实时返回任何持仓变化的更新。这对于实时监控和管理多个账户或模型中的持仓非常有用，特别是在需要跟踪特定模型或账户持仓的场景中。

```python
self.reqPositionsMulti(requestid, "U1234567", "")
```

### Receive Positions By Model

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

### Cancel Positions By Model Request

`EClient.cancelPositionsMulti` 方法用于取消之前通过 `EClient.reqPositionsMulti()` 方法发起的持仓订阅请求。该方法的参数包括：

- `requestId: int`：请求的标识符。

```python
self.cancelPositionsMulti(requestid)
```

## Profit & Loss (PnL)


可以发起请求以接收有关账户的日收益与亏损（P&L）和未实现收益与亏损的实时更新，或者针对个别持仓的收益与亏损。财务顾问还可以请求“所有”子账户的收益与亏损数据，或者针对一个投资组合模型的收益与亏损。
此外，还可以扩展到包括账户或个别持仓层面的已实现收益与亏损信息。

下面演示的 P&L API 功能返回在 TWS 当前版本的投资组合窗口中显示的数据。因此，收益与亏损值是基于在 TWS 全局配置中指定的重置计划（默认为特定于工具的重置计划）计算的，这个设置也会影响发送到相关 API 功能的值。
另外，在 TWS 中，如果且仅当账户窗口的虚拟外汇部分展开时，来自虚拟外汇头寸的收益与亏损数据才会包含在账户的收益与亏损中。

### Request P&L for individual positions

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

### Receive P&L for individual positions

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

### Cancel P&L request for individual positionsCopy Location

`EClient.cancelPnLSingle` 方法用于取消收益与亏损（P&L）订阅。该方法的参数包括：

- `reqId: int`：用于跟踪的请求标识符。

```python
self.cancelPnLSingle(requestId)
```

### Request P&L for accounts

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

### Receive P&L for accounts

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

### Cancel P&L subscription requests for accounts

`EClient.cancelPnL` 方法用于取消实时更新的日收益与亏损（PnL）订阅。该方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。

此方法通过指定请求标识符来取消之前通过 `EClient.reqPnL` 发起的实时日收益与亏损更新订阅。这对于停止接收不再需要的或相关性较低的数据更新非常有用。

```python
self.cancelPnL(reqId)
```

## White Branding User Info

这个函数将返回与用户关联的白标（White Branding）ID。

请注意，如果请求的用户名没有与任何白标实体关联，那么将不会返回任何内容。

### Requesting White Branding InfoCopy Location

`EClient.reqUserInfo` 方法用于请求用户信息。该方法的参数包括：

- `reqId: int`：请求 ID。

通过指定请求 ID，此函数用于请求关于当前用户的信息。这可能包括与用户账户关联的特定数据，如白标（White Branding）ID。
如果用户没有与任何特定实体（如白标实体）关联，那么该请求可能不会返回任何数据。这个功能对于在进行API交互时识别和确认用户身份非常有用。

```python
self.reqUserInfo(reqId)
```

### Receiving White Branding Info

`EWrapper.userInfo` 方法用于接收用户信息。该方法的参数包括：

- `reqId: int`：给定请求的标识符。
- `whiteBrandingId: String`：白标（White Branding）实体的标识符。

此方法在收到 `EClient.reqUserInfo` 请求后被触发，用于提供请求的用户信息，包括与用户关联的白标实体的标识符。这对于获取和处理用户特定的配置信息或身份标识非常有用，特别是在需要区分不同用户或用户组的情况下。

```python
def userInfo(self, reqId: int, whiteBrandingId: str):
    print("UserInfo.", "ReqId:", reqId, "WhiteBrandingId:", whiteBrandingId)
```