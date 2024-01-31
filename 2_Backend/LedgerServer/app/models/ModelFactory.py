from app.models.AccountsModel import AccountsModel
from app.models.AccountsViewModel import AccountsViewModel
from app.models.AccountTypeModel import AccountTypeModel
from app.models.ServiceProviderModel import ServiceProviderModel
from app.models.RestrictionModel import RestrictionModel


def get_model(model_type, db_path):
    if model_type == 'Accounts':
        return AccountsModel(db_path)
    if model_type == 'AccountsView':
        return AccountsViewModel(db_path)
    if model_type == 'AccountType':
        return AccountTypeModel(db_path)
    if model_type == 'ServiceProvider':
        return ServiceProviderModel(db_path)
    if model_type == 'Restriction':
        return RestrictionModel(db_path)
    else:
        raise ValueError("Unknown model type")
