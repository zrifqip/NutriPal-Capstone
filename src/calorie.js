
function getBMR(weight, height, sex, age) { //weight in kg, height in cm, age in year (float, int, int)
    if(typeof(weight) === 'number' && typeof(height) === 'number' && typeof(age) === 'number' && (sex == 'Male' || sex == 'Female')) {
        let bmr;
        switch(sex) {
            //equation ref: https://www.omnicalculator.com/health/bmr-harris-benedict-equation
            case 'Male':
                bmr = 66.5 + (13.75*weight) + (5.003*height) - (6.75*age);
                break;
            case 'Female':
                bmr = 655.1 + (9.563*weight) + (1.85*m) - (4.676*age);
                break;
          }
        return Math.round(bmr);
    }
}

function getCaloriesIntake(bmr, category, activityFactor) { //
    if(typeof(bmr) === 'number' && 
    (category == 'Underweight' || category == 'Healthy weight' || category == 'Overweight' || category == 'Obese') &&
    (activityFactor == 'Sedentary' || activityFactor == 'Lightly active' || activityFactor == 'Moderately active' || activityFactor == 'Active') || activityFactor == 'Very active') {
        let calorie;
        switch(activityFactor) {
            //scale factor ref:  https://www.omnicalculator.com/health/bmr-harris-benedict-equation
            case 'Sedentary':
                calorie = 1.2*bmr;
                break;
            case 'Lightly active':
                calorie = 1.375*bmr;
                break;
            case 'Moderately active':
                calorie = 1.55*bmr;
                break;
            case 'Active':
                calorie = 1.725*bmr;
                break;
            case 'Very active':
                calorie = 1.9*bmr;
                break;
            }

        switch(category) {
            //percentage reference: https://www.calculator.net/calorie-calculator.html
            case 'Underweight':
                calorie = 1.13*calorie; //Mild weight gain: 113% of normal calorie
                break;
            case 'Healthy weight': //Maintain weight: 100% of normal calorie
                break;
            case 'Overweight':  //Mild weight loss: 87% of normal calorie
                calorie = 0.87*calorie;
                break;
            case 'Obese': //Weight loss: 74% of normal calorie
                calorie = 0.74*calorie;
                break;
            }
        return Math.round(calorie);
    }
}

module.exports = {
    getBMR,
    getCaloriesIntake
  };