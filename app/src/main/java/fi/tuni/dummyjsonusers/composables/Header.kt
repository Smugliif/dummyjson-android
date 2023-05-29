package fi.tuni.dummyjsonusers.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Simple header using a Column and Text Composable that display given String.
 *
 * @param displayText String that will be displayed.
 */
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