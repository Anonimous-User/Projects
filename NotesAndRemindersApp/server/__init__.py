import os

from flask import Flask
from flask.helpers import send_from_directory
from flask_cors import CORS, cross_origin


def create_app(config_file="config.py"):
    # Create a Flask-SocketIO server
    app = Flask(__name__)
    app.config.from_pyfile(config_file, silent=True)

    # Enable CORS for all domains on all routes
    CORS(app, resources={r"/*": {"origins": "*"}})

    from .extensions import db
    db.init_app(app)  # init Flask app for use w/ this extension instance

    # Create the table schema in the database
    with app.app_context():
        db.create_all()

    return app