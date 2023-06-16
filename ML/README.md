# Food and BMI Documentation

This repository contains two different models for food recommendation and BMI calculation. Each model is explained in its respective folder.

## Food Recommendation

The food recommendation model is a machine learning model trained using a decision tree classifier. It provides recommendations for meals based on specific dietary requirements. The model uses the following libraries and methods:

- Libraries:
  - pandas (imported as `pd`)
  - random
  - sklearn.tree (imported as `DecisionTreeClassifier`)
  - sklearn.metrics (imported as `accuracy_score`, `confusion_matrix`, `precision_score`, `recall_score`)
  - sklearn.model_selection (imported as `train_test_split`)
  - google.colab (imported as `files`)

- Methods:
  - Data exploration and preprocessing
  - Training and evaluating the decision tree classifiers for breakfast, lunch, and dinner
  - Generating diet recommendations based on total calories and test data
  - Calculating accuracy, precision, recall, and confusion matrices for each meal type

## BMI Calculation

The BMI calculation model is a deep learning model that predicts obesity levels based on weight and height data. It uses the following libraries, pre-trained models, and methods:

- Libraries:
  - numpy (imported as `np`)
  - pandas (imported as `pd`)
  - sklearn.model_selection (imported as `train_test_split`)
  - sklearn.preprocessing (imported as `StandardScaler`, `ColumnTransformer`, `OneHotEncoder`)
  - tensorflow (imported as `tf`)
  - google.colab (imported as `files`)

- Methods:
  - Data preprocessing, including BMI calculation
  - Feature scaling using StandardScaler
  - One-hot encoding of categorical columns
  - Neural network model creation and compilation
  - Training and evaluation of the model on the BMI dataset
  - Saving and downloading the trained model for future use

Please refer to each model's respective folder for more detailed information, code implementation, and usage instructions.
