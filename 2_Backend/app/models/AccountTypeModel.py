from app.models.BaseModel import BaseModel
from app.utility.DownloadSqlScript import DATABASE_NAME


class AccountTypeModel(BaseModel):
    def __init__(self, db_path=DATABASE_NAME, account_type_id=None, account_type_name=None):
        super().__init__(db_path, 'AccountType', 'AccountTypeId', account_type_id)
        self.account_type_id = account_type_id
        self.account_type_name = account_type_name

    def to_dict(self):
        return {
            "AccountTypeId": self.account_type_id,
            "AccountTypeName": self.account_type_name,
        }
