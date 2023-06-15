const db = require('./dbConfig');
const { getIndex, getCategory, getIdealWeight } = require('./bmi')
const { getBMR, getCaloriesIntake } = require('./calorie')

function postDashboard(idUser) { 
    db.query(`SELECT * FROM survei WHERE id_user = (${db.escape(idUser)});`, (err, result) => {
        if (err) {
            res.status(500).send({ message: err.sqlMessage });
        } else {
            let idSurvey = parseInt(result[0].id_survei);
            let weight = parseFloat(result[0].weight);
            let height = parseInt(result[0].height);
            let age = parseInt(result[0].age);
            
            const bmiIndex = getIndex(weight, height);
            const bmiCategory = getCategory(bmiIndex);
            const idealWeight = getIdealWeight(height);
            const bmr = getBMR(weight, height, result[0].sex, age);
            const calorieTarget = getCaloriesIntake(bmr, bmiCategory, result[0].activity);
            db.query(`INSERT INTO hasil_survei (id_survei, calorie_target, ideal_weight , bmi, bmi_category) VALUES (${db.escape(idSurvey)}, ${db.escape(calorieTarget)}, ${db.escape(idealWeight)}, ${db.escape(bmiIndex)}, ${db.escape(bmiCategory)});`);
            
        }
    });
}

function updateDashboard(idUser) { 
    db.query(`SELECT * FROM survei WHERE id_user = (${db.escape(idUser)});`, (err, result) => {
        if (err) {
            res.status(500).send({ message: err.sqlMessage });
        } else {
            let idSurvey = parseInt(result[0].id_survei);
            let weight = parseFloat(result[0].weight);
            let height = parseInt(result[0].height);
            let age = parseInt(result[0].age);
            
            const bmiIndex = getIndex(weight, height);
            const bmiCategory = getCategory(bmiIndex);
            const idealWeight = getIdealWeight(height);
            const bmr = getBMR(weight, height, result[0].sex, age);
            const calorieTarget = getCaloriesIntake(bmr, bmiCategory, result[0].activity);
            db.query(`UPDATE hasil_survei SET calorie_target = ${calorieTarget}, ideal_weight = ${idealWeight}, bmi = ${bmiIndex}, bmi_category = ${db.escape(bmiCategory)}  WHERE id_survei = ${db.escape(idSurvey)};`);
        }
    });
}



module.exports = {
    postDashboard,
    updateDashboard
  };