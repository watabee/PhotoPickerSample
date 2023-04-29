package com.github.watabee.photopickersample

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.watabee.photopickersample.ui.theme.PhotoPickerSampleTheme

class MainActivity : ComponentActivity() {

    private val selectedImageUris = mutableStateListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
            selectedImageUris.clear()
            selectedImageUris.addAll(uris)
        }
        setContent {
            PhotoPickerSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageViewer(uris = selectedImageUris) {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageViewer(uris: List<Uri>, onPickImagesButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (uris.isEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                text = "No image selected"
            )
        } else {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                pageCount = uris.size
            ) { index ->
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = uris[index],
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
            }
        }

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onPickImagesButtonClicked
        ) {
            Text(text = "Pick images")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoPickerSampleTheme {
    }
}