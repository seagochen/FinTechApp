from AccountsModel import AccountsModel


def get_model(model_type, db_path):
    if model_type == 'Accounts':
        return AccountsModel(db_path)
    # 可以根据需要添加其他表的处理逻辑
    else:
        raise ValueError("Unknown model type")


if __name__ == '__main__':
    from Controller import *

    # Get model from database
    model = get_model('Accounts', r'..\utility\database.db')

    # 添加账户
    new_account = AccountsModel(r'..\utility\database.db', 'id1', 1, 'Account1', 1, 'password', 1, 'Note')
    add(new_account)

    # 查询账户
    print(get(new_account))

    # 更新账户
    new_account.update_from_dict({
        "ServiceProviderId": 2,
        "AccountName": "NewAccount",
        "AccountTypeId": 2,
        "AccountPassword": "newpassword",
        "RestrictionId": 2,
        "Note": "New Note"
    })
    update(new_account)

    # 查询更新后的账户
    print(get(new_account))

    # 删除账户
    delete(new_account)
