# Managed Accounts


一个用户名可以处理多个账户。如在连接部分提到的，一旦连接建立，TWS（交易工作站）将自动发送一个被管理账户的列表。这个列表也可以通过 `IBApi.EClient.reqManagedAccts` 方法获取。

## Request Managed Accounts

EClient.reqManagedAccts()
请求登录用户有权访问的帐户。

```python
    self.reqManagedAccts()
```

## Receive Managed Accounts

EWrapper.managedAccounts 
* @param **accountsList**, string, 以逗号分隔的账户列表

```python
    def managedAccounts(self, accountsList: str):
        print("Account list:", accountsList)
```
