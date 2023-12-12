# Financial Advisors

## Allocation Methods and Groups

对于财务顾问和 IBroker 账户结构，提供了多种方法和配置文件，用以指定如何在多个账户之间分配交易。这一功能允许在多个账户间进行交易。API 提供的功能与 TWS 中的相同。

财务顾问账户的“群组”和“配置文件”订单分配方法可以直接在 TWS 中创建：分配和转移。在最初创建之后，可以通过 API 直接使用 `IBApi.EClient.replaceFA()` 方法进行修改。

从以下方法名称中可以看出，“群组”将基于账户的固有属性（如清算价值或资产净值）分配订单，而“配置文件”则提供了基于明确比例或百分比分配已分配份额的可能性。

注意：有一个 API 设置，它合并了群组和配置文件，其中两种分配方法都被视为群组。请参阅“群组和配置文件的统一”。

## Allocation by Group

在财务顾问和 IBroker 账户结构中，可以使用几种订单分配方法来在多个账户间分配交易。以下是一些具体的分配方法：

1. **EqualQuantity**  
   - **要求**：指定订单大小。
   - **方法**：在群组中的所有账户间平均分配股份。
   - **示例**：传输400股 ABC 股票的订单。如果账户群组包括四个账户，每个账户接收100股。如果包括六个账户，每个账户接收66股，剩余的股份平均分配给每个账户。

2. **NetLiq**  
   - **要求**：指定订单大小。
   - **方法**：根据每个账户的净清算价值分配股份。系统根据每个账户的净清算价值计算比例，并根据这些比例分配股份。
   - **示例**：传输700股 XYZ 股票的订单。账户群组包括三个账户 A、B 和 C，净清算价值分别为 $25,000、$50,000 和 $100,000。系统计算出 1:2:4 的比例，分别给 A 客户分配 100 股，给 B 客户分配 200 股，给 C 客户分配 400 股。

3. **AvailableEquity**  
   - **要求**：指定订单大小。
   - **方法**：根据每个账户的可用资产净值分配股份。系统根据每个账户的可用资产净值计算比例，并根据这些比例分配股份。
   - **示例**：传输700股 XYZ 股票的订单。账户群组包括三个账户 A、B 和 C，可用资产净值分别为 $25,000、$50,000 和 $100,000。系统计算出 1:2:4 的比例，分别给 A 客户分配 100 股，给 B 客户分配 200 股，给 C 客户分配 400 股。

4. **PctChange**  
   - **要求**：不指定订单大小。由系统计算数量，订单大小在订单确认后显示在数量字段中。
   - **方法**：增加或减少已存在的持仓。正百分比会增加持仓，负百分比会减少持仓。例如，要完全平仓，只需指定百分比为 -100。
   - **买单**
     - 正百分比：增加多头持仓，空头持仓无影响。
     - 负百分比：多头持仓无影响，减少空头持仓。
   - **卖单**
     - 正百分比：增加空头持仓，多头持仓无影响。
     - 负百分比：减少多头持仓，空头持仓无影响。

这些分配方法提供了灵活的方式来根据各个账户的具体情况分配交易，使得财务顾问能够有效地管理多个客户账户。

## Allocation by Profile

在对配置文件的分配进行修改时，该方法使用枚举值。右边的数字精确地展示了哪个配置文件对应哪个值。

**配置文件方法**和**类型编号**：

- 百分比（Percentages）：1
- 财务比率（Financial Ratios）：2
- 股份（Shares）：3

**百分比（Percentages）**  
- 此方法会根据您指定的百分比在列表中的账户之间分配订单中的总股数。例如，使用一个四个账户各占25%的配置文件进行1000股的订单，将会在配置文件中列出的每个账户分配250股。

**财务比率（Financial Ratios）**  
- 此方法根据您输入的比率计算股份的分配。例如，使用一个设置为4, 2, 1, 1比率的四个账户的配置文件进行1000股的订单，将分别分配500, 250, 125和125股给列出的账户。

**股份（Shares）**  
- 此方法会将您输入的绝对股数分配给列表中的每个账户。如果使用此方法，订单大小通过将分配给配置文件中每个账户的股数相加来计算。

这些方法为财务顾问提供了灵活的工具来根据客户的具体需求和投资策略，在多个账户之间分配交易。

## Managing Groups and Profiles from the API

客户可以通过 TWS API 管理他们现有的分配群组。

注意：通过 API 所做的修改将影响通过 TWS、TWS API、客户门户（Client Portal）和客户门户 API 下达的订单。这意味着，无论是在哪个平台或接口进行的订单操作，都将受到通过 API 进行的分配群组设置的影响。因此，使用 API 修改分配群组时需要谨慎，以确保所有相关的交易平台和接口都能正确反映这些变更。

## Request FA Groups and Profiles

`EClient.requestFA` 方法用于请求财务顾问（FA）的配置信息，如在 TWS 中为指定的 FA 群组或配置文件所设置。该方法的参数包括：

- `faDataType: int`：要更改的配置。根据下表定义，设置为 1 或 3。

此方法允许用户获取当前在 TWS 中设置的 FA 群组或配置文件的详细配置信息。这对于理解和管理财务顾问账户的分配策略非常有用，尤其是当需要在不同的交易平台和接口之间协调一致的交易策略时。

```python
    self.requestFA(1)
```

FA Data Types


| Type Code | Type Name       | Description                                                                                                          |
| --------- | --------------- | -------------------------------------------------------------------------------------------------------------------- |
| 1         | Groups          | offer traders a way to create a group of accounts and apply a single allocation method to all accounts in the group. |
| 3         | Account Aliases | let you easily identify the accounts by meaningful names rather than account numbers.                                |

