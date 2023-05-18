import json
import re
import bcrypt
from flask import Blueprint, request, session
from server.models import User
from server.extensions import db
from sqlalchemy import exc



auth = Blueprint(
    "auth",
    __name__
)

@auth.route("/auth/register", methods=["POST"])
def on_register():
    data = request.get_json()
    # Fetch event data
    username = data["username"]
    password = data["password"]

    # Validate username
    if not re.match(vp.USERNAME_PATTERN, username):
        print(1)
        return json.dumps({
            "success": False,
            "error_message": "Invalid username."
        })

    # Validate password
    if not re.match(vp.PASSWORD_PATTERN, password):
        print(password)
        return json.dumps({
            "success": False,
            "error_message": "Invalid password"
        })

    password_bytes = bytes(password, 'UTF-8')
    hashpw = bcrypt.hashpw(password_bytes, bcrypt.gensalt(rounds=10))

    try:
        # Create a new user instance
        new_user = User(username=username, password=hashpw)

        # Add the user to the session and commit the changes
        db.session.add(new_user)
        db.session.commit()
        print(new_user)
    except exc.IntegrityError as e:
        return json.dumps({
            "success": False,
            "error_message": "Try again, be more creative",
            "data": {}
        })
    else:
        return json.dumps({
            "success": True,
            "error_message": "",
            "data": {}
        })
        print(f"Client registered: {username}")

def on_login(data=None):
    # Fetch event data
    username = data["username"]
    password = data["password"]

    # Select a user by their username
    user = User.query.filter_by(username=username).first()

    # Check if user doesn't exist
    if user is None or not user.verify_password(password):
        return json.dumps({
            "success": False,
            "error_message": "Try again",
            "data": {}
        })
        return

    # Clear session and add user
    session.clear()
    session[s.USER] = user
    # session.get(s.USER) gets user object

    # Return 200 & user data
    return json.dumps({
        "success": True,
        "error_message": "",
        "data": {
            "user_id": user.id,
            "username": user.username,
        }
    })

def on_logout(data=None):
    user = session.get(s.USER)

    # Clear session
    session.clear()

    # Return 200
    return json.dumps({
        "success": True,
        "error_message": "",
        "data": {}
    }, room=request.sid)

class vp:
    # Regex patterns for username, password, and join code validation
    USERNAME_PATTERN = r"^[a-zA-Z0-9_-]{3,16}$"
    PASSWORD_PATTERN = r"^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$"
    
class s:
    # DB Objects
    USER = "user"

    # Authenticated user
    USER_ID = "user-id"
    USER_NAME = "user-name"