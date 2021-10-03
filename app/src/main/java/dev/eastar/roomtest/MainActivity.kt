package dev.eastar.roomtest

import android.annotation.SuppressLint
import android.log.Log
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter1
import androidx.compose.material.icons.filled.Filter2
import androidx.compose.material.icons.filled.Filter3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.roomtest.data.db.Level
import dev.eastar.roomtest.data.db.UserDao
import dev.eastar.roomtest.data.db.UserEntity
import dev.eastar.roomtest.ui.theme.RoomTestTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current

            RoomTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val coroutineScope = rememberCoroutineScope()
                    MainApp(
                        dao.getAllUsers().collectAsState(initial = emptyList())
                    ) {
                        coroutineScope.launch {
                            val item = UserEntity.RANDOM
                            val id = dao.insertUser(item)
                            Toast.makeText(context, item.copy(id).toString(), Toast.LENGTH_SHORT).show()


                            val items = dao.getAllUsers()
                            items.collect {
                                it.forEach {
                                    Log.e(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainApp(
    datas: State<List<UserEntity>>,
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

        items(datas.value) { data ->
            @OptIn(ExperimentalMaterialApi::class)
            ListItem(
                icon = {
                    Icon(
                        data.level.icon,
                        contentDescription = data.level.name,
                        tint = data.level.color,
                    )
                },
                overlineText = { Text(text = data.level.name) },
                text = { Text(text = data.name) },
                secondaryText = { Text(text = data.date.toString()) },
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


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomTestTheme {
        MainApp(
            mutableStateOf(listOf(UserEntity.RANDOM, UserEntity.RANDOM, UserEntity.RANDOM))
        )
    }
}
