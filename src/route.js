const db  = require('./dbConfig');
const express = require('express');
const router = express.Router()
const { getIndex, getCategory, getIdealWeight } = require('./bmi')
const { getBMR, getCaloriesIntake } = require('./calorie')

router.get("/:id", (req, res) => {
    let id = parseInt(req.params.id);
    console.log("Response success: %d", id)
    res.send("Response Success!")
});

//getHasilSurvey
router.get("/dashboard/:id", (req, res) => {
    let id = parseInt(req.params.id);

    if (!id) {
        return res.status(400).send({message: "Parameter missing."})
    }

    db.query(`SELECT * FROM hasil_survei WHERE id = (${db.escape(req.params.id)});`, (err, row) => {
        if(err) {
            res.status(500).send({message: err.sqlMessage})
        } else {
            res.json(row)
        }
    });
});

//postHasilSurvey
router.post("/dashboard", (req, res) => {
    const id = req.body.id; //ID Survey
    const weight = req.body.weight;
    const height = req.body.height;
    const age = req.body.age;
    const sex = req.body.sex;
    const activityFactor = req.body.activityFactor;

    //Check invalid parameter or parameter missing
    if ((!id) || (!weight)  || (!height) || (!age)  || (!sex) || (!activityFactor)  || isNaN(weight) || isNaN(height) || isNaN(age)) {
        return res.status(400).send({message: "Invalid parameter or parameter missing."});
    }

    //Calculate BMI and insert them into database
    const bmiIndex = getIndex(weight, height);
    const bmiCategory = getCategory(bmiIndex);
    const idealWeight = getIdealWeight(height);
    const bmr = getBMR(weight, height, sex, age);
    const calorieTarget = getCaloriesIntake(bmr, bmiCategory, activityFactor);
    db.query(`INSERT INTO hasil_survei (id_survei, calorie_target , ideal_weight , bmi, bmi_category ) VALUES (${db.escape(req.body.id)}, ${db.escape(calorieTarget)}, ${db.escape(idealWeight)}, ${db.escape(bmiIndex)}, ${db.escape(bmiCategory)});`,(err, result) => {
        if (err) {
            throw err;
            return res.status(400).send({msg: err});
        }
        return res.status(201).json({error: false, message: "Survey Result Created"});
    });
});

//update HasilSurvey
router.put("/dashboard/:id", (req, res) => {
    let id = parseInt(req.params.id);

    const idSurvey = req.body.idSurvey; //ID Survey
    const weight = req.body.weight;
    const height = req.body.height;
    const age = req.body.age;
    const sex = req.body.sex;
    const activityFactor = req.body.activityFactor;

    //Check invalid parameter or parameter missing
    if ((!id) || (!id) || (!weight)  || (!height) || (!age)  || (!sex) || (!activityFactor)  || isNaN(weight) || isNaN(height) || isNaN(age)) {
        return res.status(400).send({message: "Invalid parameter or parameter missing."});
    }

    const bmiIndex = getIndex(weight, height);
    const bmiCategory = getCategory(bmiIndex);
    
    db.query(`UPDATE hasil_survei SET weight = ${weight}, height = ${height}, bmiIndex = ${bmiIndex}, bmiCategory = ${db.escape(bmiCategory)}  WHERE id = ${db.escape(req.body.id)};`,(err, result) => {
        if (err) {
            throw err;
            res.status(500).send({message: err.sqlMessage});
        }
        //return res.status(201).json({error: false, message: "Survey Result Created"});
        res.send({message: "Update Successful"})
            //res.json(row)
    });
});

router.get("/survey/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);
    console.log("User ID : ", idUser, typeof(idUser));

    if (!idUser) {
        return res.status(400).send({message: "Parameter missing."});
    }

    db.query(`SELECT * FROM survei WHERE id_user = (${db.escape(idUser)});`, (err, row) => {
        if(err) {
            res.status(500).send({message: err.sqlMessage})
        } else {
            res.json(row)
        }
    });
});

//post data survey
router.post("/survey/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);
    const weight = req.body.weight;
    const height = req.body.height;
    const age = req.body.age;
    const smoking = req.body.smoking;
    const alcohol = req.body.alcohol;
    const sex = req.body.sex;
    const activity = req.body.activity;

    console.log("User ID : ", idUser, typeof(idUser));
    console.log("weight : ", weight, typeof(weight));
    console.log("height: ", height, typeof(height));
    console.log("age : ", age, typeof(age));
    console.log("smoking : ", smoking, typeof(smoking));
    console.log("alcohol : ", alcohol, typeof(alcohol));
    console.log("sex : ", sex, typeof(sex));
    console.log("activity : ", activity, typeof(activity));

    //Check invalid parameter or parameter missing
    if (isNaN(weight) || isNaN(height) || isNaN(age)) {
        return res.status(400).send({message: "Invalid parameter or parameter missing."});
    }

    db.query(`INSERT INTO survei (id_user, sex, height, weight, age, smoker, activity, alcoholism) VALUES (${idUser}, ${db.escape(sex)}, ${height}, ${weight}, ${age}, ${smoking}, ${db.escape(activity)}, ${alcohol});`,(err, row) => {
        if (err) {
            //throw err;
            return res.status(400).send({msg: err});
        }
        return res.status(201).json({error: false, message: "Survey Result Created"});
        //res.json(row)
    });
});

//update HasilSurvey
router.put("/survey/:userID", (req, res) => {
    let idUser = parseInt(req.params.userID);
    const weight = req.body.weight;
    const height = req.body.height;
    const age = req.body.age;
    const smoking = req.body.smoking;
    const alcohol = req.body.alcohol;

    //Check invalid parameter or parameter missing
    if ((!idUser) || (!weight)  || (!height) || (!age)  || (!req.body.sex) || (!req.body.activity) || (!smoking) || (!alcohol) || isNaN(weight) || isNaN(height) || isNaN(age)) {
        return res.status(400).send({message: "Invalid parameter or parameter missing."});
    }
    
    db.query(`UPDATE survei SET weight = ${weight}, height = ${height}, age = ${age}, smoker = ${smoking}, alcoholism = ${alcohol}, sex = ${db.escape(req.body.sex)}, activity = ${db.escape(req.body.activityFactor)}  WHERE id_user = ${db.escape(idUser)};`,(err, row) => {
        if (err) {
            throw err;
            res.status(500).send({message: err.sqlMessage});
        }
        //return res.status(201).json({error: false, message: "Survey Result Created"});
        res.send({message: "Update Successful"})
        //res.json(row)
    });
});

module.exports = router;