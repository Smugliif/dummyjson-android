package fi.tuni.dummyjsonusers.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Loading screen displaying a centered CircularProgressIndicator Composable.
 *
 */
@Composable
fun LoadingScreen() {
    Column(Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

