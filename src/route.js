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
router.get("/dashboard/:idSurvey", (req, res) => {
    let idSurvey = parseInt(req.params.idSurvey);

    //Check invalid parameter or parameter missing
    if (!idSurvey || isNaN(idSurvey)) {
        return res.status(400).send({message: "Invalid parameter or parameter missing."});
    }

    db.query(`SELECT * FROM hasil_survei WHERE id = (${db.escape(idSurvey)});`, (err, row) => {
        if(err) {
            res.status(500).send({message: err.sqlMessage})
        } else {
            res.json(row)
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
        } else {
            res.json(result);
        }
    });
});

//post data survey dan hasil survey
router.post("/survey/:idUser", (req, res) => {
    let idUser = parseInt(req.params.idUser);
    const weight = req.body.weight;
    const height = req.body.height;
    const age = req.body.age;
    const smoking = req.body.smoking;
    const alcohol = req.body.alcohol;
    const sex = req.body.sex;
    const activity = req.body.activity;

    //Check invalid parameter or parameter missing
    if (!idUser || isNaN(idUser)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
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
    let empty = (!idUser);
    const weight = req.body.weight;
    const height = req.body.height;
    const age = req.body.age;
    const smoking = req.body.smoking;
    const alcohol = req.body.alcohol;
    const sex = req.body.sex;
    const activity = req.body.activity;

    //Check invalid parameter
    if (!idUser || isNaN(idUser)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
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
            message: "Survey and dashboard datas have been updated"
        });
    });
});

module.exports = router;