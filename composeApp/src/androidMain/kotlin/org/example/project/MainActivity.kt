package org.example.project

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.saveable.rememberSaveable

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
    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }
    var answer by remember { mutableStateOf(0) }
    val display = remember { mutableStateOf("0") }

    if (complete && operation != "") {
        leftNumber = 0
        rightNumber = 0
        operation = ""
        complete = false
        when (operation) {
            "+" -> answer = leftNumber + rightNumber
            "-" -> answer = leftNumber - rightNumber
            "*" -> answer = leftNumber * rightNumber
            "/" -> {
                if (rightNumber != 0) {
                    answer = leftNumber / rightNumber
                } else {
                    // Handle division by zero scenario
                    answer = 0 // Set answer to 0 if division by zero
                }
            }
        }
        display.value = answer.toString()
    } else if (operation != "" && !complete) {
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CalcDisplay(display)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(3f)) {
                CalcRow(display, 7, 3)
                CalcRow(display, 4, 3)
                CalcRow(display, 1, 3)
                Row(
                    modifier = Modifier.padding(8.dp)) {
                    CalcNumericButton(0, display)
                    CalcEqualsButton(display)
                }

            }
            Column(modifier = Modifier.weight(1f)) {
                CalcOperationButton("+", display) { operation = "+" }
                CalcOperationButton("-", display) { operation = "-" }
                CalcOperationButton("*", display) { operation = "*" }
                CalcOperationButton("/", display) { operation = "/" }
            }
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxWidth()
    )
}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        for (i in startNum until endNum) {
            CalcNumericButton(number = i, display = display)
        }
    }
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>) {
    Button(
        onClick = {
            if (display.value == "0") {
                display.value = number.toString()
            } else {
                display.value += number
            }
        },
        modifier = Modifier.size(64.dp).padding(4.dp)
    ) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Button(
            onClick = {
                display.value = operation
                onClick()
            },
            modifier = Modifier.size(64.dp).padding(4.dp)
        ) {
            Text(text = operation)
        }
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>) {
    Button(
        onClick = { display.value = "0" },
        modifier = Modifier.size(64.dp).padding(4.dp)
    ) {
        Text(text = "=")
    }
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