from flask import Flask, request, jsonify
from function import *
import json
import requests

app = Flask(__name__)

@app.route('/', methods=['GET'])
def home():
    return "Successful GET Response"

def make_request(url, method, headers, body):
    response = requests.request(method, url, headers=headers, json=body)
    return response

@app.route('/api/predict/<idHasilSurvey>',methods=['POST'])
def predict(idHasilSurvey):
    token = request.headers.get('Authorization')
    body = request.get_json(force=True)

    if token and 'calorie' in body:
        daily_calorie = body['calorie']
        breakfast_recommendation, lunch_recommendation, dinner_recommendation = generate_meal_recommendation(daily_calorie)
        
        result = {
        'breakfast': {f'id{i+1}': (val+1) for i, val in enumerate(breakfast_recommendation)},
        'lunch': {f'id{i+1}': (val+1) for i, val in enumerate(lunch_recommendation)},
        'dinner': {f'id{i+1}': (val+1) for i, val in enumerate(dinner_recommendation)}
        }
        
        request_headers = {"Authorization": token}
        json_body = json.dumps(result, ensure_ascii=False)
        print(result)
        api_url = 'https://nutripal-recommendation-api-gsnjwstg4a-et.a.run.app/api/' + str(idHasilSurvey)
        # Call the function to make the HTTP request
        #response = make_request(api_url, 'POST', request_headers, json = result)
        #print(response)
        response = requests.post(api_url, headers=request_headers, json=result)
        
        if response.status_code == 201:
            return jsonify({
                "error": False,
                "message": "Food recommendation data successfully generated & posted"
            }), 201
        else:
            return jsonify({
                "error": True,
                "message": "Failed to post data to the API"
            }), 500
    else:
        return jsonify({'message': 'ERR: Parameter missing'})

if __name__ == "__main__":
    app.run(debug=True)