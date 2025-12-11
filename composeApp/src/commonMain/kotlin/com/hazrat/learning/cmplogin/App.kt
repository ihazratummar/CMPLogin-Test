package com.hazrat.learning.cmplogin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import cmplogin.composeapp.generated.resources.Res
import cmplogin.composeapp.generated.resources.compose_multiplatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    MaterialTheme {
        val scope = rememberCoroutineScope()
        val auth = remember { Firebase.auth }

        var firebaseUser: FirebaseUser ? by remember { mutableStateOf(null) }

        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }

        if (firebaseUser == null) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(
                    value = userEmail,
                    onValueChange = {userEmail = it},
                    placeholder = {Text(text = "Email Address")}
                )

                Spacer(Modifier.height(12.dp))

                TextField(
                    value = userPassword,
                    onValueChange = {userPassword = it},
                    placeholder = {Text(text = "Email Address")},
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                auth.createUserWithEmailAndPassword(
                                    email = userEmail,
                                    password = userPassword
                                )
                            }catch (e: Exception){
                                auth.signInWithEmailAndPassword(
                                    email = userEmail,
                                    password = userPassword
                                )

                            }
                            firebaseUser = auth.currentUser
                        }
                    }
                ){
                    Text(text = "Sign In")
                }

            }
        }else{

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = firebaseUser?.uid ?:" No User Data"
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        scope.launch {
                            auth.signOut()
                            firebaseUser = auth.currentUser
                        }
                    }
                ){
                    Text(text = "Sign Out")
                }
            }

        }
    }
}