from flask import Flask
# from flask_sqlalchemy import SQLAlchemy

# Register the views
from app import Views


# db = SQLAlchemy()

def create_app(config_filename):

    # Create flask app
    app = Flask(__name__)


    # app.config.from_object(config_filename)

    # db.init_app(app)

    # Register blueprint
    from app.Views import bp
    app.register_blueprint(bp)

    # with app.app_context():
    #     db.create_all()

    return app