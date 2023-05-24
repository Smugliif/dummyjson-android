package fi.tuni.dummyjsonusers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Header(displayText: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = displayText,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(all = 20.dp)
        )
    }
}

@Composable
fun LoadingScreen() {
    Column(Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}



@Composable
fun BackArrow(navController: NavController) {
    OutlinedButton(
        onClick = { navController.navigate("userList") },
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back"
        )
    }
}
