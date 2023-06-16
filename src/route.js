const express = require('express');
const router = express.Router();
const { postRecommendation, getRecommendation2 } = require('./functions');
const authorization  = require('./authorization');

router.get("/", (req, res) => {
    console.log("SUCCESSFUL GET RESPONSE")
    return res.status(201).send("Response Success!")
});

// ------------------------------------- FOOD RECOMMENDATION
//get data rekomendasi
router.get("/recommendation/:idHasilSurvey", authorization, async (req, res) => {
    let idHasilSurvey = parseInt(req.params.idHasilSurvey);

    if (!idHasilSurvey || isNaN(idHasilSurvey)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
    }
    
    const totalQueriesResult = await getRecommendation2(idHasilSurvey);
    return res.status(200).send({
        error: false, 
        message: 'Retrieve data success', 
        recommendationResult: totalQueriesResult
    });
});

//post through API call from Flask app
router.post("/api/:idHasilSurvey", authorization, async (req, res) => {
    let idHasilSurvey = parseInt(req.params.idHasilSurvey);

    if (!idHasilSurvey || isNaN(idHasilSurvey)) {
        return res.status(400).send({ message: "Invalid parameter or parameter missing." });
    }

    console.log(req.body);

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
    
    postRecommendation(breakfastBulk, lunchBulk, dinnerBulk);
    return res.status(201).json({
                error: false, 
                message: "Food recommendation data successfully generated & posted"
            }); 
});

module.exports = router;