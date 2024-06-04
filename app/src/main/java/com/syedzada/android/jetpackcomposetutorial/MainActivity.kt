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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.syedzada.android.jetpackcomposetutorial.ui.theme.JetpackComposeTutorialTheme
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTutorialTheme {
                ExpandableCard(
                    title = "My Title",
                    body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
                )
            }
        }
    }
}


@Composable
fun FlatText() {
    Text(text = "Hello - ")
//    Text(text = "Jetpack Compose Tutorial")
}


@Composable
fun LinearLayoutHorizontal() {
    Row(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello - ")
        Text(text = "Jetpack Compose Tutorial")
    }
}

@Composable
fun LinearLayoutVertical() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello - ")
        Text(text = "Jetpack Compose Tutorial")
    }
}

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
/* Uni-directional data flow pattern
*   ViewModel (Survive configuration changes)
*       -> State => (FirstScreen())
*       <- Event
*   FirstScreen()
*       -> State => (ScreenContent())
*       <- Event
*   ScreenContent()
* */
/*
* URL Code-labs: "ViewModel and State in Compose"
* https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
* */
class MyViewModel: ViewModel() {
    // LiveData holds state which is observed by the UI.
    // (State flows down from ViewModel)
    private val _name = MutableLiveData("")
    val name:LiveData<String> = _name

    // onNameChanged is an event we're
    // defining that the UI can invoke/
    // (Events flow up from UI)
    fun onNameChanged(newName:String) {
        _name.value = newName
    }
}

@Composable
fun FirstScreen(
    myViewModel: MyViewModel = viewModel()
) {
    val name:String by myViewModel.name.observeAsState("")

    ScreenContent(
        name = name,
        onNameChange = { myViewModel.onNameChanged(it) }
    )
}

@Composable
fun ExpandableCard(title:String, body:String) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card {
        Column {
            Text(text = title)
            // Content of the card depends on the current value of expanded
            if(expanded) {
                Text(text = body)
                IconButton(onClick = { expanded = false }) {
                    Icon(Icons.Default.ExpandLess, contentDescription = "Collapse")
                }
            } else {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ExpandMore, contentDescription = "Expand")
                }
            }
        }
    }
}