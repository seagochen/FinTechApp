- [Market Scanner](#market-scanner)
  - [Market Scanner Parameters](#market-scanner-parameters)
    - [Request Market Scanner Parameters](#request-market-scanner-parameters)
    - [Receive Market Scanner Parameters](#receive-market-scanner-parameters)
  - [Market Scanner Subscription](#market-scanner-subscription)
    - [Request Market Scanner Subscription](#request-market-scanner-subscription)
    - [Receive Market Scanner Subscription](#receive-market-scanner-subscription)
    - [Cancel Market Scanner Subscription](#cancel-market-scanner-subscription)


# Market Scanner

TWS 高级市场扫描器中的某些扫描可以通过 TWS API 的 `EClient.reqScannerSubscription` 方法访问。

结果通过 `EWrapper.scannerData` 传递，`EWrapper.scannerDataEnd` 标记将指示所有结果已经传递完毕。返回到 `scannerData` 的结果仅由合约列表组成。扫描器不返回任何市场数据字段（如买价、卖价、最后成交价、成交量等），因此，如果需要这些数据，必须单独通过 `reqMktData` 函数请求。由于扫描器结果不包含任何市场数据字段，使用 API 扫描器不需要市场数据订阅。然而，要使用过滤器，通常需要市场数据订阅。

由于 `EClient.reqScannerSubscription` 请求保持订阅打开状态，您将继续接收定期更新，直到通过 `EClient.cancelScannerSubscription` 取消请求。

扫描结果限制为每个扫描代码最多 50 个结果，同时只能有 10 个 API 扫描处于活跃状态。

已向 API 添加了 `scannerSubscriptionFilterOptions`，以允许使用通用过滤器。此字段作为 `TagValues` 列表输入，其中包含标签及其值，例如，`TagValue("usdMarketCapAbove", "10000")` 表示市值超过 10000 美元。可用过滤器可以通过 `EClient.reqScannerParameters` 函数找到。

通过 `EWrapper.scannerParameters` 将返回包含所有可用 XML 格式化参数的字符串。

重要提示：请记住，TWS API 只是 TWS 的接口。如果您在定义扫描器时遇到问题，请确保您可以使用 TWS 的高级市场扫描器创建类似的扫描器。

## Market Scanner Parameters

通过 `EWrapper.scannerParameters` 方法，将返回一个包含所有可用 XML 格式化参数的字符串。这些参数提供了使用 TWS API 进行市场扫描时可用的各种扫描选项和过滤器的详细信息。

这个功能特别有用，因为它允许开发者和交易者了解和利用 TWS API 提供的市场扫描器的全部能力。通过查看 XML 格式化的参数，用户可以精确地了解可用于创建市场扫描的不同选项，包括但不限于市值、价格范围、行业类别、地域等。这些参数对于定制化市场扫描，以寻找特定条件下的交易机会或进行市场分析，是非常重要的。

例如，如果您希望创建一个扫描器来查找特定市值范围内的股票，或者只关注特定行业的股票，`scannerParameters` 方法返回的信息将提供创建这种扫描所需的所有必要参数和值。这样，您就可以利用 TWS API 的功能，根据自己的具体需求和偏好进行高度定制化的市场扫描。

### Request Market Scanner Parameters 

`EClient.reqScannerParameters()` 方法用于请求一个有效的 TWS 扫描器参数的 XML 列表。这个方法没有参数，它请求 TWS 提供当前所有可用的市场扫描器参数，这些参数以 XML 格式返回。

当调用此方法时，TWS API 将返回一个包含各种可用扫描条件和选项的 XML 字符串。这个列表包括了所有可用于配置市场扫描的参数，例如股票市值、价格范围、交易量、行业分类等。这些参数对于定制市场扫描器以寻找特定类型的交易机会或进行市场趋势分析至关重要。

通过使用 `reqScannerParameters()` 方法，用户可以获得深入了解 TWS 扫描器的各种功能，并根据这些信息创建更精确、更适合自己需求的市场扫描。这对于希望利用自动化工具来识别市场机会的交易者和分析师来说尤其有价值。

```python
self.reqScannerParameters()
```

### Receive Market Scanner Parameters

`EWrapper.scannerParameters` 方法用于提供 TWS 市场扫描器可用的 XML 格式化参数（并非所有参数都在 API 中可用）。当您使用 `EClient.reqScannerParameters()` 方法请求市场扫描器参数时，TWS API 通过此方法返回参数数据。该方法的参数包括：

- `xml: String`：包含可用参数的 XML 格式化字符串。

这个字符串详细描述了 TWS 扫描器提供的各种参数，包括但不限于股票市值、价格区间、成交量、行业分类等。用户可以利用这些参数来定制市场扫描器，以搜索符合特定标准的金融工具。

例如，如果您想创建一个扫描器来识别特定市值范围内的股票或关注特定行业的股票，`scannerParameters` 方法返回的信息将为您提供创建这种扫描所需的所有必要参数和值。

这使得用户能够根据自己的具体需求和偏好利用 TWS API 的功能，进行高度定制化的市场扫描，从而识别交易机会或进行市场趋势分析。

```python
def scannerParameters(self, xml: str):
    open('log/scanner.xml', 'w').write(xml)
    print("ScannerParameters received.")
```

## Market Scanner Subscription

`ScannerSubscription` 对象使用的所有值都来自 `EClient.scannerParams` 响应。XML 树将提供一个树状结构，其中包含每个 `ScannerSubscription` 字段对应的代码，如下文档所述：

- **Instrument（乐器）**：`<ScanParameterResponse> <InstrumentList> <Instrument> <type>`
- **Location Code（地点代码）**：`<ScanParameterResponse> <LocationTree> <Location> <LocationTree> <Location> <locationCode>`
- **Scan Code（扫描代码）**：`<ScanParameterResponse> <ScanTypeList> <ScanType> <scanCode>`
- **订阅选项**（Subscription Options）应该是一个空的 `TagValues` 数组。
- **过滤选项**（Filter Options）：`<ScanParameterResponse> <FilterList> <RangeFilter> <AbstractField> <code>`

`ScannerSubscription()` 方法的结构如下：

- **Instrument: String**：要使用的乐器类型。
- **Location Code: String**：扫描器搜索的国家或地区代码。
- **Scan Code: String**：扫描器用于排序的值。
- **Subscription Options: Array of TagValues**：仅供内部使用。
- **Filter Options: Array of TagValues**：包含过滤扫描器订阅的 `TagValue` 对象数组。

通过使用这些参数，用户可以创建一个定制的市场扫描器，以搜索特定类型的金融工具、特定地区或国家的工具，或者基于特定的扫描类型。过滤选项允许进一步细化搜索条件，例如设定价格范围、市值或其他相关标准。这使得用户能够根据特定的交易策略或市场研究需求定制扫描器，以便识别合适的交易机会或进行市场趋势分析。

### Request Market Scanner Subscription

`EClient.reqScannerSubscription` 方法用于根据提供的参数启动市场扫描结果的订阅。这个方法允许用户根据特定的标准启动对市场的实时扫描，参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。
- `subscription: ScannerSubscription`：包含用于构建和排序列表的详细值的对象。
- `scannerSubscriptionOptions: List`：仅供内部使用。
- `scannerSubscriptionFilterOptions: List`：用于过滤扫描器订阅结果的值列表。过度过滤可能导致扫描器响应为空。

此方法是启动市场扫描的关键步骤，允许用户指定他们希望扫描的市场类型、地区、扫描代码等。通过 `ScannerSubscription` 对象，用户可以精确地定义扫描的参数，如要扫描的金融工具类型、扫描的地理位置、排序依据等。

`scannerSubscriptionFilterOptions` 提供了进一步定制扫描结果的能力。例如，用户可以设置特定的价格范围、市值大小或其他相关标准来过滤扫描结果，确保扫描结果符合特定的交易策略或分析需求。

使用此方法启动的市场扫描可以帮助交易者和分析师快速识别符合特定条件的交易机会，从而在竞争激烈的市场环境中保持优势。

```python
self.reqScannerSubscription(7002, scannerSubscription, [], filterTagvalues)
```

### Receive Market Scanner Subscription

`EWrapper.scannerData` 方法用于提供市场扫描请求的结果数据。当您使用 `EClient.reqScannerSubscription` 方法发起市场扫描请求时，此方法被用来接收扫描结果。该方法的参数包括：

- `reqid: int`：用于跟踪数据的请求标识符。
- `rank: int`：合约在扫描排序中的排名位置。
- `contractDetails: ContractDetails`：结果对象的合约详情对象。
- `distance: String`：仅供内部使用。
- `benchmark: String`：仅供内部使用。
- `projection: String`：仅供内部使用。
- `legStr: String`：当扫描器返回 EFP（交易所交易基金）时，描述组合腿的字符串。

此方法在市场扫描器找到符合指定条件的合约时提供数据。它返回每个符合条件的合约的详细信息，包括合约的排名、合约详情等。这些信息对于交易者和分析师来说至关重要，因为它们提供了有关潜在交易机会的关键数据。

例如，如果扫描器设置为查找特定市值范围内的股票，`scannerData` 方法将返回符合这些条件的所有股票的合约详情，包括它们在扫描器中的排名。这允许用户快速识别和分析这些股票，从而做出更有信息的投资决策。

```python
def scannerData(self, reqId: int, rank: int, contractDetails: ContractDetails, distance: str, benchmark: str, projection: str, legsStr: str):
    print("ScannerData. ReqId:", reqId, ScanData(contractDetails.contract, rank, distance, benchmark, projection, legsStr))
```

### Cancel Market Scanner Subscription

`EClient.cancelScannerSubscription` 方法用于取消特定的市场扫描器订阅。当不再需要接收来自特定扫描器订阅的数据时，可以使用此方法。该方法的参数包括：

- `tickerId: int`：用于跟踪数据的请求标识符。

使用 `cancelScannerSubscription` 方法，您可以停止接收之前通过 `reqScannerSubscription` 请求的市场扫描结果。这对于管理和优化数据流非常重要，尤其是在动态的交易环境中，其中扫描器的需求可能随时间变化。

例如，如果您已经识别了足够的交易机会或者想要根据新的市场条件改变扫描策略，可以通过调用此方法来取消当前的市场扫描订阅。这样做可以确保您的系统不会继续接收不再相关或不需要的数据，从而帮助您专注于最新的市场动态和交易机会。

```python
self.cancelScannerSubscription(7003)
```

