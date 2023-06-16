# NutriPal-Capstone Authentication API

## Description
This API is a RESTful API developed using Express JS and deployed on [Google Cloud Run](https://cloud.google.com/run). It is designed to handle authentication processes for NutriPal mobile app using JWT authentication. It accepts HTTP request body in either JSON format or encoded URLs.

## Usage/Example
You can send http request either using CLI or Postman. Here's the example of sending http request to one of the endpoints
```cli
curl -X POST \
-H "Content-Type: application/json" \
-d '{"email": "alexander.arnold@gmail.com", "password": "trent66"}' \
'https://nutripal-auth-api-gsnjwstg4a-et.a.run.app/auth/signup'
```
and the expected response in JSON
```json
{
    "error": false,
    "msg": "success",
    "loginResult": {
        "id_user": 1,
        "name": "Trent Alexander-Arnold",
        "email": "taa66@gmail.com",
        "survey": 0,
        "lastLogin": null,
        "token": "(header part of JWT).(payload part of JWT).(signature part of JWT)"
    }
}
```

## How to Install and Run The Project Locally
1. Clone this branch 
```git 
git clone -b api https://github.com/zrifqip/NutriPal-Capstone.git
```
2. Install dependencies (you can see list of dependencies used in package.json)
```node
npm install
```
3. Configure your own database in dbConfig.js. If you have no idea the socket path of the database, you can easily replace it with host. If you use XAMPP or phpmyadmin, the configuration probably would look like this (except for the database value). You can also deleteo other setting and leave it as the default
```javascript
const db = mysql.createConnection({
    host: 127.0.0.1,
    user: 'root',
    password: '',
    database: 'xxx'
  });
```
4. Start local server by executing startup/main script. You can do it either by
```node
npm start
```
or
```node
node index.js
```

## How to Deploy The Project
There are many ways to deploy the project to Cloud Run. Here, I demonstrated how to deploy the project using Cloud Shell.
1. Open Cloud Shell Editor in your [GCP console](https://console.cloud.google.com/) page
2. Clone the project
```git 
git clone -b api https://github.com/zrifqip/NutriPal-Capstone.git
```
3. Change to project's directory in terminal, for example
```cli
cd NutriPal-Capstone
```
4. Install packages or dependencies (you can see list of dependencies used in package.json)
```node
npm install
```
5. This repo includes Dockerfile and deploy.sh to help build the container, push the image to container registry, and deploy image to Cloud Run in one go. To make deploy.sh executable, run this command
```cli
chmod u+x deploy.sh
```
6. Deploy to Cloud Run and there, the project is up and running on Cloud Run
```cli
./deploy.sh
```

## Credit
Ufi Nihayatal Izza Sarinop (c017dsy2780, Bangkit Academy 2023)

## Documentation
 for more details on the available routes and their request/response format, click [Here](https://tinyurl.com/nutripalAuthAPIDocumentation)