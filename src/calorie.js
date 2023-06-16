function getBMR(weight, height, sex, age) { //weight in kg, height in cm, age in year (float, int, int)
    if(typeof(weight) === 'number' && typeof(height) === 'number' && typeof(age) === 'number' && (sex == 'Male' || sex == 'Female')) {
        let bmr;
        switch(sex) {
            //equation ref: https://www.omnicalculator.com/health/bmr-harris-benedict-equation
            case 'Male':
                bmr = 66.5 + (13.75*weight) + (5.003*height) - (6.75*age);
                break;
            case 'Female':
                bmr = 655.1 + (9.563*weight) + (1.85*height) - (4.676*age);
                break;
          }
        return Math.round(bmr);
    }
}

function getCaloriesIntake(bmr, category, activityFactor) { //
    const categoryValue = ['Underweight', 'Healthy Weight', 'Overweight', 'Obese'];
    const activityValue = ['Sedentary', 'Lightly Active', 'Moderately Active', 'Active', 'Very Active'];
    const categoryMatch = categoryValue.some(item => item.toLowerCase() === category.toLowerCase());
    const activityMatch = activityValue.some(item => item.toLowerCase() === activityFactor.toLowerCase());
    if(typeof(bmr) === 'number' && (categoryMatch) && (activityMatch)) {
        let calorie;
        switch(activityFactor.toLowerCase()) {
            //scale factor ref:  https://www.omnicalculator.com/health/bmr-harris-benedict-equation
            case (activityValue[0].toLowerCase()): //Sedentary
                calorie = 1.2*bmr;
                break;
            case (activityValue[1].toLowerCase()): //Lightly active
                calorie = 1.375*bmr;
                break;
            case (activityValue[2].toLowerCase()): //'Moderately active'
                calorie = 1.55*bmr;
                break;
            case (activityValue[3].toLowerCase()): //'Active':
                calorie = 1.725*bmr;
                break;
            case (activityValue[4].toLowerCase()): //'Very active':
                calorie = 1.9*bmr;
                break;
            }

        switch(category.toLowerCase()) {
            //percentage reference: https://www.calculator.net/calorie-calculator.html
            case (categoryValue[0].toLowerCase()): //'Underweight':
                calorie = 1.13*calorie; //Mild weight gain: 113% of normal calorie
                break;
            case (categoryValue[1].toLowerCase()): //'Healthy weight': 
                break;  //Maintain weight: 100% of normal calorie
            case (categoryValue[2].toLowerCase()): //'Overweight':  
                calorie = 0.87*calorie; //Mild weight loss: 87% of normal calorie
                break;
            case (categoryValue[3].toLowerCase()): //'Obese': 
                calorie = 0.74*calorie; //Weight loss: 74% of normal calorie
                break;
            }
        return Math.round(calorie);
    }
}

module.exports = {
    getBMR,
    getCaloriesIntake
  };