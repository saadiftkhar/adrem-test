package com.adremtest.android.nytimes.ui.screenItems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(model.media?.getOrNull(0)?.mediaMetadata?.getOrNull(0)?.url)
                .crossfade(true)
                .build(),
            contentDescription = "Article image",
            placeholder = painterResource(R.drawable.ic_camera),
            error = painterResource(R.drawable.ic_camera),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = model.title.orEmpty(),
            style = TextStyle(fontSize = 16.sp, color = ColorBlack000000),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = model.abstract.orEmpty(),
            style = TextStyle(fontSize = 14.sp, color = ColorGray808080),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "Calendar",
                modifier = Modifier.size(16.dp),
                tint = ColorGray808080
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = model.publishedDate.orEmpty(),
                style = TextStyle(fontSize = 12.sp, color = ColorGray808080),
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