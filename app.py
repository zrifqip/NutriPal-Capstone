from flask import Flask, request, jsonify
import jwt
from function import *

app = Flask(__name__)

def authorization(func):
    def wrapper(*args, **kwargs):
        token = request.headers.get('Authorization')
        if not token:
            return jsonify("AUTHORIZATION ERROR!!!"), 401

        try:
            payload = jwt.decode(token, 'my-32-character-ultra-secure-and-ultra-long-secret')
            return func(*args, **kwargs)
        except jwt.DecodeError:
            return jsonify("AUTHORIZATION FAILED!!!"), 401
    return wrapper

@app.route('/', methods=['GET'])
def home():
    return "Successful GET Response"

@app.route('api/predict',methods=['POST'])
@authorization
def predict():
    body = request.get_json(force=True)
    
    if 'calorie' in body:
        daily_calorie = body['calorie']
        breakfast_recommendation, lunch_recommendation, dinner_recommendation = generate_meal_recommendation(daily_calorie)
        
        result = {
        "breakfast": {f"id{i+1}": val for i, val in enumerate(breakfast_recommendation)},
        "lunch": {f"id{i+1}": val for i, val in enumerate(lunch_recommendation)},
        "dinner": {f"id{i+1}": val for i, val in enumerate(dinner_recommendation)}
        }
        return jsonify(result)
    else:
        return jsonify({'message': 'ERR: No calorie value found in the request body'})

if __name__ == "__main__":
    app.run(debug=True)