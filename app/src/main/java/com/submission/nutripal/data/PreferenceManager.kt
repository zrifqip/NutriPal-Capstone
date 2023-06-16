package com.submission.nutripal.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveLoginResult(loginResult: LoginResult) {
        editor.putInt("id_user", loginResult.id_user)
        editor.putString("name", loginResult.name)
        editor.putString("email", loginResult.email)
        editor.putInt("survey", loginResult.survey)
        editor.putString("last_login", loginResult.last_login)
        editor.putString("token", loginResult.token)
        editor.apply()
    }
    fun saveSurveyResult(surveyResult: SurveyResult){
        editor.putInt("idHasilSurvei", surveyResult.idHasilSurvei)
        editor.putInt("idSurvei", surveyResult.idSurvei)
        editor.putInt("idUser", surveyResult.idUser)
        editor.putString("bmiCategory", surveyResult.bmiCategory)
        editor.putFloat("bmi", surveyResult.bmi)
        editor.putInt("calorieTarget", surveyResult.calorieTarget)
        editor.putFloat("idealWeight", surveyResult.idealWeight)
        editor.apply()
    }
    fun getSurveyResult(): SurveyResult {
        return SurveyResult(
            sharedPreferences.getInt("idHasilSurvei", 0),
            sharedPreferences.getInt("idSurvei", 0),
            sharedPreferences.getInt("idUser", 0),
            sharedPreferences.getString("bmiCategory", "")!!,
            sharedPreferences.getFloat("bmi", 0f),
            sharedPreferences.getInt("calorieTarget", 0),
            sharedPreferences.getFloat("idealWeight", 0f),
        )

    }


    fun getLoginResult(): LoginResult {
        return LoginResult(
            sharedPreferences.getInt("id_user", 0),
            sharedPreferences.getString("name", "")!!,
            sharedPreferences.getString("email", "")!!,
            sharedPreferences.getInt("survey", 0),
            sharedPreferences.getString("last_login", "")!!,
            sharedPreferences.getString("token", "")!!
        )
    }

    fun clearLoginResult() {
        editor.remove("id_user")
        editor.remove("name")
        editor.remove("email")
        editor.remove("survey")
        editor.remove("last_login")
        editor.remove("token")
        editor.apply()
    }
    fun saveSurvey(){
        editor.putInt("survey", 1)
        editor.apply()
    }
}