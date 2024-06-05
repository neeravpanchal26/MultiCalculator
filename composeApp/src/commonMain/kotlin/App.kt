import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import multicalculator.composeapp.generated.resources.Res
import multicalculator.composeapp.generated.resources.compose_multiplatform

import Calculator

@Composable
@Preview
fun App() {
    val calculator = Calculator()

    val addIntResult = calculator.Add(10, 5)
    println("Addition of integers: $addIntResult")

    val subtractResult = calculator.subtract(10.0, 5.0)
    println("Subtraction: $subtractResult")

    val multiplyResult = calculator.multiply(10.0, 5.0)
    println("Multiplication: $multiplyResult")

    val divideResult = calculator.divide(10.0, 5.0)
    println("Division: $divideResult")
}