## Receiving FA Groups and Profiles

`EWrapper.receiveFA` 方法用于接收 TWS 中可用的财务顾问（FA）配置。该方法的参数包括：

- `faDataType: int`：接收在 `requestFA` 中指定的 `faDataType` 值。参见 FA 数据类型。
- `faXmlData: String`：以 XML 格式的配置。

此方法在响应对 `EClient.requestFA` 的请求时被触发，提供了财务顾问在 TWS 中的配置信息。这使得用户能够访问和了解他们在 TWS 中设置的 FA 群组或配置文件的详细配置数据，这对于管理和调整这些设置非常重要，尤其是在跨平台进行交易管理时。

```python
def receiveFA(self, faData: FaDataType, cxml: str):
   print("Receiving FA: ", faData)
   open('log/fa.xml', 'w').write(cxml)
```

## Replace FA Allocations

`EClient.replaceFA` 方法用于更改财务顾问（FA）的配置结构。该方法的参数包括：

- `reqId: int`：用于跟踪数据的请求标识符。
- `faDataType: int`：要更改的配置结构。参见 FA 数据类型。
- `xml: String`：分配配置文件或群组的 XML 配置。更多细节请参见 ReplaceFA XML 结构。

此方法允许用户通过发送 XML 格式的配置来更新财务顾问的配置文件或群组设置。这对于在交易策略发生变化时调整分配方法或进行其他相关配置更新非常有用，特别是在需要精细管理多账户交易策略的情况下。通过 `reqId` 可以跟踪这些更改的请求，确保操作的准确性和有效性。

```python
   self.replaceFa(reqId, 1, xml)
```

### ReplaceFA XML Structure

右侧的结构是分配的通用结构。然而，为了获得最准确的结果，我们建议在修改之前首先调用 EClient.requestFa 方法来确认现有信息。

这样做的目的是确保在进行任何修改之前，您已经准确了解当前的配置情况。这一步骤对于避免误改重要设置，以及确保新的配置与现有的财务顾问（FA）策略和账户设置相协调，是非常重要的。通过 EClient.requestFa，可以获取现有的分配配置文件或群组的详细信息，然后基于这些信息进行适当的调整和更新。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ListOfGroups>
  <Group>
  <name>Profile_Name</name>
  <defaultMethod>Allocation_Method</defaultMethod>
  
  <ListOfAccts varName="list">
  <Account>
    <acct>Account_n</acct>
    <amount>Amount</amount>
  </Account>
  <Account>
    <acct>Account_n+1</acct>
    <amount>Amount</amount>
  </Account>
  </ListOfAccts>
  </Group>
</ListOfGroups>
```

## Model Portfolios and the API

财务顾问可以使用模型投资组合轻松地将客户的部分或全部资产投资到一个或多个自定义创建的投资组合中，而不是繁琐地管理单个工具中的个别投资。

**关于模型投资组合的更多信息**

TWS API 可以访问那些已在 TWS 中设置了特定模型的账户中的模型投资组合。API 功能允许客户端应用程序请求模型持仓更新订阅、请求模型账户更新订阅，或向特定模型下订单。

TWS API 中不可用的模型投资组合功能：

- 模型投资组合创建
- 模型投资组合再平衡
- 模型投资组合持仓或现金转移

要从特定模型请求持仓更新，可以使用 `IBApi::EClient::reqPositionsMulti` 函数：通过模型订阅持仓更新。

要请求模型账户更新，可以使用 `IBApi::EClient::reqAccountUpdatesMulti` 函数，见：通过模型订阅账户价值更新。

要向模型下订单，必须相应地设置 `IBApi.Order.ModelCode` 字段，例如：

```python
   modelOrder = Order()
   modelOrder.account = "DF12345"
   modelOrder.modelCode = "Technology" # model for tech stocks first created in TWS
   self.placeOrder(self.nextOrderId(), contract, modelOrder)
```

## Unification of Groups and ProfilesCopy Location

从 TWS/IBGW 构建 983+ 开始，API 设置中将有一个新的标志/复选框，“使用账户群组与分配方法”（对于新用户默认启用）。如果未启用，群组和配置文件的行为与以前相同。如果勾选，群组和配置文件功能将合并，API 客户端将看到以下变化：

- 如果参数为 Group，则 `IBApi::EClient::replaceFA` 和 `IBApi::EClient::requestFA` 将发送和接收统一的群组/配置文件列表。
- 如果参数为 Profile，则 `IBApi::EClient::replaceFA` 和 `IBApi::EClient::requestFA` 将接收错误。
- `IBApi::EClient::placeOrder` 将支持将配置文件名称指定为 `IBApi.Order.FaGroup` 名称；`IBApi::Order::FaMethod` 可以省略。
- 如果指定实际的群组名称且 faMethod 为空/省略，则将使用该群组的默认方法。否则，请求中的方法将设置在订单上。
- `IBApi::EWrapper::openOrder` 回调将在订单为配置文件时报告配置文件而非群组。
- 以下从 API 发出的调用，接受群组名称的地方也将接受配置文件名称：
  - `IBApi::EClient::reqAccountSummary` / `IBApi::EClient::cancelAccountSummary`
  - `IBApi::EClient::reqPositionsMulti` / `IBApi::EClient::cancelPositionsMulti`
  - `IBApi::EClient::reqAccountUpdatesMulti` / `IBApi::EClient::cancelAccountUpdatesMulti`

**模型投资组合和 API**

- 向 FA 账户下订单时，可以通过 API 功能向特定模型投资组合下订单。这为财务顾问管理多个客户账户提供了灵活性和效率，允许他们更容易地将资产分配到不同的投资策略中。