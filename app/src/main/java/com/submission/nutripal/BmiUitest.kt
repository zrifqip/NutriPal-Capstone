package com.submission.nutripal
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ResultScreen() {

    // Dummy data for illustration, you can replace these with actual data.
    val bmi = "24.5"
    val weight = "70 kg"
    val calorieTarget = "2000 kcal"
    val idealWeight = "65 kg"
    val bmiCategory = "Normal weight"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Health Information",
                fontSize = 24.sp,
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Larger box for Weight and BMI category
            LargeInfoCard(title1 = "Weight", content1 = weight, title2 = "BMI Category", content2 = bmiCategory)

            Spacer(modifier = Modifier.height(16.dp))

            // Remaining details
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // BMI Card
                InfoCard(title = "BMI", content = bmi, painter = painterResource(id = R.drawable.bmi))

                // Ideal Weight Card
                InfoCard(title = "Ideal Weight", content = idealWeight, painter = painterResource(id = R.drawable.weight))

                InfoCard(title = "Calorie Target", content = calorieTarget, painter = painterResource(id = R.drawable.calorie))
            }
        }
    }
}

@Composable
fun InfoCard(title: String, content: String, painter: Painter) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),
        elevation = 4.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = null, // usually a brief description of the image
                modifier = Modifier.size(60.dp) // set image size
            )
            Text(
                text = title,
                fontSize = 20.sp,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = content,
                fontSize = 16.sp,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun LargeInfoCard(title1: String, content1: String, title2: String, content2: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title1,
                fontSize = 20.sp,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = content1,
                fontSize = 16.sp,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content2,
                fontSize = 16.sp,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

