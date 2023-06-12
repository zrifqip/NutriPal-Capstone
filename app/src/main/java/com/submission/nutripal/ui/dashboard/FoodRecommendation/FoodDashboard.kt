package com.submission.nutripal.ui.dashboard.FoodRecommendation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.submission.nutripal.Food

@Composable
fun FoodRecommendation() {
    // Mock data
    val breakfastFood = listOf(
        Food("Breakfast", "Eggs", "2 eggs", 140),
        Food("Breakfast", "Toast", "2 slices", 200),
        Food("Breakfast", "Pancakes", "3 pancakes", 600)
    )
    val lunchFood = listOf(
        Food("Lunch", "Sandwich", "1 sandwich", 300),
        Food("Lunch", "Salad", "1 bowl", 150),
        Food("Lunch", "Soup", "1 bowl", 250)
    )
    val dinnerFood = listOf(
        Food("Dinner", "Steak", "200g", 500),
        Food("Dinner", "Fish", "200g", 250),
        Food("Dinner", "Pasta", "1 plate", 400)
    )

    var selectedMeal by remember { mutableStateOf("Breakfast") }
    val foodList = when (selectedMeal) {
        "Breakfast" -> breakfastFood
        "Lunch" -> lunchFood
        "Dinner" -> dinnerFood
        else -> listOf()
    }

    Column {
        DropdownMenu(
            selectedItem = selectedMeal,
            items = listOf("Breakfast", "Lunch", "Dinner"),
            onItemSelected = { selectedMeal = it }
        )

        LazyColumn {
            items(foodList) { foodItem ->
                FoodItem(food = foodItem, onItemClick = { food ->
                    println("Clicked on: $food")
                })
            }
        }


    }
}

@Composable
fun DropdownMenu(
    selectedItem: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationDegree by animateFloatAsState(if (expanded) 180f else 0f)

    Box(modifier = Modifier.padding(8.dp)) {
        OutlinedButton(
            onClick = { expanded = true },
            shape = RectangleShape,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(selectedItem)
                Spacer(Modifier.width(8.dp)) // add some spacing between text and icon
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow Icon",
                    modifier = Modifier.rotate(rotationDegree)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.animateContentSize().padding(top = 8.dp)
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(label)
                        expanded = false
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}
@Preview
@Composable
fun FoodRecommendationDashboardPreview() {
    FoodRecommendation()
}