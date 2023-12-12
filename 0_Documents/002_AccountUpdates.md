# Account Updates

IBApi.EClient.reqAccountUpdates 函数创建了一个订阅，通过该订阅可以接收到账户和投资组合信息。这些信息与 TWS 账户窗口中显示的信息完全相同。与 TWS 账户窗口一样，除非有仓位变动，否则这些信息每三分钟更新一次。

在用 IBApi.EClient.reqAccountUpdates 发出订阅请求后，未实现和已实现的盈亏将被发送到 API 函数 IBApi.EWrapper.updateAccountValue。这些信息对应于 TWS 账户窗口中的数据，并且有不同的信息来源、更新频率以及不同的重置计划，与 TWS 投资组合窗口和相关 API 函数（下文提及）中的盈亏数据不同。特别是，显示在 TWS 账户窗口中的未实现盈亏信息，将在以下两种情况下更新：（1）该特定工具发生交易时；或（2）每3分钟更新一次。TWS 账户窗口中的已实现盈亏数据每天重置为0一次。

需要注意的是，账户窗口和投资组合窗口中显示的盈亏数据有时会不同，因为它们有不同的信息来源和重置计划。

## Requesting Account Updates

订阅特定账户的信息和投资组合。通过这种方法，可以开始/停止单个账户的订阅。作为订阅的结果，将在 EWrapper::updateAccountValue、EWrapper::updateAccountPortfolio、EWrapper::updateAccountTime 分别接收到账户的信息、投资组合和最后更新时间。所有账户价值和持仓将最初返回，然后只有在某个持仓发生变化时，或者如果账户价值每3分钟发生变化时，才会有更新。一次只能订阅一个账户。当之前的一个订阅仍然活跃时，为另一个账户发出第二次订阅请求将导致第一个订阅被取消，转而订阅第二个账户。

EClient.reqAccountUpdates 
* @param subscribe true = 开始订阅，false = 停止订阅
* @param acctCode 要订阅的账户代码

```python
EClient.reqAccountUpdates(True, "DU12345")
```

## Receiving Account Updates

由此产生的账户和投资组合信息将通过 IBApi.EWrapper.updateAccountValue、IBApi.EWrapper.updatePortfolio、IBApi.EWrapper.updateAccountTime 和 IBApi.EWrapper.accountDownloadEnd 这些方法传递。

EWrapper.updateAccountValue 
* @param key 账户信息的键值
* @param val 账户信息的值
* @param currency 账户信息的货币
* @param accountName 账户信息的账户名称

接收订阅账户的信息。一次只能订阅一个账户。在最初调用 updateAccountValue 后，只有在值发生变化时才会进行回调。这发生在仓位变化时，或者最多每3分钟一次。这个频率无法调整。

```python

def updateAccountValue(self, key: str, val: str, currency: str,accountName: str):
    print("UpdateAccountValue. Key:", key, "Value:", val, "Currency:", currency, "AccountName:", accountName)

```

----

接收订阅账户的信息。一次只能订阅一个账户。在最初调用 updateAccountValue 后，只有在值发生变化时才会进行回调。这发生在仓位变化时，或者最多每3分钟一次。这个频率无法调整。

EWrapper.updatePortfolio
* @param contract 账户持仓的合约
* @param position 账户持仓的数量
* @param marketPrice 账户持仓的市场价格
* @param marketValue 账户持仓的市场价值
* @param averageCost 账户持仓的平均成本
* @param unrealizedPNL 账户持仓的未实现盈亏
* @param realizedPNL 账户持仓的已实现盈亏
* @param accountName 账户持仓的账户名称

```python

def updatePortfolio(self, contract: Contract, position: float, marketPrice: float, marketValue: float, averageCost: float, unrealizedPNL: float, realizedPNL: float, accountName: str):
    print("UpdatePortfolio.", "Symbol:", contract.symbol, "SecType:", contract.secType, "Exchange:", contract.exchange, "Position:", position, "MarketPrice:", marketPrice, "MarketValue:", marketValue, "AverageCost:", averageCost, "UnrealizedPNL:", unrealizedPNL, "RealizedPNL:", realizedPNL, "AccountName:", accountName)
    
```

