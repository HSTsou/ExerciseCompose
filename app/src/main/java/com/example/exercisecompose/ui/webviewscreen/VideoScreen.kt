package com.example.exercisecompose.ui.webviewscreen

import android.util.Log
import android.view.View
import android.webkit.*
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.contentColor
import androidx.ui.layout.Column
import androidx.ui.layout.aspectRatio
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.viewinterop.AndroidView
import com.example.exercisecompose.R
import com.example.exercisecompose.Screen
import com.example.exercisecompose.WebViewRef
import com.example.exercisecompose.data.VideoInfo
import com.example.exercisecompose.navigateTo


@Composable
fun VideoScreen(videoInfo: VideoInfo?) {
//    var showDialog by state { false }
//    if (showDialog) {
////        FunctionalityNotAvailablePopup { showDialog = false }
//    }

    val playing = state { false }

    Scaffold(
            topAppBar = {
                TopAppBar(
                        title = {
                            Text(
                                    text = "Video: ${videoInfo?.title}",
                                    style = MaterialTheme.typography.subtitle2,
                                    color = contentColor()
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navigateTo(Screen.Home) }) {
                                Icon(Icons.Filled.ArrowBack)
                            }
                        }
                )
            },
            bodyContent = { modifier ->
                Column() {
                    Column(modifier = Modifier.aspectRatio(1.78F)) {
                        videoInfo?.id?.let {
                            AndroidWebView(it)
                        }
                    }
                    Button(onClick = {
                        playing.value = !playing.value
                        if (playing.value) {
                            postMessage("playVideo")
                        } else {
                            postMessage("pauseVideo")
                        }
                    }) {
                        Text("playing: ${playing.value}")
                    }
                }
            }
    )
}

@Composable
fun AndroidWebView(url: String) {
    return AndroidView(R.layout.webview) { view ->
        val webView = view as WebView
        WebViewRef.webViewInstance = webView
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.addJavascriptInterface(WebViewInterface(), "ReactNativeWebView")
        webView.loadUrl("https://movie2019.appspot.com/?video_id=" + url)
    }
}

private fun postMessage(method: String) {
    val script = "handleReactNativeMessage({\"data\":\"{\\\"method\\\":\\\"${method}\\\",\\\"payload\\\":\\\"\\\"}\"})"
    WebViewRef.webViewInstance?.evaluateJavascript(script, { call ->
        Log.d("TAG", "fine")
    })
}

private class WebViewInterface {

    @JavascriptInterface
    fun postMessage(msg: String) {
        Log.d("TAG", msg)
        if(msg.contains("onPlayerReady")){
//            playing
        }
    }
}
