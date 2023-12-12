# Family Codes

通过 API 可以确定一个账户是否属于某个账户家族，并且可以使用 reqFamilyCodes 函数来找到该家族的代码。

例如，如果个人账户 U112233 属于账号为 F445566 的财务顾问下，当为账户 U112233 的用户调用 reqFamilyCodes 函数时，将返回家族代码 “F445566A”，这表明它属于那个账户家族。这个功能对于理解和管理属于财务顾问管理下的多个账户的组织结构非常有用。

## Request Family Codes

```python
EClient.reqFamilyCodes()
```

当需要请求一个账户的家族代码，例如判断它是否是财务顾问（FA）账户、IBroker账户，或者是相关联的账户时，可以使用特定的 API 函数。这个函数通常被设计用来识别账户的类型和它在更大的组织结构中的位置，这对于管理和监控多账户系统尤为重要。

例如，在交互式经纪人（Interactive Brokers, IB）的 API 中，这个功能可能是通过调用 reqFamilyCodes 函数实现的。当这个函数被调用时，它会返回与请求的账户相关的所有家族代码。这些家族代码有助于识别账户是独立账户、属于某个财务顾问的一部分，或者是IBroker系统中的其他类型账户。通过这些信息，用户或开发者可以更好地理解和管理他们的投资组合。

## Receive Family Codes

EWrapper.familyCodes
* @param familyCodes: 返回一个包含家族代码的列表

```python
def familyCodes(self, familyCodes: ListOfFamilyCode):
    print("Family Codes:", familyCode)
```

# Updates on EWrapper Interface

因为意识到客户端可能需要同时处理多个不同的消息，所以可能存在资源竞争的情况。为此，我们开发了一个多线程带锁的List和Dict，用于存储消息。这些消息将在客户端的主线程中被处理，而不是在消息线程中。这样，客户端就可以在主线程中处理消息，而不必担心资源竞争。

```python
from ibapi.wrapper import EWrapper
from ibapi.client import EClient
from ibapi.contract import Contract

# TwsApp for interacting with TWS API
class TwsApp(EWrapper, EClient):
    def __init__(self):
        EClient.__init__(self, self)
        self.nextOrderId = None
        
        # client flag message queue
        self.flag_queue = []

        # account summary
        self.account_summary = []
        
        # account updates
        self.account_updates = {}
        self.account_portfolio = {}
        self.account_update_time = None

        # family code
        self.family_codes = []
    
    def get_flag_queue(self):
        flag_queue = []

        while not self.flag_queue.empty():
            flag_queue.append(self.flag_queue.get())
        
        return flag_queue
    
    def get_account_summary(self):
        account_summary = []

        while not self.account_summary.empty():
            account_summary.append(self.account_summary.get())
        
        return account_summary
    
    def get_account_updates(self):
        account_updates = {}

        for key in self.account_updates.dict.keys():
            account_updates[key] = self.account_updates.dict[key]
        
        return account_updates

    def get_account_portfolio(self):
        account_portfolio = {}

        for key in self.account_portfolio.dict.keys():
            account_portfolio[key] = self.account_portfolio.dict[key]
        
        return account_portfolio
    
    def get_account_update_time(self):
        return self.account_update_time

    def error(self, reqId, errorCode, errorString, advancedOrderRejectJson=None):
        print("Error: ", reqId, " ", errorCode, " ", errorString, " ", advancedOrderRejectJson)

    def get_family_codes(self):
        family_codes = []

        while not self.family_codes.empty():
            family_codes.append(self.family_codes.get())
        
        return family_codes

    def cleanup(self):
        # clean flag queue
        self.flag_queue = []

        # clean account summary
        self.account_summary = []

        # clean account updates
        self.account_updates = {}
        self.account_portfolio = {}

        # clean family code
        self.family_codes = []

    ### 账号的概览信息 ###

    def accountSummary(self, reqId: int, account: str, tag: str, value: str,currency: str):
        self.account_summary.append({
            "method": "accountSummary",
            "reqId": reqId, 
            "account": account, 
            "tag": tag, 
            "value": value, 
            "currency": currency}) 
            
    def accountSummaryEnd(self, reqId: int):
        self.flag_queue.append({
            "method": "accountSummaryEnd",
            "reqId": reqId
        })

    ### 账号的更新信息: 单个账户 ###

    def updateAccountValue(self, key: str, val: str, currency: str, accountName: str):
        self.account_updates[key] = {
            "method": "updateAccountValue",
            "key": key,
            "val": val,
            "currency": currency,
            "accountName": accountName
        }

    def updatePortfolio(self, contract: Contract, position: float,
                            marketPrice: float, marketValue: float, 
                            averageCost: float, unrealizedPNL: float, 
                            realizedPNL: float, accountName: str):
        self.account_portfolio[contract.symbol] = {
            "method": "updatePortfolio",
            "contract": contract,
            "position": position,
            "marketPrice": marketPrice,
            "marketValue": marketValue,
            "averageCost": averageCost,
            "unrealizedPNL": unrealizedPNL,
            "realizedPNL": realizedPNL,
            "accountName": accountName
        }

    def updateAccountTime(self, timeStamp: str):
        self.account_update_time = timeStamp

    def accountDownloadEnd(self, accountName: str):
        self.flag_queue.append({
            "method": "accountDownloadEnd",
            "accountName": accountName
        })

    ### 账号的更新信息: 多个账户 ###

    def accountUpdateMulti(self, reqId: int, account: str, modelCode: str, key: str, value: str, currency: str):
        self.account_updates[key] = {
            "method": "accountUpdateMulti",
            "reqId": reqId,
            "account": account,
            "modelCode": modelCode,
            "key": key,
            "value": value,
            "currency": currency
        }

    def accountUpdateMultiEnd(self, reqId: int):
        self.flag_queue.append({
            "method": "accountUpdateMultiEnd",
            "reqId": reqId
        })

    ### 获取账号的Family Code ###
    def familyCodes(self, familyCodes):
        self.family_codes.append({
            "method": "familyCodes",
            "familyCodes": familyCodes
        })
```