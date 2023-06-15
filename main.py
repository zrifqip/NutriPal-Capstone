from flask import Flask, request, jsonify
from function import *

app = Flask(__name__)

@app.route('/', methods=['GET'])
def home():
    return "Successful GET Response"

@app.route('/api/predict',methods=['POST'])
def predict():
    body = request.get_json(force=True)
    
    if 'calorie' in body:
        daily_calorie = body['calorie']
        breakfast_recommendation, lunch_recommendation, dinner_recommendation = generate_meal_recommendation(daily_calorie)
        
        result = {
        "breakfast": {f"id{i+1}": (val+1) for i, val in enumerate(breakfast_recommendation)},
        "lunch": {f"id{i+1}": (val+1) for i, val in enumerate(lunch_recommendation)},
        "dinner": {f"id{i+1}": (val+1) for i, val in enumerate(dinner_recommendation)}
        }
        return jsonify(result)
    else:
        return jsonify({'message': 'ERR: No calorie value found in the request body'})

if __name__ == "__main__":
    app.run(debug=True)