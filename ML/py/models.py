# -*- coding: utf-8 -*-
"""model.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1-A8zl6UanuTKzpOH6KXmeQCsCdSZo7a8
"""

import pickle
import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder

dataset = pd.read_csv('food_data.csv', encoding='latin-1')

label_encoder = LabelEncoder()
dataset['food_name'] = label_encoder.fit_transform(dataset['food_name'])
dataset['food_name'] = dataset['food_name'].astype(float)
X = dataset[['food_name','food_weight', 'calories', 'unit']]

y_breakfast = dataset['breakfast']
y_lunch = dataset['lunch']
y_dinner = dataset['dinner']
X_train, X_test, y_train_breakfast, y_test_breakfast, y_train_lunch, y_test_lunch, y_train_dinner, y_test_dinner = train_test_split(X, y_breakfast, y_lunch, y_dinner, test_size=0.25)

breakfast_classifier = DecisionTreeClassifier(criterion='entropy')
lunch_classifier = DecisionTreeClassifier(criterion='entropy')
dinner_classifier = DecisionTreeClassifier(criterion='entropy')

breakfast_classifier.fit(X_train, y_train_breakfast)
lunch_classifier.fit(X_train, y_train_lunch)
dinner_classifier.fit(X_train, y_train_dinner)

breakfast_predictions = breakfast_classifier.predict(X_test)
lunch_predictions = lunch_classifier.predict(X_test)
dinner_predictions = dinner_classifier.predict(X_test)

# Save models using pickle
'''models = {
    'breakfast': breakfast_classifier,
    'lunch': lunch_classifier,
    'dinner': dinner_classifier,
}'''

# Serialize and store them using pickle
#pickle.dump(models, open('models.pkl','wb'))
pickle.dump((breakfast_classifier, lunch_classifier, dinner_classifier, X), open('models.pkl','wb'))

'''print("Breakfast Recommendation:")
print(breakfast_recommendation)

print("Lunch Recommendation:")
print(lunch_recommendation)

print("Dinner Recommendation:")
print(dinner_recommendation)'''