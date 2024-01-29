from BaseModel import BaseModel


class AccountsModel(BaseModel):
    def __init__(self, db_path):
        super().__init__(db_path, 'Accounts')
