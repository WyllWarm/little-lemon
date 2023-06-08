package com.example.littlelemon.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(context: Context, navHostController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
            )
        }
        Row(
            modifier = Modifier
                .background(LittleLemonColor.green)
                .height(80.dp)
                .fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Let's get to know you",
                style = typography.headlineLarge,
                color = LittleLemonColor.cloud,
            )
        }



        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Personal informations:",
                style = typography.bodyLarge
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp)
        ) {
            OutlinedTextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text("First Name") },
                singleLine = true,
                placeholder = { Text(text = "Jane") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = LittleLemonColor.charcoal,
                    focusedBorderColor = LittleLemonColor.green
                ),
                shape = RoundedCornerShape(10.dp),
            )
            OutlinedTextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = LittleLemonColor.charcoal,
                    focusedBorderColor = LittleLemonColor.green
                ),
                shape = RoundedCornerShape(10.dp),
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = LittleLemonColor.charcoal,
                    focusedBorderColor = LittleLemonColor.green
                ),
                shape = RoundedCornerShape(10.dp),
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom) {
            Button(
                onClick = {
                    if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && email.value.isNotBlank()) {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches())
                            sharedPreferences.edit()
                                .putString("firstName", firstName.value)
                                .putString("lastName", lastName.value)
                                .putString("email", email.value)
                                .putBoolean("userRegistered", true)
                                .apply()

                        Toast.makeText(
                            context,
                            "Registration Successful",
                            Toast.LENGTH_SHORT
                        )
                            .show()


                        navHostController.navigate(Home.route) {
                            popUpTo(Onboarding.route) { inclusive = true }
                            launchSingleTop = true
                        }

                    } else {
                        Toast.makeText(
                            context,
                            "Invalid Details, Please try again",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

                ) {
                Text(text = "Register")
            }
        }

    }
}


@Preview
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Onboarding(context, navController)
}