package com.example.pratica_estado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pratica_estado.ui.theme.PraticaestadoTheme

data class Artwork(
    val imageResId: Int,
    val title: String,
    val artist: String,
    val year: String
)

val artworks = listOf(
    Artwork(R.drawable.starrynight, "Starry Night", "Vincent van Gogh", "1889"),
    Artwork(R.drawable.monalisa, "Mona Lisa", "Leonardo da Vinci", "1503"),
    Artwork(R.drawable.thescream, "The Scream", "Edvard Munch", "1893")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraticaestadoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtGalleryApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtGalleryApp(modifier: Modifier = Modifier) {
    var currentArtworkIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ArtWall(artworks[currentArtworkIndex].imageResId)
        Spacer(modifier = Modifier.height(16.dp))
        ArtDescriptor(
            title = artworks[currentArtworkIndex].title,
            artist = artworks[currentArtworkIndex].artist,
            year = artworks[currentArtworkIndex].year
        )
        Spacer(modifier = Modifier.height(16.dp))
        ArtController(
            onNext = {
                currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size
            },
            onPrevious = {
                currentArtworkIndex = (currentArtworkIndex - 1 + artworks.size) % artworks.size
            }
        )
    }
}

@Composable
fun ArtWall(imageResId: Int) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Artwork",
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@Composable
fun ArtDescriptor(title: String, artist: String, year: String) {
    Column {
        Text(text = "Title: $title")
        Text(text = "Artist: $artist")
        Text(text = "Year: $year")
    }
}

@Composable
fun ArtController(onNext: () -> Unit, onPrevious: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = onPrevious) {
            Text(text = "Previous")
        }
        Button(onClick = onNext) {
            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryAppPreview() {
    PraticaestadoTheme {
        ArtGalleryApp()
    }
}
