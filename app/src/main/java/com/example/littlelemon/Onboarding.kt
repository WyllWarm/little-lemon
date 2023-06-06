package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Onboarding() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.Logo),
            contentDescription = "Logo"
        )
        Text(text = "Let's get to know you")

        val firstName = remember { mutableStateOf("") }
        val lastName = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }

        TextField(
            value = firstName.value ,
            onValueChange = { newValue -> firstName.value = newValue },
            label = { Text("First Name")}
        )
        TextField(
            value = lastName.value ,
            onValueChange = { newValue -> lastName.value = newValue },
            label = { Text("Last Name")}
        )
        TextField(
            value = email.value ,
            onValueChange = { newValue -> email.value = newValue },
            label = { Text("Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Register")
        }
    }
}
@Preview
@Composable
fun PreviewOnboarding(){
    Onboarding()
}