package kz.abylay.mycomposetutorialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import kz.abylay.mycomposetutorialapp.ui.theme.MyComposeTutorialAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTutorialAppTheme {
                var scale by remember {
                    mutableStateOf(1f)
                }
                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1280f / 959f)
                ) {
                    val state = rememberTransformableState { zoomChange, panChange, _ ->
                        scale = (scale * zoomChange).coerceIn(1f, 7f)

                        val extraWidth = (scale - 1) * constraints.maxWidth
                        val extraHeight = (scale - 1) * constraints.maxHeight

                        val maxX = extraWidth / 2
                        val maxY = extraHeight / 2

                        offset = Offset(
                            x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY)
                        )
                    }
                    Image(
                        painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/philipplackner/ComposePinchZoomRotate/master/app/src/main/res/drawable/kermit.jpg"),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .transformable(state)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeTutorialAppTheme {
        Greeting("Android")
    }
}