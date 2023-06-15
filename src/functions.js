const db = require('./dbConfig');

function postRecommendation(breakfastBulk, lunchBulk, dinnerBulk) { 
    const query1 = 'INSERT INTO food_recommendation (id_food, id_hasil_survei, time) VALUES ?';
    db.query(query1, [breakfastBulk], (err, result) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    });
    const query2 = 'INSERT INTO food_recommendation (id_food, id_hasil_survei, time) VALUES ?';
    db.query(query2, [lunchBulk], (err, result) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    });
    const query3 = 'INSERT INTO food_recommendation (id_food, id_hasil_survei, time) VALUES ?';
    db.query(query3, [dinnerBulk], (err, result) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    });
}

function deleteRecommendation(idHasilSurvey) { 
    const deleteQuery = 'DELETE FROM food_recommendation WHERE id_hasil_survei = ?';
    db.query(deleteQuery, [idHasilSurvey], (err, result) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    }); 
}

function getRecommendation(idHasilSurvey) { 
    const result = {};
    const query = "SELECT A.food_name, A.calories, A.unit FROM new_food AS A JOIN food_recommendation AS B ON A.id_food = B.id_food WHERE B.id_hasil_survey = ? AND B.time = ?";

    db.query(query, [idHasilSurvey, 'breakfast'], (err, breakfastResult) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
        result.breakfast = breakfastResult;
    });

    db.query(query, [idHasilSurvey, 'lunch'], (err, lunchResult) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
        result.lunch = lunchResult;
    });
    
    db.query(query, [idHasilSurvey, 'dinner'], (err, dinnerResult) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
        result.dinner = dinnerResult;
    });
    return result;
}


module.exports = {
    postRecommendation,
    deleteRecommendation,
    getRecommendation
  };