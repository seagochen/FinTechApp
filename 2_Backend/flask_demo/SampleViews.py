from flask import Blueprint, render_template, abort
from jinja2 import TemplateNotFound
from flask import Flask

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


if __name__ == '__main__':

    # Create flask app
    app = Flask(__name__)

    # Register blueprint
    app.register_blueprint(simple_page)

    # Register blueprint
    app.run(debug=True, host='127.0.0.1', port=3000)