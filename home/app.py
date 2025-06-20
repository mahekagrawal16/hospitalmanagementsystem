from flask import Flask, render_template
import subprocess

app = Flask(__name__)

@app.route('/')
def home():
    return render_template('homepage.html')

@app.route('/emergency')
def emergency():
    subprocess.Popen(["python3", "face_recog.py"])
    return "Emergency script triggered!"

if __name__ == '__main__':
    app.run(debug=True)
