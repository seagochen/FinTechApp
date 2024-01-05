from flask import Blueprint, render_template

bp = Blueprint('bp', __name__)

@bp.route('/')
def index():
    # users = User.query.all()

    users = [
        {"id": 1, "username": "hello kitty1", "email": "test@test.com"},
        {"id": 2, "username": "hello kitty2", "email": "test@test.com"}
    ]
    return render_template('index.html', users=users)