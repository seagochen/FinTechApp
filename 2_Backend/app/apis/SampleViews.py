from flask import Blueprint, render_template, abort
from jinja2 import TemplateNotFound

bp = Blueprint('bp', __name__)

simple_page = Blueprint('simple_page', __name__, template_folder='templates')

@simple_page.route('/', defaults={'page': 'index'})
@simple_page.route('/<page>')
def show(page):

    users = [
        {"id": 1, "username": "hello kitty1", "email": "test@test.com"},
        {"id": 2, "username": "hello kitty2", "email": "test@test.com"}
    ]

    try:
        return render_template('index.html', users=users)
        # return render_template(f'pages/{page}.html')
    except TemplateNotFound:
        abort(404)
