const db = require('./dbConfig');
const express = require('express');
const router = express.Router();
const { postRecommendation, deleteRecommendation, getRecommendation } = require('./functions');
const authorization  = require('./authorization');

// ------------------------------------- FOOD RECOMMENDATION
//get data rekomendasi
router.get("/recommendation/:idHasilSurvey", authorization, (req, res) => {
    let idHasilSurvey = parseInt(req.params.idHasilSurvey);

    if (!idHasilSurvey || isNaN(idHasilSurvey)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
    }
    
    const result = getRecommendation(idHasilSurvey);
    return res.status(200).send({
        error: false, 
        message: 'Retrieve data success', 
        recommendationResult: result
    });
});

//post & update through API call from Flask app
router.post("/api/:idHasilSurvey", (req, res, next) => {
    let idHasilSurvey = parseInt(req.params.idHasilSurvey);

    if (!idHasilSurvey || isNaN(idHasilSurvey)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
    }

    const breakfastIds = Object.values(req.body.breakfast);
    const lunchIds = Object.values(req.body.lunch);
    const dinnerIds = Object.values(req.body.dinner);

    // Create an array to store the query values for each row
    const breakfastBulk = [];
    const lunchBulk = [];
    const dinnerBulk = [];
    // Generate the query values for each food ID and survey ID
    for (const breakfastId of breakfastIds) {
        breakfastBulk.push([breakfastId, idHasilSurvey, 'breakfast']);
    }
    for (const lunchId of lunchIds) {
        lunchBulk.push([lunchId, idHasilSurvey, 'lunch']);
    }
    for (const dinnerId of dinnerIds) {
        dinnerBulk.push([dinnerId, idHasilSurvey, 'dinner']);
    }

    db.query(`SELECT * FROM food_recommendation WHERE id_hasil_survei = (${db.escape(idHasilSurvey)});`, (err, results) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        }
        if (result.length) {    //Data already exist
            deleteRecommendation(idHasilSurvey);
            postRecommendation(breakfastBulk, lunchBulk, dinnerBulk);
            return res.status(201).json({
                error: false,
                message: "Food recommendation data successfully generated & updated"
            });
            
        } else {
            postRecommendation(breakfastBulk, lunchBulk, dinnerBulk);
            return res.status(201).json({
                error: false, 
                message: "Food recommendation data successfully generated & posted"
            });
        }
    });
});

module.exports = router;