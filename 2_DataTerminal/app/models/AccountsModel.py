from app.models.BaseModel import BaseModel
from app.utility.DownloadSqlScript import DATABASE_NAME


class AccountsModel(BaseModel):
    def __init__(self, db_path=DATABASE_NAME, account_id=None, service_provider_id=None, account_name=None,
                 account_type_id=None, account_password=None, restriction_id=None, note=None):
        super().__init__(db_path, 'Accounts', 'AccountId', account_id)
        self.account_id = account_id
        self.service_provider_id = service_provider_id
        self.account_name = account_name
        self.account_type_id = account_type_id
        self.account_password = account_password
        self.restriction_id = restriction_id
        self.note = note

    def to_dict(self):
        return {
            "AccountId": self.account_id,
            "ServiceProviderId": self.service_provider_id,
            "AccountName": self.account_name,
            "AccountTypeId": self.account_type_id,
            "AccountPassword": self.account_password,
            "RestrictionId": self.restriction_id,
            "Note": self.note
        }

    # def update_from_tuplet(self, tuplet):
    #     (self.account_id,
    #      self.service_provider_id,
    #      self.account_name,
    #      self.account_type_id,
    #      self.account_password,
    #      self.restriction_id,
    #      self.note) = tuplet
