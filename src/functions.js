const db = require('./dbConfig');
const util = require('util');

function postRecommendation(breakfastBulk, lunchBulk, dinnerBulk) { 
    const query1 = 'INSERT INTO food_recommendation (id_food, id_hasil_survei, time) VALUES ?';
    db.query(query1, [breakfastBulk], (err) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    });
    const query2 = 'INSERT INTO food_recommendation (id_food, id_hasil_survei, time) VALUES ?';
    db.query(query2, [lunchBulk], (err) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    });
    const query3 = 'INSERT INTO food_recommendation (id_food, id_hasil_survei, time) VALUES ?';
    db.query(query3, [dinnerBulk], (err) => {
        if (err) {
            return res.status(500).send({ message: err.sqlMessage });
        } 
    });
}

async function getRecommendation2(idHasilSurvey){
    const queryPromisify = util.promisify(db.query).bind(db);
    try {
        const query = 'SELECT A.food_name, A.calories, A.unit FROM new_food AS A JOIN food_recommendation AS B ON A.id_food = B.id_food WHERE B.id_hasil_survei = ? AND B.time = ? ORDER BY createdAt DESC LIMIT 5';
        
        const breakfastQuery = await queryPromisify(query, [idHasilSurvey, 'breakfast']);
        const lunchQuery = await queryPromisify(query, [idHasilSurvey, 'lunch']);
        const dinnerQuery = await queryPromisify(query, [idHasilSurvey, 'dinner']);
        const [breakfastResult, lunchResult, dinnerResult] = ([breakfastQuery, lunchQuery, dinnerQuery]);
        

        const menuData = {
            breakfast: {no1: breakfastResult[0], no2: breakfastResult[1], no3: breakfastResult[2], no4: breakfastResult[3], no5: breakfastResult[4]
            },
            lunch: {no1: lunchResult[0], no2: lunchResult[1], no3: lunchResult[2], no4: lunchResult[3], no5: lunchResult[4]
            },
            dinner: {no1: dinnerResult[0], no2: dinnerResult[1], no3: dinnerResult[2], no4: dinnerQuery[3], no5: dinnerResult[4]
            }
          };
          return menuData;
      } catch (error) {
        console.error('Error fetching user data:', error);
        throw error;
      }
  }

module.exports = {
    postRecommendation,
    getRecommendation2
  };