----

接收订阅账户的投资组合。这个函数只会接收订阅账户的投资组合。在最初调用 updatePortfolio 后，只有在仓位发生变化时才会进行回调。

EWrapper.updateAccountTime
* @param timeStamp 账户信息的时间戳

```python
def updateAccountTime(self, timeStamp: str):
    print("UpdateAccountTime. Time:", timeStamp)
```

----

接收订阅账户的最后更新时间。这个函数只会接收订阅账户的最后更新时间。在最初调用 updateAccountTime 后，只有在值发生变化时才会进行回调。这发生在仓位变化时，或者最多每3分钟一次。这个频率无法调整。

EWrapper.accountDownloadEnd
* @param accountName 账户信息的账户名称

```python
def accountDownloadEnd(self, accountName: str):
    print("Account download finished:", accountName)
```

## Account Value Keys

当请求 reqAccountUpdates 时，客户将接收到与各种账户键/值对应的值。下表记录了潜在的响应及其含义。

通过 IBApi.EWrapper.updateAccountValue 传递的账户值可以按以下方式分类：

商品：后缀为 “-C”
证券：后缀为 “-S”
总计：无后缀

| Key | 描述 |
| --- | --- |
| AccountCode | 账户ID号 |
| AccountOrGroup | “All”表示返回所有账户的账户概要数据，或设置为在TWS全局配置中已创建的特定顾问账户组名称 |
| AccountReady | 仅供内部使用 |
| AccountType | 标识IB账户结构 |
| AccruedCash | 股票、商品和证券的总累计现金价值 |
| AccruedCash-C | 反映到目前为止当月在商品部分累积的借记和贷记利息，每日更新 |
| AccruedCash-S | 反映到目前为止当月在证券部分累积的借记和贷记利息，每日更新 |
| AccruedDividend | 累积的股息的总投资组合价值 |
| AccruedDividend-C | 商品部分累积但未支付的股息 |
| AccruedDividend-S | 证券部分累积但未支付的股息 |
| AvailableFunds | 这个值告诉你有多少可用于交易 |
| AvailableFunds-C | 净清算价值 - 初始保证金 |
| AvailableFunds-S | 贷款价值的股本 - 初始保证金 |
| Billable | 国库券的总投资组合价值 |
| Billable-C | 商品部分的国库券价值 |
| Billable-S | 证券部分的国库券价值 |
| BuyingPower | 现金账户：最小值（贷款价值的股本，前一天的贷款价值的股本）-初始保证金，标准保证金账户：最小值（贷款价值的股本，前一天的贷款价值的股本）-初始保证金*4 |
| CashBalance | 交易时认可的现金 + 期货盈亏 |
| CorporateBondValue | 非政府债券的价值，如公司债券和市政债券 |
| Currency | 按货币分组的未平仓头寸 |
| Cushion | 净清算价值的超额流动性百分比 |
| DayTradesRemaining | 在检测到日内交易模式之前可以进行的开/平交易数量 |
| DayTradesRemainingT+1 | 明天之前可以进行的开/平交易数量，在检测到日内交易模式之前 |
| DayTradesRemainingT+2 | 从今天起两天内可以进行的开/平交易数量，在检测到日内交易模式之前 |
| DayTradesRemainingT+3 | 从今天起三天内可以进行的开/平交易数量，在检测到日内交易模式之前 |
| DayTradesRemainingT+4 | 从今天起四天内可以进行的开/平交易数量，在检测到日内交易模式之前 |
| EquityWithLoanValue | 确定客户是否拥有启动或维持证券头寸所需资产的基础 |
| EquityWithLoanValue-C | 现金账户：总现金价值 + 商品期权价值 - 期货维持保证金要求 + 最小值（0，期货盈亏）保证金账户：总现金价值 + 商品期权价值 - 期货维持保证金要求 |
| EquityWithLoanValue-S | 现金账户：结算现金保证金账户：总现金价值 + 股票价值 + 债券价值 +（非美国和加拿大证券期权价值） |
| ExcessLiquidity | 这个值显示你的保证金余地，在清算之前 |
| ExcessLiquidity-C | 贷款价值的股本 - 维持保证金 |
| ExcessLiquidity-S | 净清算价值 - 维持保证金 |
| ExchangeRate | 货币对您的基础货币的汇率 |
| FullAvailableFunds | 整个投资组合的可用资金，没有折扣或日内信贷 |
| FullAvailableFunds-C | 净清算价值 - 全额初始保证金 |
| FullAvailableFunds-S | 贷款价值的股本 - 全额初始保证金 |
| FullExcessLiquidity | 整个投资组合的超额流动性，没有折扣或日内信贷 |
| FullExcessLiquidity-C | 净清算价值 - 全额维持保证金 |
| FullExcessLiquidity-S | 贷款价值的股本 - 全额维持保证金 |
| FullInitMarginReq | 整个投资组合的初始保证金，没有折扣或日内信贷 |
| FullInitMarginReq-C | 商品部分投资组合的初始保证金，没有折扣或日内信贷 |
| FullInitMarginReq-S | 证券部分投资组合的初始保证金，没有折扣或日内信贷 |
| FullMaintMarginReq | 整个投资组合的维持保证金，没有折扣或日内信贷 |
| FullMaintMarginReq-C | 商品部分投资组合的维持保证金，没有折扣或日内信贷 |
| FullMaintMarginReq-S | 证券部分投资组合的维持保证金，没有折扣或日内信贷 |
| FundValue | 基金价值（货币市场基金 + 互惠基金） |
| FutureOptionValue | 期货期权的实时市值 |
| FuturesPNL | 自上次结算以来期货价值的实时变化 |
| FxCashBalance | 相关IB-UKL账户中的现金余额 |
| GrossPositionValue | 证券部分的总头寸价值 |
| GrossPositionValue-S | 长股价值 + 短股价值 + 长期权价值 + 短期权价值 |
| IndianStockHaircut | IB-IN账户的保证金规则 |
| InitMarginReq | 整个投资组合的初始保证金要求 |
| InitMarginReq-C | 商品部分的初始保证金，以基础货币计算 |
| InitMarginReq-S | 证券部分的初始保证金，以基础货币计算 |
| IssuerOptionValue | 发行期权的实时市值 |
| Leverage-S | 证券部分的GrossPositionValue / NetLiquidation |
| LookAheadNextChange | 向前看值生效的时间 |
| LookAheadAvailableFunds | 这个值反映了下一个保证金变化时您的可用资金 |
| LookAheadAvailableFunds-C | 净清算价值 - 向前看初始保证金 |
| LookAheadAvailableFunds-S | 贷款价值的股本 - 向前看初始保证金 |
| LookAheadExcessLiquidity | 这个值反映了下一个保证金变化时您的超额流动性 |
| LookAheadExcessLiquidity-C | 净清算价值 - 向前看维持保证金 |
| LookAheadExcessLiquidity-S | 贷款价值的股本 - 向前看维持保证金 |
| LookAheadInitMarginReq | 下一期间保证金变化时整个投资组合的初始保证金要求 |
| LookAheadInitMarginReq-C | 下一期间保证金变化时的初始保证金要求，以账户的基础货币计算 |
| LookAheadInitMarginReq-S | 下一期间保证金变化时的初始保证金要求，以账户的基础货币计算 |
| LookAheadMaintMarginReq | 下一期间的保证金变化时整个投资组合的维持保证金要求 |
| LookAheadMaintMarginReq         | 下一期间的保证金变化时整个投资组合的维持保证金要求            |
| LookAheadMaintMarginReq-C       | 下一期间的保证金变化时账户基础货币中的维持保证金要求           |
| LookAheadMaintMarginReq-S       | 下一期间的保证金变化时账户基础货币中的维持保证金要求           |
| MaintMarginReq                  | 整个投资组合的维持保证金要求                               |
| MaintMarginReq-C                | 商品部分的维持保证金                                      |
| MaintMarginReq-S                | 证券部分的维持保证金                                      |
| MoneyMarketFundValue            | 排除共同基金外的货币市场基金的市值                          |
| MutualFundValue                 | 排除货币市场基金外的共同基金的市值                          |
| NetDividend                     | 证券和商品部分的应付/应收红利总值                          |
| NetLiquidation                  | 确定账户资产价格的基础                                    |
| NetLiquidation-C                | 总现金价值 + 期货盈亏 + 商品期权价值                      |
| NetLiquidation-S                | 总现金价值 + 股票价值 + 证券期权价值 + 债券价值            |
| NetLiquidationByCurrency        | 各个货币的净清算价值                                      |
| OptionMarketValue               | 期权的实时市价                                            |
| PASharesValue                   | 整个投资组合的个人账户股份价值                            |
| PASharesValue-C                 | 商品部分的个人账户股份价值                                |
| PASharesValue-S                 | 证券部分的个人账户股份价值                                |
| PostExpirationExcess            | 到期后总预计“超额流动性”                                  |
| PostExpirationExcess-C          | 根据即将到期的合约在商品部分预计的到期后“超额流动性”        |
| PostExpirationExcess-S          | 根据即将到期的合约在证券部分预计的到期后“超额流动性”        |
| PostExpirationMargin            | 到期后总预计“保证金”                                      |
| PostExpirationMargin-C          | 根据即将到期的合约在商品部分预计的到期后保证金价值          |
| PostExpirationMargin-S          | 根据即将到期的合约在证券部分预计的到期后保证金价值          |
| PreviousDayEquityWithLoanValue  | 上一天16:00 ET时证券部分的可贷款股权价值                   |
| PreviousDayEquityWithLoanValue-S| 上一天16:00 ET时的可贷款股权价值                          |
| RealCurrency                    | 按货币分组的未平仓头寸                                    |
| RealizedPnL                     | 已平仓头寸的利润，即进场执行成本与出场执行成本之差          |
| RegTEquity                      | 通用账户的T规则股权                                      |
| RegTEquity-S                    | 证券部分的T规则股权                                      |
| RegTMargin                      | 通用账户的T规则保证金                                    |
| RegTMargin-S                    | 证券部分的T规则保证金                                    |
| SMA                             | T规则账户中证券市值增加时产生的信贷额度                    |
| SMA-S                           | 证券部分的T规则特别备忘账户余额                            |
| SegmentTitle                    | 账户部分的名称                                            |
| StockMarketValue                | 股票的实时市价                                            |
| TBondValue                      | 国库债券的价值                                           |
| TBillValue                      | 国库券的价值      | LookAheadMaintMarginReq         | 下一期间的保证金变化时整个投资组合的维持保证金要求            |
| LookAheadMaintMarginReq-C       | 下一期间的保证金变化时账户基础货币中的维持保证金要求           |
| LookAheadMaintMarginReq-S       | 下一期间的保证金变化时账户基础货币中的维持保证金要求           |
| MaintMarginReq                  | 整个投资组合的维持保证金要求                               |
| MaintMarginReq-C                | 商品部分的维持保证金                                      |
| MaintMarginReq-S                | 证券部分的维持保证金                                      |
| MoneyMarketFundValue            | 排除共同基金外的货币市场基金的市值                          |
| MutualFundValue                 | 排除货币市场基金外的共同基金的市值                          |
| NetDividend                     | 证券和商品部分的应付/应收红利总值                          |
| NetLiquidation                  | 确定账户资产价格的基础                                    |
| NetLiquidation-C                | 总现金价值 + 期货盈亏 + 商品期权价值                      |
| NetLiquidation-S                | 总现金价值 + 股票价值 + 证券期权价值 + 债券价值            |
| NetLiquidationByCurrency        | 各个货币的净清算价值                                      |
| OptionMarketValue               | 期权的实时市价                                            |
| PASharesValue                   | 整个投资组合的个人账户股份价值                            |
| PASharesValue-C                 | 商品部分的个人账户股份价值                                |
| PASharesValue-S                 | 证券部分的个人账户股份价值                                |
| PostExpirationExcess            | 到期后总预计“超额流动性”                                  |
| PostExpirationExcess-C          | 根据即将到期的合约在商品部分预计的到期后“超额流动性”        |
| PostExpirationExcess-S          | 根据即将到期的合约在证券部分预计的到期后“超额流动性”        |
| PostExpirationMargin            | 到期后总预计“保证金”                                      |
| PostExpirationMargin-C          | 根据即将到期的合约在商品部分预计的到期后保证金价值          |
| PostExpirationMargin-S          | 根据即将到期的合约在证券部分预计的到期后保证金价值          |
| PreviousDayEquityWithLoanValue  | 上一天16:00 ET时证券部分的可贷款股权价值                   |
| PreviousDayEquityWithLoanValue-S| 上一天16:00 ET时的可贷款股权价值                          |
| RealCurrency                    | 按货币分组的未平仓头寸                                    |
| RealizedPnL                     | 已平仓头寸的利润，即进场执行成本与出场执行成本之差          |
| RegTEquity                      | 通用账户的T规则股权                                      |
| RegTEquity-S                    | 证券部分的T规则股权                                      |
| RegTMargin                      | 通用账户的T规则保证金                                    |
| RegTMargin-S                    | 证券部分的T规则保证金                                    |
| SMA                             | T规则账户中证券市值增加时产生的信贷额度                    |
| SMA-S                           | 证券部分的T规则特别备忘账户余额                            |
| SegmentTitle                    | 账户部分的名称                                            |
| StockMarketValue                | 股票的实时市价                                            |
| TBondValue                      | 国库债券的价值                                           |
| TBillValue                      | 国库券的价值      
| LookAheadMaintMarginReq         | 下一期间的保证金变化时整个投资组合的维持保证金要求            |
| LookAheadMaintMarginReq-C       | 下一期间的保证金变化时账户基础货币中的维持保证金要求           |
| LookAheadMaintMarginReq-S       | 下一期间的保证金变化时账户基础货币中的维持保证金要求           |
| MaintMarginReq                  | 整个投资组合的维持保证金要求                               |
| MaintMarginReq-C                | 商品部分的维持保证金                                      |
| MaintMarginReq-S                | 证券部分的维持保证金                                      |
| MoneyMarketFundValue            | 排除共同基金外的货币市场基金的市值                          |
| MutualFundValue                 | 排除货币市场基金外的共同基金的市值                          |
| NetDividend                     | 证券和商品部分的应付/应收红利总值                          |
| NetLiquidation                  | 确定账户资产价格的基础                                    |
| NetLiquidation-C                | 总现金价值 + 期货盈亏 + 商品期权价值                      |
| NetLiquidation-S                | 总现金价值 + 股票价值 + 证券期权价值 + 债券价值            |
| NetLiquidationByCurrency        | 各个货币的净清算价值                                      |
| OptionMarketValue               | 期权的实时市价                                            |
| PASharesValue                   | 整个投资组合的个人账户股份价值                            |
| PASharesValue-C                 | 商品部分的个人账户股份价值                                |
| PASharesValue-S                 | 证券部分的个人账户股份价值                                |
| PostExpirationExcess            | 到期后总预计“超额流动性”                                  |
| PostExpirationExcess-C          | 根据即将到期的合约在商品部分预计的到期后“超额流动性”        |
| PostExpirationExcess-S          | 根据即将到期的合约在证券部分预计的到期后“超额流动性”        |
| PostExpirationMargin            | 到期后总预计“保证金”                                      |
| PostExpirationMargin-C          | 根据即将到期的合约在商品部分预计的到期后保证金价值          |
| PostExpirationMargin-S          | 根据即将到期的合约在证券部分预计的到期后保证金价值          |
| PreviousDayEquityWithLoanValue  | 上一天16:00 ET时证券部分的可贷款股权价值                   |
| PreviousDayEquityWithLoanValue-S| 上一天16:00 ET时的可贷款股权价值                          |
| RealCurrency                    | 按货币分组的未平仓头寸                                    |
| RealizedPnL                     | 已平仓头寸的利润，即进场执行成本与出场执行成本之差          |
| RegTEquity                      | 通用账户的T规则股权                                      |
| RegTEquity-S                    | 证券部分的T规则股权                                      |
| RegTMargin                      | 通用账户的T规则保证金                                    |
| RegTMargin-S                    | 证券部分的T规则保证金                                    |
| SMA                             | T规则账户中证券市值增加时产生的信贷额度                    |
| SMA-S                           | 证券部分的T规则特别备忘账户余额                            |
| SegmentTitle                    | 账户部分的名称                                            |
| StockMarketValue                | 股票的实时市价                                            |
| TBondValue                      | 国库债券的价值                                           |
| TBillValue                      | 国库券的价值                                             |
| TotalCashBalance                | 包括未来盈亏的总现金余额                                  |
| TotalCashValue                  | 股票、商品和证券的总现金价值                              |
| TotalCashValue-C                | 商品部分的现金余额                                        |
| TotalCashValue-S                | 证券部分的现金余额                                        |
| TradingType-S                   | 账户类型                                                  |
| UnrealizedPnL                   | 未平仓头寸的当前市值与平均成本之差，或价值 - 平均成本      |
| WarrantValue                    | 认股权证的价值                                           |
| WhatIfPMEnabled                 | 检查在投资组合保证金模型下的预计保证金要求                  |


