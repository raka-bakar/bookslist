package com.raka.books.ui.bookdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.raka.books.R
import com.raka.books.ui.components.BookDetailView
import com.raka.books.ui.components.LoadingView
import com.raka.data.CallResult
import com.raka.data.model.BookDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    callResult: CallResult<BookDetail>,
    onBackClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            title = {},
            colors = TopAppBarDefaults
                .topAppBarColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)
        )

        when (callResult.status) {
            CallResult.Status.SUCCESS -> {
                val bookDetail = callResult.data
                if (bookDetail != null) {
                    BookDetailView(bookDetail)
                }
            }

            CallResult.Status.LOADING -> {
                LoadingView()
            }

            CallResult.Status.ERROR -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(id = R.string.no_data))
                }
            }
        }
    }
}