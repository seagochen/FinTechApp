from app.models.BaseModel import BaseModel
from app.utility.DownloadSqlScript import DATABASE_NAME


class ServiceProviderModel(BaseModel):
    def __init__(self, db_path=DATABASE_NAME, service_provider_id=None, service_provider=None):
        super().__init__(db_path, 'ServiceProvider', 'ServiceProviderId', service_provider_id)
        self.service_provider_id = service_provider_id
        self.service_provider = service_provider

    def to_dict(self):
        return {
            "ServiceProviderId": self.service_provider_id,
            "ServiceProvider": self.service_provider,
        }
