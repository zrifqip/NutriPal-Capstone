package com.submission.nutripal.data



data class FoodResponse(val error: Boolean, val message: String, val recommendationResult: RecommendationResult)

data class RecommendationResult(
    val breakfast: Map<String, FoodItem>,
    val lunch: Map<String, FoodItem>,
    val dinner: Map<String, FoodItem>)

data class FoodItem(
    val food_name: String,
    val calories: Int,
    val unit: Float
    )
data class Food(
    val mealType: String,
    val name: String,
    val portion: String, val totalCalories: Int)


