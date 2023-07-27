package ru.student.vknewsclient.samples

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter

@Preview
@Composable
fun ActivityResultTest() {
    var imageUri by remember {
        mutableStateOf(Uri.EMPTY)
    }

    val contract = ActivityResultContracts.GetContent()

    val launcher = rememberLauncherForActivityResult(
        contract = contract,
        onResult = {
            imageUri = it
        }
    )
    Column(Modifier.fillMaxSize()) {
        Image(
            painter = rememberImagePainter(imageUri),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth().wrapContentHeight().weight(1f)
        ) {
            Text(text = "Launch")
        }
    }
}