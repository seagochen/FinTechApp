# -*- coding: utf-8 -*-
# Author: Orlando Chen
# Create: Sep 19, 2018
# Modifi: Oct 01, 2018

from flask import request


def _load_from_stream(fs):
    """
    Args:
    * [fs] file storage, typeof werkzeug.datastructures.FileStorage
    """
    data = fs.read()
    return data


def get_request_params(request):
    params = {}

    # adding request method
    if request.method == 'POST':
        params['http_request'] = 'POST'
    elif request.method == 'GET':
        params['http_request'] = 'GET'
    elif request.method == 'HEAD':
        params['http_request'] = 'HEAD'
    elif request.method == 'PUT':
        params['http_request'] = 'PUT'
    elif request.method == 'DELETE':
        params['http_request'] = 'DELETE'
    elif request.method == 'TRACE':
        params['http_request'] = 'TRACE'
    elif request.method == 'CONNECT':
        params['http_request'] = 'CONNECT'
    else:
        params['http_request'] = 'UNKNOW'
        return params

    # adding request parameters
    if len(request.args) > 0:
        get_args = {}
        for key, val in request.args.items():
            get_args[key] = val
        params['args'] = get_args
    else:
        params['args'] = None

    if len(request.form) > 0:
        form_args = {}
        for key, val in request.form.items():
            form_args[key] = val
        params['form'] = form_args
    else:
        params['form'] = None

    # adding files to redis
    if len(request.files) > 0:
        params['files'] = request.files
    else:
        params['files'] = None

    return params


def get_remote_ip(request):
    return request.remote_addr


def simplify_variables(oldfmt):
    """
    To simplify the variables that user sent to Menca, and to decrease
    the difficulty of maintenance and development.
    Although APIs can be simplified, there is a probelm of parameters being
    overwritten when the GET and POST requests have parameters of the same name.
    Args:
    * [oldfmt] dict
    Returns:
    * dict, simpilified parameters
    """

    if None is oldfmt or len(oldfmt) <= 0:
        return None

    res = {}
    if 'args' in oldfmt:
        for key, val in oldfmt['args'].items():
            print(key, val)
            res[key] = val

    if 'form' in oldfmt:
        for key, val in oldfmt['form'].items():
            print(key, val)
            res[key] = val

    if 'files' in oldfmt:
        res['files'] = oldfmt['files']

    res['http_request'] = oldfmt['http_request']

    return res
