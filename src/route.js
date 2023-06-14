const db = require('./dbConfig');
const express = require('express');
const router = express.Router();
const { postDashboard,updateDashboard } = require('./dashboard');

router.get("/", (req, res) => {
    console.log("OK")
    res.send("Response Success!")
});

// ------------------------------------- DASHBOARD
//getHasilSurvey atau Dashboard data
router.get("/dashboard/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);

    //Check invalid parameter or parameter missing
    if (!idUser || isNaN(idUser)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
    }

    db.query(`SELECT A.id_hasil_survei, A.id_survei, A.bmi_category, A.bmi, A.calorie_target, A.ideal_weight
    FROM hasil_survei AS A
    JOIN survei AS B ON A.id_survei = B.id_survei
    WHERE B.id_user = (${db.escape(idUser)});`, (err, result) => {
        if(err) {
            return res.status(500).send({message: err.sqlMessage});
        }
        if (!result.length) {
            return res.status(400).send({ message: "No such data exist" });
        } else {
            return res.status(200).send({error: false, message: 'Retrieve data success', dashboardResult: result[0]});
        }
    });
});

// ------------------------------------- SURVEY
//get data Survey
router.get("/survey/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);

    if (!idUser || isNaN(idUser)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
    }

    db.query(`SELECT * FROM survei WHERE id_user = (${db.escape(idUser)});`, (err, result) => {
        if (err) {
            res.status(500).send({ message: err.sqlMessage });
        }
        if (!result.length) {
            return res.status(400).send({ message: "No such data exist" });
        } else {
            return res.status(200).send({error: false, message: 'Retrieve data success', surveyResult: result[0]});
        }
    });
});

//post data survey dan hasil survey
router.post("/survey/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);
    const weight = typeof(req.body.weight) === 'string' ? parseInt(req.body.weight) : req.body.weight;
    const height = typeof(req.body.height) === 'string' ? parseInt(req.body.height) : req.body.height;
    const age = typeof(req.body.age) === 'string' ? parseInt(req.body.age) : req.body.age;
    const smoking = typeof(req.body.smoking) === 'string' ? parseInt(req.body.smoking) : req.body.smoking;
    const alcohol = typeof(req.body.alcohol) === 'string' ? parseInt(req.body.alcohol) : req.body.alcohol;
    const sex = req.body.sex;
    const activity = req.body.activity;

    //Check invalid parameter or parameter missing
    if (!idUser || !weight || !height || !age || !sex || !activity  ) {
        return res.status(400).send({ message: "Parameter missing." });
    }
    if (isNaN(idUser) || isNaN(weight) || isNaN(height) || isNaN(age) || isNaN(smoking) || isNaN(alcohol)) {
        return res.status(400).send({ message: "Invalid parameter." });
    }
    
    //Check if survey data has already existed
    db.query(`SELECT * FROM survei WHERE id_user = ${db.escape(idUser)};`, (err, result) => {
        if (err) {
            //throw err;
            return res.status(400).send({ msg: err });
        }
        //It has already existed
        if (result.length) {
            return res.status(401).send({ msg: 'Survey data has already existed. Try PUT instead' });
        } else {
            db.query(`INSERT INTO survei (id_user, sex, height, weight, age, smoker, activity, alcoholism) VALUES (${idUser}, ${db.escape(sex)}, ${height}, ${weight}, ${age}, ${smoking}, ${db.escape(activity)}, ${alcohol});`);
            postDashboard(idUser);
            return res.status(201).json({
                error: false, 
                message: "New survey data posted & survey result successfully created"
            });
                //res.json(row)
        }        
    });
});

//update data dan Hasil Survey
router.put("/survey/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);
    const weight = typeof(req.body.weight) === 'string' ? parseInt(req.body.weight) : req.body.weight;
    const height = typeof(req.body.height) === 'string' ? parseInt(req.body.height) : req.body.height;
    const age = typeof(req.body.age) === 'string' ? parseInt(req.body.age) : req.body.age;
    const smoking = typeof(req.body.smoking) === 'string' ? parseInt(req.body.smoking) : req.body.smoking;
    const alcohol = typeof(req.body.alcohol) === 'string' ? parseInt(req.body.alcohol) : req.body.alcohol;
    const sex = req.body.sex;
    const activity = req.body.activity;

    //Check invalid parameter or parameter missing
    if (!idUser || !weight || !height || !age || !sex || !activity  ) {
        return res.status(400).send({ message: "Parameter missing." });
    }
    if (isNaN(idUser) || isNaN(weight) || isNaN(height) || isNaN(age) || isNaN(smoking) || isNaN(alcohol)) {
        return res.status(400).send({ message: "Invalid parameter." });
    }

    //Check if survey data has already existed
    db.query(`SELECT * FROM survei WHERE id_user = ${db.escape(idUser)};`, (err, result) => {
        if (err) {
            //throw err;
            return res.status(404).send({ msg: err });
        }
        //It hasn't already existed
        if (!result.length) {
            return res.status(401).send({ msg: 'Survey data has not existed. Try POST instead' });
        }

        db.query(`UPDATE survei SET weight = ${weight}, height = ${height}, age = ${age}, smoker = ${smoking}, alcoholism = ${alcohol}, sex = ${db.escape(sex)}, activity = ${db.escape(activity)}  WHERE id_user = ${db.escape(idUser)};`);
        updateDashboard(idUser);
        return res.status(201).json({
            error: false,
            message: "Survey and dashboard data have been updated"
        });
    });
});

module.exports = router;