## Cancel Account Updates

当不再需要订阅账户更新时，可以通过调用 IBApi.EClient.reqAccountUpdates 方法并将订阅标志指定为 False 来取消订阅。

注意：在调用 IBApi.EClient.reqAccountUpdates 后，在 IBApi.EWrapper.updateAccountValue 中返回的一个重要键是布尔值 accountReady。如果返回的 accountReady 值为 false，这意味着 IB 服务器在那一刻正在进行重置，即账户“未准备好”。当这种情况发生时，返回到当前更新中 IBApi::EWrapper::updateAccountValue 的后续键值可能是过时的或不正确的。

重要提示：一次只能订阅一个账户。尝试第二次订阅而没有先前取消一个活跃的订阅不会产生任何错误信息，尽管它会用新的账户覆盖已订阅的账户。对于财务顾问（FA）账户结构，有一种指定账户代码的替代方法，使信息返回“所有”子账户 - 这是通过在账户号码末尾追加字母“A”来完成的，即 

```python
reqAccountUpdates(true, "F123456A")。
```

# Account Update by Model

## Requesting Account Update by Model

`IBApi.EClient.reqAccountUpdatesMulti` 可以用于任何账户结构，以从一个或多个账户和/或模型中创建同时的账户价值订阅。与 IBApi.EClient.reqAccountUpdates 一样，返回的数据将与 TWS 账户窗口内显示的数据相匹配。

