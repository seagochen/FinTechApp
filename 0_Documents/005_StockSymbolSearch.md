- [Stock Symbol Search](#stock-symbol-search)
  - [Request Stock Contract Search](#request-stock-contract-search)
  - [Receive Searched Stock Contract](#receive-searched-stock-contract)


# Stock Symbol Search

`IBApi::EClient::reqMatchingSymbols` 函数可用于搜索股票合约。输入可以是股票代码的前几个字母，或对于更长的字符串，可以是证券名称中匹配的字符序列。例如，要搜索股票代码 ‘IBKR’，可以使用输入 ‘I’ 或 ‘IB’，也可以使用单词 ‘Interactive’。最多返回 16 个匹配结果。

连续调用 `reqMatchingSymbols` 之间必须至少间隔 1 秒。

匹配的股票合约将返回到 `IBApi::EWrapper::symbolSamples`，其中包含有关存在的衍生合约类型（如权证、期权、荷兰式权证、期货）的信息。这个功能对于快速查找和确定股票及其相关衍生品的合约非常有用，尤其是在进行市场分析和交易准备时。

## Request Stock Contract Search

`EClient.reqMatchingSymbols` 方法用于请求匹配的股票代码。该方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。
- `pattern: String`：股票代码的开头或（对于较长的字符串）公司名称。

此方法允许用户根据给定的模式（如股票代码的一部分或公司名称的一部分）搜索匹配的股票。这对于快速找到特定股票或探索与某个公司名称相关的不同股票非常有用，尤其是在进行市场研究或投资决策时。

```python
self.reqMatchingSymbols(reqId, "IBM")
```

## Receive Searched Stock Contract

`EWrapper.symbolSamples` 方法用于返回一系列示例合约描述。该方法的参数包括：

- `reqID: int`：用于跟踪数据的请求标识符。
- `contractDescription: ContractDescription[]`：提供与请求描述匹配的一系列合约对象。

此方法在响应 `EClient.reqMatchingSymbols` 请求时被触发，返回与所请求的描述匹配的合约对象数组。这为用户提供了关于找到的股票及其相关信息的综合概览，帮助他们更好地了解和分析潜在的投资选择。

```python
def symbolSamples(self, reqId: int, contractDescriptions: ListOfContractDescription):
    print("Symbol Samples. Request Id: ", reqId)
    for contractDescription in contractDescriptions:
        derivSecTypes = ""
        for derivSecType in contractDescription.derivativeSecTypes:
        derivSecTypes += " "
        derivSecTypes += derivSecType
        print("Contract: conId:%s, symbol:%s, secType:%s primExchange:%s, "
            "currency:%s, derivativeSecTypes:%s, description:%s, issuerId:%s" % (
            contractDescription.contract.conId,
            contractDescription.contract.symbol,
            contractDescription.contract.secType,
            contractDescription.contract.primaryExchange,
            contractDescription.contract.currency, derivSecTypes,
            contractDescription.contract.description,
            contractDescription.contract.issuerId))
```
