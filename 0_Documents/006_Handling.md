- [Error Handling](#error-handling)
  - [Advanced order rejection](#advanced-order-rejection)
  - [Error Message Codes](#error-message-codes)
  - [Receive Error Messages](#receive-error-messages)


# Error Handling

当客户端应用程序向 TWS 发送需要响应的消息（例如下订单、请求市场数据、订阅账户更新等）时，TWS 几乎总是会 1）用相关数据响应或 2）通过 `IBApi::EWrapper::error` 发送错误消息。

无响应的异常情况：如果在完全建立连接之前发出请求（通过返回的 2104 或 2106 错误代码“数据服务器正常”表示），请求可能不会收到响应。
TWS 发送的错误消息由 `IBApi.EWrapper.error` 方法处理。`IBApi.EWrapper.error` 事件包含发起请求的 ID（或在下订单时引发错误的订单 ID）、数字错误代码和简要描述。需要注意的是，这个函数既用于真正的错误消息，也用于并不意味着有任何问题的通知。

当 TWS 未设置为英语时的 API 错误消息

目前在 Windows 平台上，错误消息使用 Latin1 编码发送。如果 TWS 是以非西方语言启动的，建议在全局配置 -> API -> 设置中启用“以英语显示 API 错误消息”的设置。

## Advanced order rejection

`advancedErrorOverride` 和 `EWrapper.error` 中的 `advancedOrderRejectJson` 属性是交互式经纪人（Interactive Brokers, IB）API 的高级功能，用于处理订单放置时的特定错误和拒绝。

- `advancedErrorOverride` 接受包含错误标签的参数的逗号分隔列表。此列表将覆盖提到的错误，并继续进行订单放置。它使得 API 用户能够自定义他们希望忽略的特定错误类型，从而在特定条件下强制执行订单。

- `advancedOrderRejectJson` 以 JSON 格式返回订单拒绝描述。这些 JSON 响应可以用来向 `advancedErrorOverride` 添加字段。此功能提供了更详细的错误信息，有助于理解订单被拒绝的具体原因。

当 API 客户端从后端收到拒绝消息时，响应将包含一个 FIX 标签 8230，这是订单拒绝消息的标签。例如，响应可能包含代码 8229=IBDBUYTX，这可以在 `IBApi::EClient::placeOrder` 方法中传递到 8229（`advancedErrorOverride`）字段。标签 8229 的值可以包含高级错误覆盖代码的逗号分隔列表，例如 8229=IBDBUYTX,MOBILEPH 将覆盖这两种拒绝。

此外，引入了一个新的 API 设置：“始终在 UI 中显示高级订单拒绝”。如果选中此选项，订单拒绝将不会发送到 API，这意味着拒绝消息将直接在交易工作站（TWS）界面上显示，而不是通过 API 传送。这对于希望在 TWS UI 中直接处理订单拒绝的用户来说很有用。

## Error Message Codes

有关 TWS API 中所有可用错误代码的完整列表，请访问我们的 TWS API 错误消息页面。

## Receive Error Messages

`EWrapper.error` 方法用于处理错误消息。该方法的参数包括：

- `reqId: int`：与维护错误流的最近的 `reqId` 相对应的请求标识符。这不涉及来自 `placeOrder` 的 `orderId`，而是最近的 `requestId`。
- `errorCode: int`：用于识别错误的代码。
- `errorMsg: String`：错误的描述。
- `advancedOrderRejectJson: String`：以 JSON 格式的高级订单拒绝描述。

具体的错误消息及其代码可以在 TWS 错误代码部分找到更多详情。这些信息对于理解和处理在使用 TWS API 过程中可能遇到的各种错误至关重要，特别是对于自动化交易系统来说，准确地解释和响应这些错误消息是确保系统稳定运行的关键。

```python
    def error(self, reqId: TickerId, errorCode: int, errorString: str, advancedOrderRejectJson = ""):
        print("Error. Id:", reqId, "Code:", errorCode, "Msg:", errorString, "AdvancedOrderRejectJson:", advancedOrderRejectJson)
```