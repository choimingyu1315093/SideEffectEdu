package com.example.sideeffectedu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.sideeffectedu.ui.theme.SideEffectEduTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SideEffectEduTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ProduceStateSample()
                }
            }
        }
    }
}

@Composable
fun LaunchedEffectSample(modifier: Modifier = Modifier){
    var isRunning by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {
                isRunning = true
            }
        ) {
            Text(
                text = "Start Timer"
            )
        }

        LaunchedEffect(isRunning) {
            if(isRunning){
                while (true){
                    delay(1000)
                    count++
                }
            }
        }

        Text(
            text = "count $count"
        )
    }
}

@Composable
fun DisposableEffectSimple(modifier: Modifier = Modifier){
    var showEffect by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ){
        Button(onClick = {
            showEffect = !showEffect
            }
        ) {
            Text(if (showEffect) "Socket 해제" else "Socket 연결")
        }

        if (showEffect) {
            DisposableEffect(Unit) {
                Toast.makeText(context, "Socket 연결", Toast.LENGTH_SHORT).show()
                onDispose {
                    Toast.makeText(context, "Socket 해제", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun SideEffectSample(modifier: Modifier = Modifier){
    var count by remember { mutableStateOf(0) }
    var context = LocalContext.current

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {
                count++
            }
        ) {
            Text(
                text = "Add Count"
            )
        }

        Text("Count: $count")

        SideEffect {
            Toast.makeText(context, "count $count", Toast.LENGTH_SHORT).show()
        }
    }
}

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun ProduceStateSample(modifier: Modifier = Modifier){
    var isLoading by remember { mutableStateOf(false) }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ){
        Button(
            onClick = {
                isLoading = true
            }
        ) {
            Text(
                "데이터 가져오기"
            )
        }

        val dataState = produceState(initialValue = "데이터 가져오는 중...", key1 = isLoading) {
            if(isLoading){
                delay(3000)
                value = "데이터 가져오기 완료"
            }
        }

        Text(
            text = dataState.value
        )
    }
}