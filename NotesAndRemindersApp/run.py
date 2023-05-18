from server.__init__ import create_app

app = create_app()

if __name__ == '__main__':
    app.run(debug=True)
    


#create new virtual environment
 # python -m venv venv

#start virtual environment
 # .\venv\Scripts\activate

#install all from requiremnts.txt
 # pip install -r requirements.txt

#start server
 # python run.py

#writes all required libraries for the project into requirements.txt
 # pip freeze > .\requirements.txt