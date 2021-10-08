package dev.eastar.roomtest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter1
import androidx.compose.material.icons.filled.Filter2
import androidx.compose.material.icons.filled.Filter3
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dev.eastar.roomtest.data.db.Level
import dev.eastar.roomtest.data.db.UserEntity

@Composable
fun MainApp(
    items: LazyPagingItems<UserEntity>,
    onItemClicked: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        @OptIn(ExperimentalFoundationApi::class)
        stickyHeader {
            Button(onClick = { onItemClicked("insert") }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Random insert item"
                )
            }
            Divider()
        }

        items(items) { data ->
            data ?: return@items

            @OptIn(ExperimentalMaterialApi::class)
            ListItem(
                icon = {
                    Box(
                        Modifier
                            .height(70.dp)
                            .width(70.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        @OptIn(ExperimentalCoilApi::class)
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = rememberImagePainter(
                                data.photo ?: "https://picsum.photos/id/101/100/100"
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                        )
                        Icon(
                            data.level.icon,
                            contentDescription = data.level.name,
                            tint = data.level.color,
                        )
                    }
                },
                overlineText = { Text(text = data.date.toString()) },
                text = { Text(text = data.name) },
                secondaryText = { Text(text = data.location ?: "UNKNOWN") },
            )
            Divider()
        }

        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
                textAlign = TextAlign.Center,
                color = Color.White,
                text = "last item"
            )
            Divider()
        }
    }
}


private val Level.icon: ImageVector
    get() = when (this) {
        Level.Level2 -> Icons.Filled.Filter2
        Level.Level3 -> Icons.Filled.Filter3
        else -> Icons.Filled.Filter1
    }

private val Level.color: Color
    get() = when (this) {
        Level.Level2 -> Color.Red.copy(alpha = 0.5F)
        Level.Level3 -> Color.Red.copy(alpha = 0.3F)
        else -> Color.Red.copy(alpha = 1F)
    }


// @SuppressLint("UnrememberedMutableState")
// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//     RoomTestTheme {
//         MainApp(
//             mutableStateOf(listOf(UserEntity.RANDOM, UserEntity.RANDOM, UserEntity.RANDOM))
//         )
//     }
// }