EClient.reqAccountUpdatesMulti
* @param reqId 请求ID，必须是唯一的
* @param accountCode 要订阅的账户代码
* @param modelCode 要订阅的模型代码
* @param ledgerAndNLV true = 返回账户余额和净清算价值，false = 返回账户余额和净清算价值

该方法用于请求账户和/或模型的账户更新。

需要注意的是，IBApi.EClient.reqAccountUpdatesMulti 不能与在 IBroker 账户中使用“所有”作为账户的情况一起使用，如果该账户拥有超过50个子账户。

对于财务顾问，账户参数中的组（group）可以被配置文件名称（profile name）所替代。

```python
self.reqAccountUpdatesMulti(reqId, self.account, "", True)
```

## Receiving Account Update by Model

通过 IBApi.EWrapper.accountUpdateMulti 和 IBApi.EWrapper.accountUpdateMultiEnd 方法，可以接收到账户和投资组合信息的更新。

EWrapper.accountUpdateMulti
* @param reqId 请求ID，必须是唯一的
* @param account 账户代码
* @param modelCode 模型代码
* @param key 账户信息的键值
* @param value 账户信息的值
* @param currency 账户信息的货币

这个方法提供账户的更新信息。当所有的更新信息都已发送完毕时，会调用 IBApi.EWrapper.accountUpdateMultiEnd 方法，其主要参数是 requestId，标识完成了哪个请求的更新。

