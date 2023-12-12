# White Branding User Info

这个函数将返回与用户关联的白标（White Branding）ID。

请注意，如果请求的用户名没有与任何白标实体关联，那么将不会返回任何内容。

## Requesting White Branding InfoCopy Location

`EClient.reqUserInfo` 方法用于请求用户信息。该方法的参数包括：

- `reqId: int`：请求 ID。

通过指定请求 ID，此函数用于请求关于当前用户的信息。这可能包括与用户账户关联的特定数据，如白标（White Branding）ID。
如果用户没有与任何特定实体（如白标实体）关联，那么该请求可能不会返回任何数据。这个功能对于在进行API交互时识别和确认用户身份非常有用。

```python
    self.reqUserInfo(reqId)
```

## Receiving White Branding Info

`EWrapper.userInfo` 方法用于接收用户信息。该方法的参数包括：

- `reqId: int`：给定请求的标识符。
- `whiteBrandingId: String`：白标（White Branding）实体的标识符。

此方法在收到 `EClient.reqUserInfo` 请求后被触发，用于提供请求的用户信息，包括与用户关联的白标实体的标识符。这对于获取和处理用户特定的配置信息或身份标识非常有用，特别是在需要区分不同用户或用户组的情况下。

```python
    def userInfo(self, reqId: int, whiteBrandingId: str):
        print("UserInfo.", "ReqId:", reqId, "WhiteBrandingId:", whiteBrandingId)
```