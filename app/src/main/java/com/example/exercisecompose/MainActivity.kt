package com.example.exercisecompose

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.core.content.res.ResourcesCompat
import androidx.ui.animation.Crossfade
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import androidx.ui.viewinterop.AndroidView
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
                                    "https://www.youtube.com/watch?v=sEnELVWQB5M",
                                    "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true"
                                ),
                                VideoInfo(
                                    "7jYDYon4sGQ",
                                    "Last Dance",
                                    "https://zhuanlan.zhihu.com/p/67838438",
                                    "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true"
                                ),
                                VideoInfo(
                                    "Ngyz1gmZzb0",
                                    "秘密 Secret",
                                    "https://www.youtube.com/watch?v=VsStyq4Lzxo",
                                    "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true"
                                ), VideoInfo(
                                    "NYdl3-PxEhQ",
                                    "夏夜晚風 Summer night wind",
                                    "https://www.youtube.com/watch?v=sEnELVWQB5M",
                                    "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true"
                                ),
                                VideoInfo(
                                    "7jYDYon4sGQ",
                                    "Last Dance",
                                    "https://zhuanlan.zhihu.com/p/67838438",
                                    "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true"
                                ),
                                VideoInfo(
                                    "Ngyz1gmZzb0",
                                    "秘密 Secret",
                                    "https://www.youtube.com/watch?v=VsStyq4Lzxo",
                                    "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true"
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
            modifier = Modifier
                .clickable(onClick = {
                    Log.d("HS", "This is ${videoInfo.title}")
                    navigateTo(Screen.VideoScreen(videoInfo))
                })
                .padding(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 8.dp)
                .fillMaxWidth()
                .drawBackground(Color.Cyan)

        ) {
            Image(asset = imageResource(id = R.drawable.placeholder_1_1))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Text(text = videoInfo.title,
                    modifier = Modifier.fillMaxHeight()
                )
//                Text(text = videoInfo.id)
            }
        }
}


@Composable
fun Counter(count: Int, update: (Int) -> Unit) {
    Button(onClick = { update(count + 1) }) {
        Text("I've been clicked $count times")
    }
}

//
//@Composable
//fun renderItems() {
//    val pikaImageUrl = "https://stickershop.line-scdn.net/stickershop/v1/product/12797/LINEStorePC/main.png;compress=true";
////    val image = +image(post.imageThumbUrl) ?: +imageResource(R.drawable.placeholder_1_1)
//
//    Container(width = 40.dp, height = 40.dp) {
//        DrawImage(image)
//    }
//}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExerciseComposeTheme {
        HomeScreen("GGG")
    }
}