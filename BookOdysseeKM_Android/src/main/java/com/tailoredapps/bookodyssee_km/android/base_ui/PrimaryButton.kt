package com.tailoredapps.bookodyssee_km.android.base_ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    btnText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    btnColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
) {
    Button(
        colors = btnColors,
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        onClick = onClick
    ) {
        Text(
            text = btnText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}
