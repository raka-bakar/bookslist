package com.raka.books.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.raka.books.R

/**
 * Component that shows Error message view
 * @param message error message of type String
 */
@Composable
fun EmptyDataView(
    message: String,
    onButtonClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            fontSize = dimensionResource(id = R.dimen.font_size_large_item).value.sp
        )
        Button(onClick = onButtonClick) {
            Text(text = stringResource(id = R.string.retry_label))
        }
    }


}