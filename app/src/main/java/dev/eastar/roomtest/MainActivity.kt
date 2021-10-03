package dev.eastar.roomtest

import android.log.Log
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.roomtest.data.db.UserDao
import dev.eastar.roomtest.data.db.UserEntity
import dev.eastar.roomtest.ui.theme.RoomTestTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
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
                    MainApp(dao.getAllUsers()) {
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
    items: Flow<List<UserEntity>>,
    onItemClicked: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        @OptIn(ExperimentalFoundationApi::class)
        stickyHeader {
            @OptIn(ExperimentalMaterialApi::class)
            ListItem(
                Modifier
                    .background(MaterialTheme.colors.background)
                    .clickable {
                        onItemClicked("insert")
                    }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Random insert item"
                )
            }
            Divider()
        }

        // Add 5 items
        items(10) { index ->
            Text(text = "Item: $index")
            Divider()
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomTestTheme {
        MainApp(flowOf(listOf(UserEntity.RANDOM, UserEntity.RANDOM, UserEntity.RANDOM)))
    }
}
