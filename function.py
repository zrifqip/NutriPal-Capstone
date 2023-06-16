import random
import pickle

# Load models & variable from the pickle file
breakfast_classifier, lunch_classifier, dinner_classifier, X = pickle.load(open('models.pkl', 'rb'))

def generate_meal_recommendation(total_calories):
    breakfast_calories = total_calories * 0.25
    lunch_calories = total_calories * 0.40
    dinner_calories = total_calories * 0.35

    breakfast_candidates = X[(X['calories'] <= breakfast_calories)]
    breakfast_predictions = breakfast_classifier.predict(breakfast_candidates)

    lunch_candidates = X[(X['calories'] <= lunch_calories)]
    lunch_predictions = lunch_classifier.predict(lunch_candidates)

    dinner_candidates = X[(X['calories'] <= dinner_calories)]
    dinner_predictions = dinner_classifier.predict(dinner_candidates)

    breakfast_recommendation = random.sample(list(breakfast_candidates.index[breakfast_predictions == 1]), min(5, len(breakfast_candidates)))
    lunch_recommendation = random.sample(list(lunch_candidates.index[lunch_predictions == 1]), min(5, len(lunch_candidates)))
    dinner_recommendation = random.sample(list(dinner_candidates.index[dinner_predictions == 1]), min(5, len(dinner_candidates)))
    
    return breakfast_recommendation, lunch_recommendation, dinner_recommendation