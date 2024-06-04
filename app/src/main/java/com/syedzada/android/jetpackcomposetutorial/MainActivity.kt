package com.syedzada.android.jetpackcomposetutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.syedzada.android.jetpackcomposetutorial.ui.theme.JetpackComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTutorialTheme {
                FlatText()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlatText() {
    Text(text = "Hello - ")
//    Text(text = "Jetpack Compose Tutorial")
}


@Preview(showBackground = true)
@Composable
fun LinearLayoutHorizontal() {
    Row(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello - ")
        Text(text = "Jetpack Compose Tutorial")
    }
}

@Preview(showBackground = true)
@Composable
fun LinearLayoutVertical() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello - ")
        Text(text = "Jetpack Compose Tutorial")
    }
}

@Preview(showBackground = true)
@Composable
fun SimilarFrameLayout() {
    Box {
        Text(text = "Hello - ")
        Text(text = "Jetpack Compose Tutorial")
    }
}

// initial composition
// recomposition - state of app change -> recomposition call - re-execute the composable function that make changes

// Life cycle:
/*
* Step 1:
*   Enter the composition
* Step 2:
*   Recompose 0 or more times
* Step 3:
*   Leave the composition
* */

// Recomposition:
/*
* TextView -> Value update -> binding.textView.text = "New Text"
* In Compose-> internal state of the composable -> Call Composable function with updated state
* and then redrawn recomposition occur.
* */

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyButton() {
    // state
    var myValue by remember {
        mutableStateOf(false)
    } // saved in composition while initial composition
    Log.d("Recomposition", "MyButton Composition")
    Button(
        onClick = { myValue = !myValue }
    ) {
        Text(text = "$myValue")
        Log.d("Recomposition", "MyButton Composition/ReComposition Lambda Content")
    }
}

// MutableState
/*
* Return a new MutableState initialized with the passed in value.
* The MutableState class is a single value holder whose reads and writes
* are observed by Compose. Additionally, writes to it are transacted as
* part of the Snapshot system.
* */

/* Remember:
* Composable Functions can execute in any order.
* Composable Functions can execute in parallel.
* Recomposition skips as many composable functions and lambdas as possible.
* Recomposition is optimistic and may be canceled.
* A composable function might run quite frequently as often as every frame of an animation.
* */

/*
* State?
*   State is any value that can change over time.
*   example: variable, room database value, sensor value
*   UI is immutable - so if drawn you can't change UI -> you just handle state of the ui
* Event?
*   Notify a part of a program that something has happened.
*   Example TextField
* */

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlainTextComposable() {
    var name by remember {
        mutableStateOf("")
    }

    TextField(
        value = name,
        onValueChange = {
            name = it
        },
        label = {
            Text(text = "Name")
        }
    )
}

/*
* Composable types:
*   1- Stateful (expensive so use State Hoisting - replace state by parameter ⬆)
*   2- Stateless (not hold any state)
* */

/*
* Stateful
* FirstScreen()
* */

@Composable
fun FirstScreen() {
    var name by remember {
        mutableStateOf("")
    }

    ScreenContent(
        name = name,
        onNameChange = { name = it }
    )
}

/*
* Stateless
* ScreenContent()
* */

@Composable
fun ScreenContent(
    name: String,
    onNameChange: (String) -> Unit
) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = {
            Text(text = "Name")
        }
    )
}