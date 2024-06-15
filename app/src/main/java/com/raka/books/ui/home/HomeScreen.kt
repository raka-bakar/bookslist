package com.raka.books.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.raka.books.R
import com.raka.books.ui.components.BookItemView
import com.raka.books.ui.components.EmptyDataView
import com.raka.books.ui.components.LoadingView
import com.raka.data.CallResult
import com.raka.data.model.BookItem

/**
 * Component of Home Screen where it display a list of Books
 * @param callResult result state of the call to get a list of Books
 * @param onRetryButtonClick higher function to handle on retry button clicked
 * @param navigateToDetail higher function to navigate to detail screen
 */
@Composable
fun HomeScreen(
    callResult: CallResult<List<BookItem>>,
    onRetryButtonClick: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        when (callResult.status) {
            CallResult.Status.LOADING -> {
                LoadingView()
            }

            CallResult.Status.ERROR -> {
                Toast.makeText(context, callResult.message, Toast.LENGTH_LONG).show()
                EmptyDataView(
                    message = stringResource(id = R.string.no_data),
                    onButtonClick = onRetryButtonClick
                )
            }

            CallResult.Status.SUCCESS -> {
                val list = callResult.data
                if (list.isNullOrEmpty()) {
                    EmptyDataView(
                        message = stringResource(id = R.string.retry_label),
                        onButtonClick = onRetryButtonClick
                    )
                } else {
                    RecordingListView(list = list, onItemClicked = navigateToDetail)
                }
            }
        }
    }
}

/**
 *  Component that composes All Books Data
 *  @param list data of Books
 *  @param onItemClicked handle for to navigate to detail screen
 */
@Composable
private fun RecordingListView(
    list: List<BookItem>,
    onItemClicked: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .testTag("HomeList")
    ) {
        items(count = list.size, key = { list[it].id }) {
            BookItemView(bookItem = list[it], navigateToDetail = onItemClicked)
        }
    }
}