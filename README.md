# NutriPal-Capstone Authentication API

## Description
This API is a RESTful API developed using Express JS. It is designed to handle authentication processes for the NutriPal mobile app using JWT authentication. The API accepts HTTP request body in either JSON format or encoded URLs.

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
2. Install dependencies (you can see list of dependencies used in package.json
```git 
npm install
```
3. Configure your own database

5.
6. 

## Credit
Ufi Nihayatal Izza Sarinop (c017dsy2780, Bangkit Academy 2023)

## Documentation
 for more details on the available routes and their request/response format, click [Here](https://tinyurl.com/nutripalAuthAPIDocumentation)
