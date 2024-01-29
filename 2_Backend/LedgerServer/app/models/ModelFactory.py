from AccountsModel import AccountsModel


class ModelFactory:
    def get_model(self, model_type, db_path):
        if model_type == 'Accounts':
            return AccountsModel(db_path)
        # 可以根据需要添加其他表的处理逻辑
        else:
            raise ValueError("Unknown model type")


if __name__ == '__main__':
    # 创建工厂和模型实例
    factory = ModelFactory()
    model = factory.get_model('Accounts', r'..\utility\database.db')

    # 删除全部数据
    model.delete_all()

    # 示例：添加账户
    model.add(
        "(AccountId, ServiceProviderId, AccountName, AccountTypeId, AccountPassword, RestrictionId, Note)",
        ('id1', 1, 'Account1', 1, 'password', 1, 'Note'))

    # 示例：查询账户
    print(model.get("AccountId", "id1"))

    # 示例：更新账户
    model.update("AccountId", "id1",
                 "ServiceProviderId = ?, AccountName = ?, AccountTypeId = ?, AccountPassword = ?, "
                 "RestrictionId = ?, Note = ?",
                 (2, 'NewAccount', 2, 'newpassword', 2, 'New note'))

    # 查询
    print(model.get("AccountId", "id1"))

    # 示例：删除账户
    model.delete("AccountId", "id1")

    # 显示全部数据
    print(model.get_all())
