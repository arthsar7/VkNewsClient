package ru.student.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.student.vknewsclient.ui.theme.MainScreen
import ru.student.vknewsclient.ui.theme.MainViewModel
import ru.student.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun Example1() {
//    ModalNavigationDrawer(drawerContent = {
//
//        Text(text = "1", Modifier.background(Color.White))
//    }) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = {
//                        Text(text = "Top app bar title")
//                    },
//                    colors = TopAppBarDefaults.smallTopAppBarColors(Color.Cyan),
//                    navigationIcon = {
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
//                        }
//                    }
//                )
//            },
//            bottomBar = {
//                NavigationBar {
//                    repeat(3) {
//                        NavigationBarItem(
//                            selected = false,
//                            onClick = { /*TODO*/ },
//                            icon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = null) },
//                            label = { Text(text = "Nigga")}
//                        )
//                    }
//                }
//            }
//        ) {
//            Text(
//                text = "This is scaffold content",
//                modifier = Modifier.padding(it)
//            )
//        }
//    }
//
//}