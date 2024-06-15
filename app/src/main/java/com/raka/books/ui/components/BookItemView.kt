package com.raka.books.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.raka.books.R
import com.raka.data.model.BookItem

@Composable
fun BookItemView(
    bookItem: BookItem,
    navigateToDetail: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.padding_medium)
            )
            .background(Color.Gray)
            .clickable { navigateToDetail(bookItem.id) }
    ) {

        val (imageRef, titleRef, descriptionRef) = createRefs()

        AsyncImage(
            error = painterResource(id = R.drawable.ic_no_image),
            model = bookItem.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(dimensionResource(id = R.dimen.image_size))
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clip(CircleShape)
        )

        Text(
            text = bookItem.title,
            color = Color.White,
            fontSize = dimensionResource(id = R.dimen.font_size_large_item).value.sp,
            modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(imageRef.end)
                }
                .padding(
                    top = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_small)
                )
        )

        Text(
            text = bookItem.description,
            overflow = TextOverflow.Ellipsis,
            fontSize = dimensionResource(id = R.dimen.font_size_medium_item).value.sp,
            modifier = Modifier
                .constrainAs(descriptionRef) {
                    top.linkTo(titleRef.bottom)
                    start.linkTo(imageRef.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    start = dimensionResource(id = R.dimen.padding_small),
                    end = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_small),
                    bottom = dimensionResource(id = R.dimen.padding_medium)
                ),
            color = Color.White,
            maxLines = 2
        )
    }
}