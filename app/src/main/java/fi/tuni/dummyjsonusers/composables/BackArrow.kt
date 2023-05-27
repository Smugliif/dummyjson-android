package fi.tuni.dummyjsonusers.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fi.tuni.dummyjsonusers.R

@Composable
fun BackArrow(navController: NavController) {
    OutlinedButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back"
        )
    }
}