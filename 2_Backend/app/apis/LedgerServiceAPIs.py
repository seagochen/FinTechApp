import base64

from flask import Blueprint, request, jsonify

from app import HttpRespond
from app import RequestParams
from app.HttpRespond import HTTP_STATUS
from app.utility.DownloadSqlScript import *

ledger_service = Blueprint('ledger_service', __name__, url_prefix='/ledger_service')


def create_sqlite_database():
    # Download the sqlite scripts from GitHub
    download_file(GITHUB_CREATION_URL, filename="create_table.sql")
    download_file(GITHUB_UPDATEVIEW_URL, filename="update_view.sql")
    download_file(GITHUB_TRIGGER_URL, filename="trigger_table.sql")

    # Check if the database file exists
    if os.path.exists(DATABASE_NAME):
        # If the database file exists, delete it
        os.remove(DATABASE_NAME)

    # Execute the downloaded sqlite files
    execute_script("create_table.sql", database=DATABASE_NAME)
    execute_script("update_view.sql", database=DATABASE_NAME)
    execute_script("trigger_table.sql", database=DATABASE_NAME)


@ledger_service.route('/setup', methods=['GET'])
def setup():
    # Processing GET request
    params = RequestParams.get_request_params(request)

    # If args is not None, then get the args
    if params['args'] is not None:  # /setup?method=create
        if hasattr(params['args'], 'method') and params['args']['method'] == 'create':
            create_sqlite_database()  # /setup?method=update&param=base64_str
        elif hasattr(params['args'], 'method') and params['args']['method'] == 'update':
            base64_decode_string = base64.urlsafe_b64decode(params['args']['param'])



    else:
        return jsonify(HttpRespond.response(HTTP_STATUS.CERR_Not_Acceptable, "Parameters are not acceptable"))

    return jsonify(HttpRespond.response(HTTP_STATUS.CERR_Bad_Request, "No method specified"))


@ledger_service.route('/accounts', methods=['GET'])
def accounts():
    return jsonify(HttpRespond.response(HTTP_STATUS.CERR_Bad_Request, "No method specified"))


@ledger_service.route('/accounts/restriction_opts', methods=['GET'])
def accounts_restriction_opts():
    return jsonify(HttpRespond.response(HTTP_STATUS.CERR_Bad_Request, "No method specified"))


@ledger_service.route('/accounts/account_type_opts', methods=['GET'])
def accounts_account_type_opts():
    return jsonify(HttpRespond.response(HTTP_STATUS.CERR_Bad_Request, "No method specified"))


@ledger_service.route('/accounts/service_providers_opts', methods=['GET'])
def accounts_service_providers_opts():
    return jsonify(HttpRespond.response(HTTP_STATUS.CERR_Bad_Request, "No method specified"))
