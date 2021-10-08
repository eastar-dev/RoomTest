package dev.eastar.roomtest

import android.log.Log
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import dev.eastar.roomtest.data.db.UserEntity
import dev.eastar.roomtest.data.repository.UserRepository
import dev.eastar.roomtest.ui.theme.RoomTestTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            RoomTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val lazyPagingItems = userRepository.getUser("SomeKeyword").collectAsLazyPagingItems()
                    MainApp(
                        lazyPagingItems
                    ) {
                        coroutineScope.launch {
                            val item = UserEntity.RANDOM
                            val ids = userRepository.insertUser(item)
                            ids.forEach {
                                Toast.makeText(context, item.copy(it).toString(), Toast.LENGTH_SHORT).show()
                            }
                            val items = userRepository.getAllUsers()
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
