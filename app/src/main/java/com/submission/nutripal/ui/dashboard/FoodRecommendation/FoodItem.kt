package com.submission.nutripal.ui.dashboard.FoodRecommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.submission.nutripal.Food

@Composable
fun FoodItem(food: Food, onItemClick: (Food) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onItemClick(food) }
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp)) // Set the corner radius here
            .background(Color.LightGray), // Set the color of your choice here
        contentAlignment = Alignment.Center
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = food.name,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = food.portion,
                    textAlign = TextAlign.Start
                )
            }
            Text(
                text = "${food.totalCalories} kcal",
                textAlign = TextAlign.End,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewFoodItem() {
    FoodItem(food = Food("Breakfast", "Nasi" , "1 piring", 100), onItemClick = {})
}