```python
def accountUpdateMulti(self, reqId: int, account: str, modelCode: str, key: str, value: str, currency: str):
  print("AccountUpdateMulti. RequestId:", reqId, "Account:", account, "ModelCode:", modelCode, "Key:", key, "Value:", value, "Currency:", currency)
```

----

EWrapper.accountUpdateMultiEnd 
* @param reqId 请求ID，必须是唯一的

这个方法标识了 IBApi.EWrapper.accountUpdateMulti 方法的结束。当所有的更新信息都已发送完毕时，会调用 IBApi.EWrapper.accountUpdateMultiEnd 方法。

```python
def accountUpdateMultiEnd(self, reqId: int):
    print("AccountUpdateMultiEnd. RequestId:", reqId)
```

## Cancel Account Update by Model

EClient.reqAccountUpdatesMulti 
* @param reqId 请求ID，必须是唯一的
* @param accountCode 要订阅的账户代码
* @param modelCode 要订阅的模型代码
* @param ledgerAndNLV true = 返回账户余额和净清算价值，false = 返回账户余额和净清算价值

```python
self.reqAccountUpdatesMulti(reqId, self.account, "", False)
```

# Updates on Ewrapper Interface

from ibapi.wrapper import EWrapper
from ibapi.client import EClient
from ibapi.contract import Contract

