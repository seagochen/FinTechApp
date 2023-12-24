- [Bulletins](#bulletins)
  - [Request IB BulletinsCopy Location](#request-ib-bulletinscopy-location)
  - [Receive IB BulletinsCopy Location](#receive-ib-bulletinscopy-location)
  - [Cancel Bulletin Request](#cancel-bulletin-request)


# Bulletins

交互式经纪人（Interactive Brokers, IB）会不时发送重要的新闻公告，这些公告可以通过 TWS API 中的 `EClient.reqNewsBulletins` 访问。每当有新的公告时，公告会通过 `IBApi.EWrapper.updateNewsBulletin` 传递。为了停止接收这些公告，您需要取消订阅。

## Request IB Bulletins

`EClient.reqNewsBulletins` 方法用于请求新闻公告。该方法的参数包括：

- `allMessages: bool`：如果设置为 `true`，将返回当天现有的所有公告；如果设置为 `false`，则只接收新的公告。

通过使用此方法，用户可以根据需要选择接收当天所有的新闻公告或仅接收新发布的公告。这对于及时获取交易相关的重要信息非常有用，尤其是在需要跟踪市场动态或紧急通知时。

```python
    self.reqNewsBulletins(True)
```

## Receive IB Bulletins

`EWrapper.updateNewsBulletin` 方法用于接收新闻公告更新。该方法的参数包括：

- `msgId: int`：公告的标识符。
- `msgType: int`：公告类型，1 表示常规新闻公告；2 表示某个交易所不再可用于交易；3 表示某个交易所现在可用于交易。
- `message: String`：新闻公告的内容。
- `origExchange: String`：消息来源的交易所。

此方法在接收到来自 `EClient.reqNewsBulletins` 的新闻公告请求后被触发，提供公告的详细信息，包括其类型、内容以及来源交易所。这对于用户及时了解和响应市场和交易所的重要通知非常有用。

```python
def updateNewsBulletin(self, msgId: int, msgType: int, newsMessage: str, originExch: str):
    print("News Bulletins. MsgId:", msgId, "Type:", msgType, "Message:", newsMessage, "Exchange of Origin: ", originExch)
```

## Cancel Bulletin Request

`EClient.cancelNewsBulletin` 方法用于取消订阅交互式经纪人（IB）的新闻公告。通过调用这个方法，用户可以停止接收通过 `EClient.reqNewsBulletins` 方法订阅的新闻公告更新，这对于控制接收的信息量或结束对特定新闻公告的关注非常有用。

```python
    self.cancelNewsBulletins()
```