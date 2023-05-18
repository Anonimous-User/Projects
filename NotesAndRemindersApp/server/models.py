import bcrypt
from server.extensions import db

class User(db.Model):
    __tablename__ = "user"

    id = db.Column(db.Integer, nullable=False, unique=True, primary_key=True, autoincrement=True)
    username = db.Column(db.String, nullable=False, unique=True)
    password = db.Column(db.String, nullable=False)
    created_at = db.Column(db.DateTime, nullable=False, default=db.func.now())

    def to_json(self):
        return {column.name: getattr(self, column.name) for column in self.__table__.columns}

    def verify_password(self, password):
        password_bytes = bytes(password, 'UTF-8')

        # Remove the leading '\x' from the string
        hex_string = self.password.replace("\\x", "")

        # Convert the hex string to bytes
        hashed_password = bytes.fromhex(hex_string)

        return bcrypt.checkpw(password_bytes, hashed_password)

    def __repr__(self):
        return f"<User(id={self.id}, username='{self.username}')>"

class Note(db.Model):
    __tablename__ = "note"

    id = db.Column(db.Integer, nullable=False, unique=True, primary_key=True, autoincrement=True)
    message = db.Column(db.String, nullable=False)
    date = db.Column(db.String, nullable=True)
    time = db.Column(db.String, nullable=True)
    # user_id = db.Column(db.Integer, db.ForeignKey("user.id"), nullable=False)
    
    # user = db.relationship(User, foreign_key = [user_id])

    def __repr__(self):
        return f"<Message = {self.message}, date = {self.date}, time = {self.time}>"

    
    def to_json(self):
        return {column.name: getattr(self, column.name) for column in self.__table__.columns}
