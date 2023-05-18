import os
from os import environ

SECRET_KEY = environ.get("SECRET_KEY")
basedir = os.path.abspath(os.path.dirname(__file__))
SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(basedir, 'database.db')
# SQLALCHEMY_BINDS = False
SQLALCHEMY_ECHO = False
SQLALCHEMY_TRACK_MODIFICATIONS = False