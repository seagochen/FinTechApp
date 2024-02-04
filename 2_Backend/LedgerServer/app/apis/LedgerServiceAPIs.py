from flask import Blueprint, request, jsonify

from app import HttpRespond
from app.HttpRespond import HTTP_STATUS
from app import RequestParams


ledger_service = Blueprint('ledger_service', __name__, url_prefix='/ledger_service')


@ledger_service.route('/setup', methods=['GET'])
def setup():
    # Processing GET request
    params = RequestParams.get_request_params(request)

    # If args is not None, then get the args
    if params['args'] is not None:
        if hasattr(params['args'], 'method') and params['args']['method'] == 'create':
            # Download the database files to create the local database
            pass
        elif hasattr(params['args'], 'method') and params['args']['method'] == 'update':
            # Update the database
            pass
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
