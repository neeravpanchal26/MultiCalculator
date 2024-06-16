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
        display.value = rightNumber.toString()
    } else {
        display.value = leftNumber.toString()
    }

    fun numberPress(btnNum: Int) {
        if (complete) {
            leftNumber = 0
            rightNumber = 0
            operation = ""
            complete = false
        } else if (operation.isNotBlank() && !complete) {
            rightNumber = rightNumber * 10 + btnNum
        } else if (operation.isBlank() && !complete) {
            leftNumber = leftNumber * 10 + btnNum
        }
    }

    fun operationPress(op: String) {
        if (!complete) {
            operation = op
        }
    }

    fun equalsPress() {
        complete = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(16.dp)
    ) {
        Row {
            CalcDisplay(display)
        }
        Row {
            Column(modifier = Modifier.weight(3f)) {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(
                        onPress = { number -> numberPress(number) },
                        startNum = i,
                        numButtons = 3
                    )
                }
                Row {
                    CalcNumericButton(number = 0, onPress = { number -> numberPress(number) })
                    CalcEqualsButton(onPress = { equalsPress() })
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                CalcOperationButton(operation = "+", onPress = { op -> operationPress(op) })
                CalcOperationButton(operation = "-", onPress = { op -> operationPress(op) })
                CalcOperationButton(operation = "*", onPress = { op -> operationPress(op) })
                CalcOperationButton(operation = "/", onPress = { op -> operationPress(op) })
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
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        for (i in startNum until endNum) {
            CalcNumericButton(number = i, onPress)
        }
    }
}

@Composable
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    Button(
        onClick = {
            onPress(number)
        },
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
    ) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            onClick = {
                onPress(operation)
            },
            modifier = Modifier
                .size(64.dp)
                .padding(4.dp)
        ) {
            Text(text = operation)
        }
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit) {
    Button(
        onClick = { onPress() },
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
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