# TwsApp for interacting with TWS API
class TwsApp(EWrapper, EClient):
    def __init__(self):
        EClient.__init__(self, self)
        self.nextOrderId = None
        
        # client flag message queue
        self.flag_queue = []

        # account summary
        self.account_summary = []

        # account updates
        self.account_updates = {}
        self.account_portfolio = {}
        self.account_update_time = None

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        print("Error: ", reqId, " ", errorCode, " ", errorString, " ", advancedOrderRejectJson)

    def cleanup(self):
        # clean flag queue
        self.flag_queue = []

        # clean account summary
        self.account_summary = []

        # clean account updates
        self.account_updates = {}

    ### 账号的概览信息 ###

    def accountSummary(self, reqId: int, account: str, tag: str, value: str,currency: str):
        self.account_summary.append({
            "method": "accountSummary",
            "reqId": reqId, 
            "account": account, 
            "tag": tag, 
            "value": value, 
            "currency": currency}) 
            
    def accountSummaryEnd(self, reqId: int):
        self.flag_queue.append({
            "method": "accountSummaryEnd",
            "reqId": reqId
        })

    ### 账号的更新信息: 单个账户 ###

    def updateAccountValue(self, key: str, val: str, currency: str, accountName: str):
        self.account_updates[key] = {
            "method": "updateAccountValue",
            "key": key,
            "val": val,
            "currency": currency,
            "accountName": accountName
        }

    def updatePortfolio(self, contract: Contract, position: float,
                            marketPrice: float, marketValue: float, 
                            averageCost: float, unrealizedPNL: float, 
                            realizedPNL: float, accountName: str):
        self.account_portfolio[contract.symbol] = {
            "method": "updatePortfolio",
            "contract": contract,
            "position": position,
            "marketPrice": marketPrice,
            "marketValue": marketValue,
            "averageCost": averageCost,
            "unrealizedPNL": unrealizedPNL,
            "realizedPNL": realizedPNL,
            "accountName": accountName
        }

    def updateAccountTime(self, timeStamp: str):
        self.account_update_time = timeStamp

    def accountDownloadEnd(self, accountName: str):
        self.flag_queue.append({
            "method": "accountDownloadEnd",
            "accountName": accountName
        })

    ### 账号的更新信息: 多个账户 ###

    def accountUpdateMulti(self, reqId: int, account: str, modelCode: str, key: str, value: str, currency: str):
        self.account_updates[key] = {
            "method": "accountUpdateMulti",
            "reqId": reqId,
            "account": account,
            "modelCode": modelCode,
            "key": key,
            "value": value,
            "currency": currency
        }

    def accountUpdateMultiEnd(self, reqId: int):
        self.flag_queue.append({
            "method": "accountUpdateMultiEnd",
            "reqId": reqId
        })