//reference: https://www.cdc.gov/healthyweight/assessing/bmi/adult_bmi/index.html

function getIndex(weight, height) { //weight in kg and height in cm)
    if(typeof(weight) === 'number' && typeof(height) === 'number') {
        let idx = ( 10000 * weight) / (height * height);
        return parseFloat(idx.toFixed(1));
    }
}

function getCategory(index) {
    if (index < 18.5) {
        return 'Underweight';
    } else if (index < 25.0) {
        return 'Healthy weight';
    } else if (index < 30.0) {
        return 'Overweight';
    } else {
        return 'Obese';
    }
}
//2.2 x BMItarget + 3.5 x BMItarget x (Height (m) - 1.5 m)
//ref:https://www.topendsports.com/testing/tests/peterson-equation.htm
function getIdealWeight(height) { //height in cm, BMItarget normally set
    //Peterson Equation (2016) : 2.2 x BMItarget + 3.5 x BMItarget x (Height (m) - 1.5 m)
    //22 is normally set as BMItarget
    if(typeof(height) === 'number') {
        let ideal = 48.4 + (0.77 * (height - 150));
        return parseFloat(ideal.toFixed(1));
    }
}

module.exports = {
    getIndex,
    getCategory,
    getIdealWeight
  };