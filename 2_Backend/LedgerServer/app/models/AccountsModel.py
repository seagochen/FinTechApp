from BaseModel import BaseModel


class AccountsModel(BaseModel):
    def __init__(self, db_path, account_id=None, service_provider_id=None, account_name=None,
                 account_type_id=None, account_password=None, restriction_id=None, note=None):
        super().__init__(db_path, 'Accounts')
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
