- [Wall Street Horizons](#wall-street-horizons)
  - [Meta Data](#meta-data)
    - [Request Meta Data](#request-meta-data)
    - [Receive Meta Data](#receive-meta-data)
    - [Cancel Meta Data](#cancel-meta-data)
  - [Event Data](#event-data)
    - [WshEventData Object](#wsheventdata-object)
    - [Request Event Data](#request-event-data)
    - [Receive Event Data](#receive-event-data)
    - [Cancel Event Data](#cancel-event-data)


# Wall Street Horizons

您可以通过TWS API获取来自华尔街地平线（Wall Street Horizon，简称WSH）事件日历的日历和事件数据。这是通过以下函数实现的：

1. `IBApi.EClient.reqWshMetaData`：用于请求WSH元数据。
2. `IBApi.EClient.reqWshEventData`：用于请求特定的WSH事件数据。

然而，要访问这些数据，首先必须在账户管理中激活华尔街地平线的公司事件数据研究订阅。

WSH向IBKR提供公司事件数据集，包括盈利日期、股息日期、期权到期日期、拆分、剥离以及各种与投资者相关的会议等信息。这些数据对于希望更深入了解公司活动和市场影响的投资者和交易者来说非常有价值。利用这些信息，他们可以更好地理解市场动态，制定更有效的交易策略和投资决策。

通过API访问这些数据，用户可以将华尔街地平线的深入研究和分析直接整合到他们的交易平台和策略中，从而提高对重要公司事件的响应速度和效率。

## Meta Data

确实，`IBApi.EClient.reqWshMetaData` 函数用于请求描述日历事件的元数据。这个函数是Interactive Brokers（IB）的TWS API的一部分，它允许用户获取关于华尔街地平线（Wall Street Horizon, WSH）提供的各种公司事件的详细元数据信息。

元数据通常包含关于日历事件的基本描述，例如事件类型、重要日期、以及可能的影响等。这对于想要了解特定公司事件详细信息的交易者和分析师来说非常重要，因为它可以帮助他们更好地预测这些事件对市场的可能影响，并据此制定相应的交易策略。

通过使用 `reqWshMetaData`，用户可以访问丰富的事件信息，从而更全面地理解市场动态和公司活动。这种信息对于进行基于事件的交易分析和决策尤为关键。

### Request Meta Data

`EClient.reqWshMetaData` 是TWS API中的一个方法，用于从华尔街地平线（Wall Street Horizon, WSH）日历请求元数据。此方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

当调用 `reqWshMetaData` 方法时，它会向WSH发送请求以获取关于即将发生的公司事件的元数据。这些元数据通常包括事件的类型、相关的日期、影响范围和其他关键信息。这对于需要准确和及时了解市场重大事件的交易者和投资者来说非常重要。

利用从WSH获取的元数据，用户可以更好地分析市场动态，预测这些事件可能对市场的影响，并据此调整他们的交易策略。例如，了解具体的盈利公告日期、股息日期或重大会议的时间可以帮助用户在这些事件发生前做好准备，从而在市场上占据有利位置。

```python
self.reqWshMetaData(1100)
```

### Receive Meta Data

`EWrapper.wshEventData` 是TWS API中的一个回调函数，用于返回来自华尔街地平线（Wall Street Horizon, WSH）日历的元数据。当您使用 `EClient.reqWshMetaData` 方法请求WSH日历的元数据时，此回调函数被触发。`EWrapper.wshEventData` 方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。
- `dataJson: String`：以JSON格式提供的元数据。

此回调函数提供了关于WSH日历中的公司事件的详细元数据。这些元数据以JSON格式返回，通常包含事件的类型、日期、影响程度和其他相关信息，这对于需要深入理解特定公司事件和市场影响的用户来说非常有价值。

通过解析 `dataJson` 中的信息，用户可以获得关于即将发生或已经发生的市场事件的深入洞察，从而帮助他们在交易决策和市场分析中利用这些关键信息。例如，用户可以基于盈利日期、重要会议或其他重大事件的数据来优化其交易策略或进行风险管理。这种及时和详细的市场事件信息对于在快速变化的市场环境中保持竞争优势尤为重要。

```python
def wshMetaData(self, reqId: int, dataJson: str):
    print("WshMetaData.", "ReqId:", reqId, "Data JSON:", dataJson)
```

### Cancel Meta Data

`EClient.cancelWshMetaData` 是TWS API中的一个方法，用于取消正在进行的华尔街地平线（Wall Street Horizon, WSH）元数据请求。此方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

当您调用 `cancelWshMetaData` 方法时，它会取消与特定 `requestId` 相关联的WSH元数据请求。这在您不再需要先前请求的数据或者需要中止正在进行的数据检索过程时非常有用。

例如，如果您在等待WSH元数据响应时决定更改策略或者认为该信息不再相关，可以使用此方法来取消请求。这有助于管理API客户端的数据流量和响应，确保您的系统只处理最相关和最及时的信息。

使用 `cancelWshMetaData` 是一个有效的方式来优化API交互，特别是在处理大量数据请求或需要快速调整信息获取策略的情况下。通过取消不再需要的请求，可以减少不必要的网络流量和系统负担，提高整体的效率和响应速度。

```python
self.cancelWshMetaData(1100)
```

## Event Data

确实，`EClient.reqWshEventData` 函数用于请求日历事件，而事件数据随后通过回调 `EWrapper.wshEventData` 接收。如果有待处理的事件数据请求，可以使用 `IBApi.EClient.cancelWshEventData` 函数来取消。

请注意以下几点：

1. **请求元数据**：在发送 `reqWshEventData` 消息之前，预期API客户端会先通过 `EClient.reqWshMetaData` 请求元数据。如果不这样做，可能会收到错误消息。

2. **避免并发请求**：TWS不支持多个并发请求。在发送下一个请求之前，前一个请求应该成功完成、失败或被客户端取消。如果尝试发送多个并发请求，TWS将拒绝这些请求，并显示“Duplicate WSH meta-data request”（重复的WSH元数据请求）或“Duplicate WSH event request”（重复的WSH事件请求）的错误消息。

这些注意事项对于确保在使用TWS API进行华尔街地平线（WSH）事件和元数据查询时的正确操作非常重要。正确地管理这些请求可以帮助避免不必要的错误和延迟，确保您能够有效地获取并利用这些重要的市场事件信息。通过遵循这些指导原则，API用户可以更有效地利用TWS和WSH提供的功能，从而在他们的交易策略中集成深入的市场事件分析。

### WshEventData Object

当通过API向华尔街地平线（Wall Street Horizons, WSH）事件日历发起请求时，用户必须创建一个 `wshEventData` 对象。这个对象包含多个字段，以及一个接受JSON格式字符串的过滤器（filter）字段。过滤器的值是从WSH元数据请求返回的。

在创建这个对象时，用户可以指定 `WshEventData.conId`、`WshEventData.startDate` 和 `WshEventData.endDate`，或者他们可以选择使用 `WshEventData.filter` 值。尝试同时使用这些值将导致错误。

`wshEventData` 对象可用字段的概述如下（请注意，可能会有新的字段可用）：

- `conId`：合约的唯一标识符。
- `startDate`：事件开始日期。
- `endDate`：事件结束日期。
- `filter`：用于筛选事件的JSON格式字符串。这个字符串可以基于从WSH元数据请求中获得的值来构建，允许用户更精确地过滤他们想要的事件类型。

正确构建和使用 `wshEventData` 对象对于成功从WSH获取特定事件信息至关重要。用户应确保他们仅使用 `conId`、日期字段或过滤器字段中的一种方法来定义他们的请求，以避免发生错误。这种灵活性允许用户根据他们的具体需求和所拥有的信息来定制他们的事件查询，从而能够有效地集成和利用WSH提供的市场事件数据。

`WshEventData` 是用于请求华尔街地平线（Wall Street Horizons, WSH）事件数据的对象，具有多个字段，可用于定制事件请求。它的字段包括：

- `conId: String`：指定事件请求的合约标识符。

- `startDate: String`：指定事件请求的开始日期。格式为“YYYYMMDD”。

- `endDate: String`：指定事件请求的结束日期。格式为“YYYYMMDD”。

- `fillCompetitors: bool`：自动填充现有头寸的竞争对手值。

- `fillPortfolio: bool`：自动填充投资组合值。

- `fillWatchlist: bool`：自动填充观察列表值。

- `totalLimit: int`：最大值为100。

- `filter: String`：包含所有过滤器值的JSON格式字符串。一些可用的值包括：
  - `watchlist: Array of string`：接受单个conid的数组。
  - `country: String`：指定一个国家代码，或“全部”。

`wshMetaData` 响应的头部部分将包含格式为“wshe_”的值。这些值可以在eventData请求中包括或以true/false值忽略。

由于Interactive Brokers经常添加额外的字段，建议总是查看TWS API安装中最新版本的代码以获取最新信息。

使用 `WshEventData` 对象，用户可以根据特定条件查询WSH提供的事件数据，如按特定合约、日期范围、国家或其他过滤条件。这使得用户可以获取特定的市场事件信息，从而帮助他们更好地了解市场动态并做出更明智的交易决策。通过定期查看API的最新版本，用户可以确保他们利用的是最新的功能和字段，从而充分利用WSH事件数据。

> ibapi\client.py

### Request Event Data

`EClient.reqWshEventData` 是TWS API中的一个方法，用于从华尔街地平线（Wall Street Horizon, WSH）日历请求事件数据。此方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。
- `wshEventData: WshEventData`：用于跟踪事件数据请求所有参数的唯一对象。有关更多细节，请参见 `WshEventData` 对象。

当调用 `reqWshEventData` 方法时，它会根据提供的 `WshEventData` 对象中的参数向WSH发送事件数据请求。这可以包括特定的合约标识符、事件的日期范围、过滤器选项等。这个方法使用户能够根据特定的条件和偏好获取WSH提供的市场事件数据。

利用 `reqWshEventData` 方法，用户可以访问和分析WSH日历中的具体事件，例如盈利公告、股息支付日期、投资者会议等。这对于需要详细了解市场动态和影响因素的交易者和分析师来说非常有用。通过定制化的事件查询，他们可以更有效地整合市场事件数据到他们的交易策略和市场分析中，从而做出更加明智的投资决策。

```python
self.reqWshEventData(1101, eventDataObj)
```

### Receive Event Data

`EWrapper.wshEventData` 是TWS API中的一个回调函数，用于返回来自华尔街地平线（Wall Street Horizon, WSH）的日历事件。当您使用 `EClient.reqWshEventData` 方法请求WSH日历的事件数据时，此回调函数被触发。`EWrapper.wshEventData` 方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。
- `dataJson: String`：以JSON格式提供的事件数据。

此回调函数提供了WSH日历中的具体事件信息，以JSON格式返回，通常包含关于特定市场事件的详细信息，如事件的类型、相关日期、重要性以及其他相关数据。这对于需要深入理解特定市场事件的用户来说非常有价值。

通过解析 `dataJson` 中的信息，用户可以获得对即将发生或已发生的市场事件的深入洞察，从而帮助他们在交易决策和市场分析中利用这些关键信息。例如，用户可以基于公司盈利公告、重大会议或其他重要事件的数据来优化其交易策略或进行风险管理。这种及时和详细的市场事件信息对于在快速变化的市场环境中保持竞争优势尤为重要。

```python
def wshEventData(self, reqId: int, dataJson: str):
    print("WshEventData.", "ReqId:", reqId, "Data JSON:", dataJson)
```

### Cancel Event Data

`EClient.cancelWshEventData` 是TWS API中的一个方法，用于取消正在进行的华尔街地平线（Wall Street Horizon, WSH）事件数据请求。此方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

当您调用 `cancelWshEventData` 方法时，它会取消与特定 `requestId` 相关联的WSH事件数据请求。这在您不再需要先前请求的数据或者需要中止正在进行的数据检索过程时非常有用。

例如，如果您在等待WSH事件数据响应时决定更改策略或者认为该信息不再相关，可以使用此方法来取消请求。这有助于管理API客户端的数据流量和响应，确保您的系统只处理最相关和最及时的信息。

使用 `cancelWshEventData` 是一个有效的方式来优化API交互，特别是在处理大量数据请求或需要快速调整信息获取策略的情况下。通过取消不再需要的请求，可以减少不必要的网络流量和系统负担，提高整体的效率和响应速度。

```python
self.cancelWshEventData(1101, eventDataObj)
```