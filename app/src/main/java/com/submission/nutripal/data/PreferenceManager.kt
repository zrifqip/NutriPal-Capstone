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
    fun saveFillSurvey(surveyResult: SurveyResult){
        editor.putInt("idHasilSurvei", surveyResult.idHasilSurvei)



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
        editor.remove("fill_survey")
        editor.remove("last_login")
        editor.remove("token")
        editor.apply()
    }
    fun saveSurvey(){
        editor.putInt("survey", 1)
        editor.apply()
    }
}