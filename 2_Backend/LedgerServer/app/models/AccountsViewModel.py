from app.models.BaseModel import BaseModel
from app.utility.DownloadSqlScript import DATABASE_NAME


class AccountsViewModel(BaseModel):
    def __init__(self, db_path=DATABASE_NAME, account_id=None, service_provider=None, account_name=None,
                 account_type_name=None, account_password=None, restriction_name=None, note=None):
        super().__init__(db_path, 'AccountsView', 'AccountId', account_id)
        self.account_id = account_id
        self.service_provider = service_provider
        self.account_name = account_name
        self.account_type_name = account_type_name
        self.account_password = account_password
        self.restriction_name = restriction_name
        self.note = note

    def to_dict(self):
        return {
            "AccountId": self.account_id,
            "ServiceProvider": self.service_provider,
            "AccountName": self.account_name,
            "AccountTypeName": self.account_type_name,
            "AccountPassword": self.account_password,
            "RestrictionName": self.restriction_name,
            "Note": self.note
        }
