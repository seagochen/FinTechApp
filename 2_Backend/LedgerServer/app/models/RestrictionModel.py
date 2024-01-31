from app.models.BaseModel import BaseModel
from app.utility.DownloadSqlScript import DATABASE_NAME


class RestrictionModel(BaseModel):
    def __init__(self, db_path=DATABASE_NAME, restriction_id=None, restriction_name=None):
        super().__init__(db_path, 'Restriction', 'RestrictionId', restriction_id)
        self.restriction_id = restriction_id
        self.restriction_name = restriction_name

    def to_dict(self):
        return {
            "RestrictionId": self.restriction_id,
            "RestrictionName": self.restriction_name,
        }
