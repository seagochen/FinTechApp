- [Requesting Account Summary](#requesting-account-summary)
  - [Account Summary Tags](#account-summary-tags)
  - [Receiving Account Summary](#receiving-account-summary)
  - [Canceling Account Summary](#canceling-account-summary)
- [Implementing the EWrapper Interface](#implementing-the-ewrapper-interface)


# Requesting Account Summary

请求特定账户的概要。此方法将订阅如 TWS 的账户概要选项卡中所呈现的账户概要。客户可以通过使用特定的标签值来指定接收的数据。有关可用选项，请参阅账户概要标签部分。

或者，许多语言提供了导入 AccountSummaryTags 的方法，以检索所有标签值。

EClient.reqAccountSummary
* reqId: int。唯一的请求标识符。
* group: String。设置为“All”以返回所有账户的账户概要数据，或设置为在 TWS 全局配置中已创建的特定顾问账户组名称。
* tags: String。所需标签的逗号分隔列表

重要提示：同时只允许有两个活跃的概要订阅！

```python
EClient.reqAccountSummary(9001, "All", AccountSummaryTags.AllTags)
```

## Account Summary Tags

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

## Receiving Account Summary

EWrapper.accountSummary 
* reqId: int. 请求的唯一标识符。
* account: String. 账户编号。
* tag: String. 正在接收的账户属性。
* value: String. 账户属性的值。
* currency: String. 表达值的货币。


```python
def accountSummary(feedback: list, reqId: int, account: str, tag: str, value: str,currency: str):
    feedback.append({
        "method": "accountSummary",
        "reqId": reqId, 
        "account": account, 
        "tag": tag, 
        "value": value, 
        "currency": currency})
```

----

EWrapper.accountSummaryEnd
* reqId: int. 请求的唯一标识符。

当请求的数据已被完全接收时，将调用 accountSummaryEnd()。此方法将始终在 accountSummary() 方法之后调用。

```python
def accountSummaryEnd(feedback: list, reqId: int):
    feedback.append({
        "method": "accountSummaryEnd",
        "reqId": reqId})
```

## Canceling Account Summary

一旦不再需要订阅账户概要，可以通过 IBApi::EClient::cancelAccountSummary 方法来取消订阅

EClient.cancelAccountSummary 
* reqId: int. 请求的唯一标识符，该标识符应该与请求概要时使用的标识符相同。

```python
EClient.cancelAccountSummary(9001)
```

# Implementing the EWrapper Interface

```python
from ibapi.wrapper import EWrapper
from ibapi.client import EClient

# TwsApp for interacting with TWS API
class TwsApp(EWrapper, EClient):
    def __init__(self):
        EClient.__init__(self, self)
        self.nextOrderId = None
        self.feedback = []

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        print("Error: ", reqId, " ", errorCode, " ", errorString, " ", advancedOrderRejectJson)

    def cleanup(self):
        self.feedback = []

    def accountSummary(self, reqId: int, account: str, tag: str, value: str,currency: str):
        self.feedback.append({
            "method": "accountSummary",
            "reqId": reqId, 
            "account": account, 
            "tag": tag, 
            "value": value, 
            "currency": currency}) 
            
    def accountSummaryEnd(self, reqId: int):
        self.feedback.append({
            "method": "accountSummaryEnd",
            "reqId": reqId})
```