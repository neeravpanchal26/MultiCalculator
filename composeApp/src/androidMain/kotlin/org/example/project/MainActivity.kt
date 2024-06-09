package org.example.project

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                CalcView()
            }
        }
    }
}

@Composable
fun CalcView() {
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    content()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        CalcView()
    }
}