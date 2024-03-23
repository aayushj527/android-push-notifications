package com.test.pushnotifications

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.test.pushnotifications.ui.theme.PushNotificationsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("FCM Token", "Initializing firebase")
        FirebaseApp.initializeApp(this)
        Log.d("FCM Token", "Firebase initialized")

        /**
         *  A FCM token will be generated as soon as user opens the app.
         */
        getFcmToken()

        setContent {
            PushNotificationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

fun getFcmToken() {
    Log.d("FCM Token", "Getting FCM token")
    CoroutineScope(Dispatchers.IO).launch {
        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("FCM Token", it.result)
                } else {
                    Log.d("FCM Token", "Unable to generate token")
                    Log.d("FCM Token", "${it.exception?.localizedMessage}")
                }
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PushNotificationsTheme {
        Greeting("Android")
    }
}