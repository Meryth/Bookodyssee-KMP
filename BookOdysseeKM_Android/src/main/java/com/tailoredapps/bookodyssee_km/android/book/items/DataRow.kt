package com.tailoredapps.bookodyssee_km.android.book.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DataRow(
    field: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = field,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = value)
    }
}

@Composable
fun DataRow(
    field: String,
    value: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = field,
            color = MaterialTheme.colorScheme.primary
        )

        var fullValueList = ""

        value.forEach { value ->
            fullValueList += if (fullValueList.isEmpty()) {
                value
            } else {
                ", $value"
            }
        }
        Text(text = fullValueList)
    }
}

@Preview(showBackground = true)
@Composable
fun DataRowPreview() {
    DataRow(field = "Author", value = "Bob")
}

@Preview(showBackground = true)
@Composable
fun DataRowListPreview() {
    DataRow(field = "Author", value = listOf("Alice", "Bob", "Charles"))
}
