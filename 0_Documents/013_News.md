- [News](#news)
  - [News Providers](#news-providers)
    - [Request News Providers](#request-news-providers)
    - [Receive News Providers](#receive-news-providers)
  - [Live News Headlines](#live-news-headlines)
    - [Request Contract Specific News](#request-contract-specific-news)
    - [Request BroadTape News](#request-broadtape-news)
    - [Receive Live News Headlines](#receive-live-news-headlines)
  - [Historical News Headlines](#historical-news-headlines)
    - [Requesting Historical News](#requesting-historical-news)
    - [Receive Historical News](#receive-historical-news)
  - [News Articles](#news-articles)
    - [Request News Articles](#request-news-articles)
    - [Receive News Articles](#receive-news-articles)


# News

API新闻功能需要特定于API的新闻订阅；TWS中的大多数新闻服务在API中并不可用。默认在账户中启用并可通过API访问的有三个API新闻服务，它们是：

1. Briefing.com一般市场专栏（BRFG）
2. Briefing.com分析师行动（BRFUPDN）
3. 道琼斯新闻简报（DJNL）

此外，还有四个附加的新闻服务在所有TWS版本中可用，但需要先在账户管理中进行特定于API的订阅。它们的数据费用与仅在TWS中同一新闻的订阅不同。与所有订阅一样，它们只适用于进行订阅的特定TWS用户名下：

1. Briefing Trader（BRF）
2. Benzinga Pro（BZ）
3. Fly on the Wall（FLY）

处理新闻的API函数能够查询可用的新闻提供商，实时订阅新闻以接收发布的头条新闻，请求特定新闻文章，并返回系统中缓存的历史新闻故事列表。

这些API新闻服务为用户提供了一个强大的工具，可以实时获取和分析市场新闻和信息，从而做出更加明智的交易决策。用户可以根据自己的需要和兴趣选择和订阅特定的新闻服务，利用API功能来增强他们对市场动态的了解和反应。

## News Providers

在账户中添加或移除API新闻订阅是通过账户管理（Account Management）来完成的。使用API时，可以通过 `IBApi::EClient::reqNewsProviders` 函数检索当前订阅的新闻来源。可订阅的新闻来源列表将返回到 `IBApi::EWrapper::newsProviders` 函数。

具体操作如下：

1. **添加或移除新闻订阅**：首先，登录到账户管理界面，在那里可以添加或取消新闻服务的订阅。这涉及到选择所需的新闻提供商，并根据个人的交易需求和兴趣进行订阅设置。

2. **检索当前订阅的新闻来源**：在API中，通过调用 `IBApi::EClient::reqNewsProviders` 函数，可以获取当前已订阅的新闻来源列表。这个功能对于确定哪些新闻服务已经激活并且可以通过API访问非常有用。

3. **接收订阅的新闻来源列表**：`IBApi::EWrapper::newsProviders` 函数用于接收由 `reqNewsProviders` 请求返回的新闻提供商列表。这个列表包含了所有已订阅并且对API客户端可用的新闻源。

这些功能对于希望通过API及时获取市场新闻和分析的交易者来说非常重要。正确使用这些工具可以帮助用户更好地利用市场信息，从而做出更加明智的交易决策。通过定期更新和管理新闻订阅，用户可以确保他们总是有最新和最相关的市场新闻和信息。

### Request News Providers

`EClient.reqNewsProviders()` 是一个API函数，用于请求用户已订阅的新闻提供商。当调用此函数时，它会查询并返回用户通过账户管理订阅的所有新闻服务提供商的列表。

这个功能对于API用户来说非常重要，尤其是对于那些依赖实时新闻来做出交易决策的交易者。通过使用 `reqNewsProviders`，用户可以轻松地获取他们当前有权访问的新闻源信息，进而可以利用这些新闻源来获取市场动态、分析报告或其他相关信息。

简单来说，此函数是用于确保交易者或投资者能够访问他们订阅的所有新闻提供商，并据此获取最新的市场新闻和信息，有助于他们更好地理解市场趋势和制定交易策略。

```python
self.reqNewsProviders()
```
### Receive News Providers

`EWrapper.newsProviders` 是一个API回调函数，用于返回当前用户订阅的API新闻提供商数组。此函数接收的参数是：

- `newsProviders: NewsProviders[]`：一个独特的数组，包含所有可用的新闻来源。

当调用 `EClient.reqNewsProviders()` 函数请求用户订阅的新闻提供商信息时，`EWrapper.newsProviders` 回调会被触发，并返回一个 `NewsProviders` 类型的数组。这个数组列出了用户通过其账户订阅的所有API新闻提供商。

这个功能对于需要实时访问市场新闻的交易者和投资者来说是非常有价值的。它提供了一种快速有效的方式来确认哪些新闻服务是当前激活并可通过API接入的，从而使用户能够根据这些信息源来做出更加明智的交易决策。通过 `newsProviders` 回调，用户可以确保他们的交易策略和决策是基于最新和最相关的市场信息。

```python
def newsProviders(self, newsProviders: ListOfNewsProviders):
    print("NewsProviders: ", newsProviders)
```

## Live News Headlines

重要提示：为了通过TWS API获取新闻源，您需要通过您的账户管理获取相关的特定于API的订阅。

通过API提供的新闻文章可能与直接通过交易工作站（Trader Workstation, TWS）可用的内容不同。数据在平台外的分发取决于新闻源提供商的决定，而不是由Interactive Brokers控制。

当调用 `IBApi.EClient.reqMktData` 为特定的 `IBApi.Contract` 时，您将遵循与其他基本合约相同的格式约定。新闻源通过 `genericTickList` 参数来识别。

注意事项：

1. **错误消息“无效的刻度类型”**：如果用户名未添加相应的API新闻订阅，将返回“invalid tick type”错误消息。

2. **Briefing Trader实时头条**：通过API提供的Briefing Trader实时头条只在Briefing.com的案例基础上提供。Briefing Trader的订阅者可以通过API访问订阅的实时头条新闻。如需更多信息或提交API权限申请，请直接联系Briefing.com，电邮至dbeasley@briefing.com。

这些提示对于想要通过API获取最新市场新闻的用户来说非常重要。它们提供了关于如何正确订阅和访问新闻源的关键信息，以及在使用这些功能时可能遇到的限制和要求。通过理解这些细节，用户可以有效地整合和利用API提供的新闻服务，以支持他们的交易决策和市场分析。


### Request Contract Specific News

`EClient.reqMktData` 方法通常用于请求市场数据，但也可以用来检索新闻。此方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。

- `contract: Contract`：用于指定金融工具的合约对象。

- `genericTickList: String`：可用通用刻度的逗号分隔ID。

- `snapshot: bool`：获取新闻数据时，始终设置为false。

- `regulatorySnapshot: bool`：获取新闻数据时，始终设置为false。

- `mktDataOptions: List<TagValue>`：仅限内部使用。

在检索新闻时，可以指定“mdoff”来禁用标准市场数据。对于新闻来源，需要指定通用刻度292，后跟冒号和新闻提供商的代码。这样，可以专门从特定的新闻提供商获取新闻数据，而不是标准的市场数据。

这种方法对于那些希望通过API获取特定新闻提供商的实时新闻的用户来说非常有用。通过正确设置 `reqMktData` 方法的参数，用户可以确保他们接收到的是与他们选择的新闻提供商相关的新闻，从而使他们能够及时了解和反应市场新闻和事件。

```python
self.reqMktData(reqId, contract, "mdoff,292:BRFG", False, False, [])
```

### Request BroadTape News

对于BroadTape新闻，您需要为特定的新闻来源指定合约。这是通过符号（symbol）和交易所（exchange）唯一识别的。通过 `EClientSocket.reqContractDetails` 请求，可以轻松获取金融工具的符号。

通常情况下，符号是由提供商代码、一个冒号，然后是新闻提供商代码加上 “_ALL” 组成。例如，如果新闻提供商代码是 "XYZ"，那么BroadTape新闻的符号可能会是 "XYZ:XYZ_ALL"。这样的格式有助于API精确地识别并请求来自特定新闻提供商的新闻数据。

使用BroadTape新闻合约的步骤通常包括：

1. **确定新闻提供商代码**：首先，您需要知道您想要接收新闻的特定新闻提供商的代码。

2. **构建合约细节**：使用这个代码，您可以构建一个合约对象，其中包含用于标识新闻源的符号和交易所。

3. **请求合约细节**：通过 `EClientSocket.reqContractDetails` 方法，您可以获取这个特定新闻源的更多细节。

4. **请求新闻数据**：一旦有了合约的细节，您就可以使用 `EClient.reqMktData` 方法来请求这个新闻源的新闻数据。

通过这种方式，您可以确保从TWS API中获取您所需的特定新闻源信息，从而使您能够及时跟踪市场动态和重要事件。

```python
contract = Contract()
contract.symbol  = "BRF:BRF_ALL"
contract.secType = "NEWS"
contract.exchange = "BRF"
```

`EClient.reqMktData` 是一个API方法，通常用于请求市场数据，但也可用于检索新闻。该方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。

- `contract: Contract`：用于指定金融工具的合约对象。

- `genericTickList: String`：可用通用刻度的逗号分隔ID。

- `snapshot: bool`：在获取新闻数据时，始终设置为false。

- `regulatorySnapshot: bool`：在获取新闻数据时，始终设置为false。

- `mktDataOptions: List<TagValue>`：仅限内部使用。

当用于检索新闻时，可以指定“mdoff”来禁用标准市场数据。这意味着您可以专门请求新闻数据，而不会接收常规的市场数据更新。这在用户只对特定新闻源的实时更新感兴趣，而不需要实时的市场价格或其他市场数据时非常有用。

在使用此方法请求新闻时，您需要确保合约对象（`contract`）正确设置以指向您感兴趣的新闻源，并在 `genericTickList` 参数中指定适当的通用刻度代码，以便于正确请求和接收所需的新闻数据。

```python
self.reqMktData(reqId, contract, "mdoff,292", False, False, [])
```

### Receive Live News Headlines

`EWrapper.tickNews` 是一个API回调函数，用于返回请求合约的新闻头条。该方法的参数包括：

- `tickerId: int`：用于跟踪数据的请求标识符。

- `timeStamp: int`：文章发布时间的Epoch时间。

- `providerCode: String`：基于请求数据的新闻提供商代码。

- `articleId: String`：用于跟踪特定文章的标识符。更多信息请参见新闻文章。

- `headline: String`：提供的新闻文章的标题。

- `extraData: String`：返回关于文章的任何附加数据。

当使用 `EClient.reqMktData` 方法请求新闻时，`EWrapper.tickNews` 回调被触发，以返回相关合约的新闻头条。这个功能对于想要通过API及时获取和跟踪特定新闻事件的交易者来说非常有用。

每当有关于所请求合约的新闻更新时，`tickNews` 方法都会提供该新闻事件的详细信息，包括时间戳、新闻提供商、文章ID、标题，以及可能的额外数据。这使得用户能够及时了解市场新闻和相关事件，从而更好地做出交易和投资决策。

```python
def tickNews(self, tickerId: int, timeStamp: int, providerCode: str, articleId: str, headline: str, extraData: str):
    print("TickNews. TickerId:", tickerId, "TimeStamp:", timeStamp, "ProviderCode:", providerCode, "ArticleId:", articleId, "Headline:", headline, "ExtraData:", extraData)
```

## Historical News Headlines

确实，在拥有适当的API新闻订阅后，可以使用 `EClient::reqHistoricalNews` 函数从API请求历史新闻头条。请求的结果（即历史新闻头条）将返回到 `EWrapper::historicalNews`。

具体操作步骤如下：

1. **获取API新闻订阅**：首先，确保您的账户已订阅了所需的API新闻服务。

2. **使用 `EClient::reqHistoricalNews` 请求历史新闻**：通过此函数，您可以指定相关参数来请求特定时间范围内的历史新闻头条。参数通常包括请求标识符、新闻提供商代码、合约代码、开始日期、结束日期等。

3. **接收历史新闻头条**：请求发出后，`EWrapper::historicalNews` 回调函数会被触发，并返回历史新闻头条数据。这些数据通常包括新闻时间戳、新闻提供商代码、新闻标题等信息。

这个功能对于需要回顾和分析过去新闻事件的用户来说非常有用，尤其是对于那些新闻事件对市场有显著影响的情况。通过访问历史新闻数据，交易者和分析师可以更好地理解市场变化的背景和动因，从而做出更加明智的交易和投资决策。

### Requesting Historical News

`EClient.reqHistoricalNews` 是一个API方法，用于请求历史新闻头条。此方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

- `conId: int`：股票的合约ID。关于如何检索conId，请参见合约细节。

- `providerCodes: String`：用‘+’分隔的提供商代码列表。

- `startDateTime: String`：标记日期范围的（排他性）开始时间。格式为 yyyy-MM-dd HH:mm:ss。

- `endDateTime: String`：标记日期范围的（包含性）结束时间。格式为 yyyy-MM-dd HH:mm:ss。

- `totalResults: int`：要获取的最大头条数（1 – 300）。

- `historicalNewsOptions: Null`：保留供内部使用。应定义为null。

通过使用 `reqHistoricalNews` 方法，用户可以请求特定时间范围内的历史新闻头条。这对于分析过去的市场事件、理解历史新闻对市场的影响或进行历史数据研究非常有用。

这个功能使交易者和分析师能够访问到过去的新闻数据，帮助他们更好地理解市场的历史动态，从而在未来的交易和投资决策中做出更加明智的选择。通过明确的时间范围和新闻提供商代码，用户可以精确地定位和获取他们感兴趣的历史新闻信息。

```python
self.reqHistoricalNews(reqId, 8314, "BRFG", "", "", 10, [])
```

### Receive Historical News

`EWrapper.historicalNews` 是TWS API中的一个回调函数，用于返回请求合约的历史新闻头条。当您使用 `EClient.reqHistoricalNews` 方法请求历史新闻时，该方法会被触发。`EWrapper.historicalNews` 方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

- `time: int`：文章发布时间的Epoch时间。

- `providerCode: String`：基于请求数据的新闻提供商代码。

- `articleId: String`：用于跟踪特定文章的标识符。更多信息请参见新闻文章。

- `headline: String`：提供的新闻文章的标题。

此回调函数返回请求的历史新闻头条，包括每个新闻事件的发布时间、新闻提供商代码、文章ID和标题。这使用户能够查看和分析特定时间范围内的新闻事件，帮助他们理解过去的市场动态和新闻对市场的影响。

`historicalNews` 回调为交易者和分析师提供了一个强大的工具，以获取和利用历史新闻数据，从而更好地理解市场走势、制定策略或进行市场研究。通过准确的历史数据，用户可以对市场的过去行为有更深入的洞察，从而为未来的决策提供支持。

```python
def historicalNews(self, requestId: int, time: int, providerCode: str, articleId: str, headline: str):
    print("historicalNews. RequestId:", requestId, "Time:", time, "ProviderCode:", providerCode, "ArticleId:", articleId, "Headline:", headline)
```

----

`EWrapper.historicalNewsEnd` 是TWS API中的一个回调函数，用于标记历史新闻头条请求的结束。当 `EClient.reqHistoricalNews` 方法完成历史新闻头条的请求时，该方法被触发。`EWrapper.historicalNewsEnd` 方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

- `hasMore: bool`：指示是否还有更多数据（true表示有，false表示没有）。

此回调函数的主要用途是通知API用户已经接收到了所有针对特定请求的历史新闻头条，或者还有更多的新闻头条可以继续请求。这对于处理和管理历史新闻数据流非常重要，尤其是在进行大量数据分析或需要处理多个新闻请求时。

`historicalNewsEnd` 回调为用户提供了一个清晰的结束标记，从而可以确保他们已经收到了所有请求的数据，或者了解是否需要进行进一步的请求以获取更多数据。这有助于用户有效地管理他们的新闻数据请求和分析过程，确保他们能够获得所需的所有相关信息。

```python
def historicalNewsEnd(self, reqId: int, hasMore: bool):
    print("historicalDataEnd. ReqId:", reqId, "Has More:", hasMore)
```

## News Articles

确实，使用上述函数之一请求新闻头条后，可以通过调用 `IBApi::EClient::reqNewsArticle` 函数并使用返回的文章ID来请求新闻文章的正文。随后，新闻文章的正文会返回到 `IBApi::EWrapper::newsArticle` 函数。

具体操作步骤如下：

1. **请求新闻头条**：首先，使用 `EClient.reqMktData` 或 `EClient.reqHistoricalNews` 等函数请求新闻头条。

2. **获取文章ID**：从返回的新闻头条信息中获取文章ID（`articleId`）。这个ID是每篇新闻文章的唯一标识符。

3. **使用 `IBApi::EClient::reqNewsArticle` 请求文章正文**：通过 `reqNewsArticle` 函数，使用之前获得的文章ID请求文章的正文。需要提供的参数包括请求标识符和文章ID。

4. **接收文章正文**：`IBApi::EWrapper::newsArticle` 回调函数会被触发，返回请求的新闻文章正文。这个函数通常提供文章正文的内容以及相关的信息，如文章类型或新闻提供商代码。

这个流程允许用户不仅仅获取新闻头条，还能深入了解具体新闻文章的详细内容，这对于全面分析市场新闻和事件非常重要。通过访问新闻文章的完整正文，用户可以获得更深入的洞察和分析，从而更好地理解市场趋势和事件背后的详细信息。

### Request News Articles

`EClient.reqNewsArticle` 是一个API方法，用于根据给定的文章ID请求新闻文章的正文。此方法的参数包括：

- `requestId: int`：请求的ID。

- `providerCode: String`：指示新闻提供商的简短代码，例如 "FLY"。

- `articleId: String`：特定文章的ID。

- `newsArticleOptions: List`：保留供内部使用。应定义为null。

当您想要获取特定新闻文章的详细内容时，可以使用 `reqNewsArticle` 方法。您需要提供新闻提供商的代码和文章的唯一标识符（articleId）。这个功能对于那些不仅需要查看新闻头条，而且需要深入阅读完整新闻内容的用户来说非常有用。

调用此方法后，新闻文章的正文将通过 `IBApi::EWrapper::newsArticle` 回调函数返回。这使用户能夠访问和分析特定新闻事件的全面细节，从而更好地理解市场动态和事件背景。通过这种方式，交易者和分析师可以在他们的交易决策和市场分析中利用更丰富的信息源。

```python
self.reqNewsArticle(10002,"BRFG", "BRFG$04fb9da2", [])
```

### Receive News Articles

`EWrapper.newsArticle` 是TWS API中的一个回调函数，用于在响应 `reqNewsArticle()` 请求时接收新闻文章。当您使用 `EClient.reqNewsArticle` 方法请求一个新闻文章的正文后，此函数被调用。`EWrapper.newsArticle` 方法的参数包括：

- `requestId: int`：用于跟踪数据的请求标识符。

- `articleType: int`：新闻文章的类型（0表示普通文本或HTML，1表示二进制数据/ PDF）。

- `articleText: String`：文章的正文（如果 `articleType` 等于1，则二进制数据使用Base64方案编码）。

此回调函数的主要作用是返回请求的新闻文章正文。根据 `articleType` 的不同，它可以是普通文本格式的文章，也可能是以二进制形式（如PDF）提供的，后者会用Base64编码。这使用户能够访问和阅读他们请求的具体新闻文章内容。

对于交易者和市场分析师来说，这个功能非常重要，因为它提供了对市场新闻事件深入理解的途径。无论是通过文本形式还是PDF等格式，能够详细阅读新闻文章可以帮助他们获得关于市场动态和事件背景的更多信息，这对于做出明智的交易和投资决策至关重要。

```python
def newsArticle(self, requestId: int, articleType: int, articleText: str):
    print("requestId: ", requestId, "articleType: ", articleType, "articleText: ", articleText)
```

