# NutriPal-Capstone Food-ML API

## Description
This API is developed using Flask and deployed on [Google Cloud Run](https://cloud.google.com/run). It is designed to generate meal recommendation using ML model. It accepts HTTP request body only in JSON format.

## How to Install and Run The Project Locally
1. Clone this branch 
```git 
git clone -b food-ml-api https://github.com/zrifqip/NutriPal-Capstone.git
```
2. Create virtual environment
```node
python -m venv dir/venv
```
3. Install dependencies (you can see list of dependencies used in package.json)
```node
pip install -r requirements.txt
```
4. Start local server by executing startup/main script. You can do it by running this command
```python
python -u dir/main.py
```

## How to Deploy The Project
There are many ways to deploy the project to Cloud Run. Here, I demonstrated how to deploy the project using Cloud Shell.
1. Open Cloud Shell Editor in your [GCP console](https://shell.cloud.google.com/)
2. Open terminal and clone the project
```git 
git clone -b food-ml-api https://github.com/zrifqip/NutriPal-Capstone.git
```
3. Change to project's directory in terminal, for example
```cli
cd NutriPal-Capstone
```
4. This repo includes Dockerfile and deploy.sh to help build the container, push the image to container registry, and deploy image to Cloud Run in one go. To make deploy.sh executable, run this command
```cli
chmod u+x deploy.sh
```
5. Deploy to Cloud Run and there, the project is up and running on Cloud Run
```cli
./deploy.sh
```

## Credit
Andi Sekar Ayu Fadilla (M151DSY0570, Bangkit Academy 2023)
Astried Sulastri Madinah (M297DSY0473, Bangkit Academy 2023)
Ufi Nihayatal Izza Sarinop (c017dsy2780, Bangkit Academy 2023)

## Documentation
 For more details on the available routes and their request/response format, click [Here](https://tinyurl.com/nutripalFoodMLAPI)
