package com.example.exercisecompose

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ScrollingView
import androidx.transition.Transition
import androidx.ui.animation.Crossfade
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import androidx.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.exercisecompose.data.VideoInfo
import com.example.exercisecompose.ui.ExerciseComposeTheme
import com.example.exercisecompose.ui.webviewscreen.VideoScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExerciseComposeTheme {
                entry()
            }
        }
    }
}

@Composable
fun entry() {
    Crossfade(RouteStatus.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen("GGG")
                is Screen.VideoScreen -> VideoScreen(screen.videoInfo)
            }
        }
    }
}


@Composable
fun HomeScreen(name: String) {
    val countState = state { 0 }

    MaterialTheme(
        colors = lightColorPalette()
    ) {
        Scaffold(
            topAppBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "this is bar"
                    )
                }
            }
        ) {
            Surface {
                VerticalScroller {
                    Column(
                        horizontalGravity = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hello $name!",
                            style = TextStyle(fontSize = 28f.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color.Gray)
                        Spacer(modifier = Modifier.height(8.dp))

                        Counter(
                            count = countState.value,
                            update = { newValue ->
                                countState.value = newValue
                            }
                        )

                        VideoTiles(
                            listOf<VideoInfo>(
                                VideoInfo(
                                    "NYdl3-PxEhQ",
                                    "夏夜晚風 Summer night wind",
                                    "https://img.youtube.com/vi/NYdl3-PxEhQ/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "7jYDYon4sGQ",
                                    "Last Dance",
                                    "https://img.youtube.com/vi/7jYDYon4sGQ/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "Ngyz1gmZzb0",
                                    "秘密 Secret",
                                    "https://img.youtube.com/vi/Ngyz1gmZzb0/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "NYdl3-PxEhQ",
                                    "夏夜晚風 Summer night wind",
                                    "https://img.youtube.com/vi/NYdl3-PxEhQ/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "7jYDYon4sGQ",
                                    "Last Dance",
                                    "https://img.youtube.com/vi/7jYDYon4sGQ/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "Ngyz1gmZzb0",
                                    "秘密 Secret",
                                    "https://img.youtube.com/vi/Ngyz1gmZzb0/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "NYdl3-PxEhQ",
                                    "夏夜晚風 Summer night wind",
                                    "https://img.youtube.com/vi/NYdl3-PxEhQ/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "7jYDYon4sGQ",
                                    "Last Dance",
                                    "https://img.youtube.com/vi/7jYDYon4sGQ/mqdefault.jpg"
                                ),
                                VideoInfo(
                                    "Ngyz1gmZzb0",
                                    "秘密 Secret",
                                    "https://img.youtube.com/vi/Ngyz1gmZzb0/mqdefault.jpg"
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoTiles(videoInfos: List<VideoInfo>) {
    for (videoInfo in videoInfos)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalGravity = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = {
                    Log.d("HS", "This is ${videoInfo.title}")
                    navigateTo(Screen.VideoScreen(videoInfo))
                })
                .padding(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 8.dp)
                .fillMaxWidth()
                .drawBackground(Color.Cyan)

        ) {
            val loadPictureState = loadPicture(videoInfo.imageUrl)
            if (loadPictureState is UiState.Success<Bitmap>) {
                Card(
                    modifier = Modifier.preferredSize(96.dp),
                    shape = RoundedCornerShape(0),
                    color = Color.LightGray,
                    elevation = 2.dp
                ) {

                    Image(loadPictureState.data.asImageAsset())
                }
            }
//            Image(asset = imageResource(id = R.drawable.placeholder_1_1))
            Column(
                modifier = Modifier.weight(1f),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Text(
                    text = videoInfo.title
                )
            }
        }
}


@Composable
fun Counter(count: Int, update: (Int) -> Unit) {
    Button(onClick = { update(count + 1) }) {
        Text("I've been clicked $count times")
    }
}

@Composable
fun loadPicture(url: String): UiState<Bitmap> {
    var bitmapState: UiState<Bitmap> by state<UiState<Bitmap>> { UiState.Loading }

    Glide.with(ContextAmbient.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Bitmap,
                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
            ) {
                bitmapState = UiState.Success(resource)
            }
        })

    return bitmapState
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExerciseComposeTheme {
        Counter(0, {})
    }
}