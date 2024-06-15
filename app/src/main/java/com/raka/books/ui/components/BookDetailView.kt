package com.raka.books.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.raka.books.R
import com.raka.data.model.BookDetail

/**
 * Component that shows Book Detail
 * @param bookDetail of type BookDetail
 */
@Composable
fun BookDetailView(bookDetail: BookDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            error = painterResource(id = R.drawable.ic_no_image),
            model = bookDetail.image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(
                    width = dimensionResource(id = R.dimen.detail_image_size),
                    height = dimensionResource(id = R.dimen.detail_image_size)
                )
        )
        Text(
            text = bookDetail.title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium)
                )
        )
        Text(
            text = bookDetail.releaseDate,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_small),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        )
        Text(
            text = bookDetail.description,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium)
                )
        ) {
            val (authorRef, emailRef) = createRefs()
            Text(text = stringResource(id = R.string.author),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(authorRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                    )
            )
            Text(text = bookDetail.author,
                modifier = Modifier
                    .constrainAs(emailRef) {
                        start.linkTo(authorRef.end)
                        top.linkTo(parent.top)
                    }
                    .padding(start = dimensionResource(id = R.dimen.padding_small))
            )
        }

    }
}