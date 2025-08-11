package com.adremtest.android.nytimes.ui.screenItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.adremtest.android.R
import com.adremtest.android.nytimes.domain.entity.Article
import com.adremtest.android.nytimes.ui.theme.ColorBlack000000
import com.adremtest.android.nytimes.ui.theme.ColorGray808080

@Composable
fun ItemNyTimes(model: Article) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(model.media?.get(0)?.mediaMetadata?.get(0)?.url)
                .size(200, 200)
                .crossfade(true)
                .build(),
            contentDescription = "",
            placeholder = painterResource(R.drawable.ic_android),
            error = painterResource(R.drawable.ic_android),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .weight(1f),
            text = model.title ?: "",
            style = TextStyle(
                fontSize = 14.sp,
                color = ColorBlack000000
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .weight(1f),
            text = model.abstract ?: "",
            style = TextStyle(
                fontSize = 12.sp,
                color = ColorGray808080
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.End)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Calendar",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = model.abstract ?: "",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = ColorGray808080
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ItemNyTimesPreview() {
    ItemNyTimes(Article())
}