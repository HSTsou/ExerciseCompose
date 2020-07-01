package com.example.exercisecompose

import android.webkit.WebView
import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue
import com.example.exercisecompose.data.VideoInfo

sealed class Screen {
    object Home : Screen()
    data class VideoScreen(val videoInfo: VideoInfo) : Screen()
}

object RouteStatus {
    var currentScreen by mutableStateOf<Screen>(Screen.Home)
}


object WebViewRef {
    var webViewInstance: WebView? = null
}


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val exception: Exception) : UiState<Nothing>()
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(destination: Screen) {
    RouteStatus.currentScreen = destination
}
