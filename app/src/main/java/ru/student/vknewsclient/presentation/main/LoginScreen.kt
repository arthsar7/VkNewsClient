package ru.student.vknewsclient.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.student.vknewsclient.R
import ru.student.vknewsclient.ui.theme.VkNewsClientTheme

@Preview
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {}
) {
    VkNewsClientTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vk) ,
                    contentDescription = "VK Icon",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
                Button(onClick = { onLoginClick() }) {
                    Text(text = "Войти")
                }
            }
        }
    }

}