- [Option Chains](#option-chains)
  - [Request Option Chains](#request-option-chains)
  - [Receive Option Chains](#receive-option-chains)


# Option Chains

使用 `reqContractDetails` 函数可以返回给定证券的期权链。如果一个期权合约定义不完整（例如未定义执行价），并用作 `IBApi::EClient::reqContractDetails` 的参数，则会返回所有匹配的期权合约列表。

这种技术的一个限制是，返回期权链的速度将受到限制，并且合约定义越模糊，返回时间越长。因此，引入了 `IBApi::EClient::reqSecDefOptParams` 函数，该函数没有这种限制。

不建议使用 `reqContractDetails` 来接收基础资产上的完整期权链，例如所有组合的执行价/权利/到期日。
对于通过 `reqContractDetails` 返回的非常大的期权链，取消 TWS 全局配置中的设置（API -> 设置 -> “向 API 公开完整交易时间表”）将减少每个期权返回的数据量，有助于更快地返回合约列表。
`IBApi::EClient::reqSecDefOptParams` 返回一系列到期日和一系列执行价。在某些情况下，可能存在执行价和到期日的组合不会构成有效的期权合约。

## Request Option Chains

`EClient.reqSecDefOptParams` 方法用于请求查看合约期权链的安全定义选项参数。该方法的参数包括：

- `reqId: int`：选择用于请求的 ID。
- `underlyingSymbol: String`：基础资产的合约标志。
- `futFopExchange: String`：返回期权正在交易的交易所。可以设置为空字符串 “” 来表示所有交易所。
- `underlyingSecType: String`：基础安全性的类型，例如 STK（股票）。
- `underlyingConId: int`：基础安全性的合约 ID。

此方法用于请求安全定义选项参数，以便查看合约的期权链。它为用户提供了获取特定基础资产的期权链所需的关键信息，包括到期日和执行价等，这对于期权交易者进行市场分析和决策非常重要。

```python
self.reqSecDefOptParams(0, "IBM", "", "STK", 8314)
```

## Receive Option Chains

`EWrapper.securityDefinitionOptionParameter` 方法用于返回在 `reqSecDefOptParams` 中指定的交易所上某个基础资产的期权链。该方法的参数包括：

- `reqId: int`：启动回调的请求 ID。
- `underlyingConId: int`：基础证券的 conID。
- `tradingClass: String`：期权的交易类别。
- `multiplier: String`：期权的乘数。
- `exchange: String`：托管衍生品的交易所。
- `expirations: HashSet`：该基础资产在此交易所上期权的到期日列表。
- `strikes: HashSet`：该基础资产在此交易所上期权的可能执行价列表。

如果在 `reqSecDefOptParams` 中指定了多个交易所，将会有多次对 `securityDefinitionOptionParameter` 的回调。这为用户提供了获取特定交易所上特定基础资产期权链的详细信息，包括不同到期日和执行价的选项，这对于进行期权交易和策略规划非常重要。

```python
def securityDefinitionOptionParameter(self, reqId: int, exchange: str, underlyingConId: int, tradingClass: str, multiplier: str, expirations: SetOfString, strikes: SetOfFloat):
    print("SecurityDefinitionOptionParameter.", "ReqId:", reqId, "Exchange:", exchange, "Underlying conId:", underlyingConId, "TradingClass:", tradingClass, "Multiplier:", multiplier, "Expirations:", expirations, "Strikes:", strikes)
```