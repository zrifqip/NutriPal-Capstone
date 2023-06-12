package com.submission.nutripal.ui.dashboard.FoodRecommendation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.submission.nutripal.Food
import com.submission.nutripal.R

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Halo User, selamat datang di Nutripal", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

        // Include your image here. Replace 'imageResource' with your actual image.
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "Your image",
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
        )

        Text(text = "Berikut Merupakan Hasil Rekomendasi Makanan anda dari survey yang telah diisi", fontSize = 18.sp, textAlign = TextAlign.Center)

        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.weight(1f)) {
            FoodRecommendation()
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { /* Implement Navigation */ }) {
            Text("Other Menu")
        }
    }
}
//add preview for home screen
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}


