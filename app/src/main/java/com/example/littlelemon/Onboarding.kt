package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sharedPref = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
    val onBoardingCompleted = sharedPref.getBoolean("onBoardingCompleted", false)
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    fun onBoardingAction() {
        if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && email.value.isNotBlank()) {
            if (isValidEmail(email.value)) {
                // Save user data to SharedPreferences
                with(sharedPref.edit()) {
                    putString("firstName", firstName.value)
                    putString("lastName", lastName.value)
                    putString("email", email.value)
                    putBoolean("onBoardingCompleted", true)
                    apply()
                }

                // Display "Registration successful!" message
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()

                // Navigate to the home screen
                navController.navigate(Destinations.Home)
            } else {
                // Display "Invalid email address" message
                Toast.makeText(context, "Invalid email address.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Display "Registration unsuccessful. Please enter all data." message
            Toast.makeText(
                context,
                "Registration unsuccessful. Please enter all data.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (onBoardingCompleted) {
        // Navigate to the home screen
        coroutineScope.launch {
            navController.navigate(Destinations.Home){
                popUpTo("Onboarding") { inclusive = true }
                launchSingleTop = true
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 15.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.fillMaxWidth(0.02F))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Little Lemon Logo",
                        modifier = Modifier
                            .fillMaxWidth(0.5F)
                            .padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.02F))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp)
                        .background(LittleLemonColor.green)
                        .height(120.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Let's get to know you",
                        style = MaterialTheme.typography.h5,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Profile information:",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 5.dp, bottom = 30.dp)
                )

                Text(
                    text = "First Name",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp, bottom = 5.dp)
                )
                TextField(
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    shape = RoundedCornerShape(10),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        disabledTextColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(10)),
                )
                Text(
                    text = "Last Name",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp, bottom = 5.dp)
                )
                TextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        disabledTextColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(10)),
                )

                Text(
                    text = "Email",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp, bottom = 5.dp)
                )
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        disabledTextColor = Color.Black,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(10)),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onBoardingAction() })
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ){
                Button(
                    onClick = {
                        onBoardingAction()
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 16.dp, top = 20.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LittleLemonColor.yellow)
                ) {
                    Text(text = "Register",
                        color = LittleLemonColor.green
                    )
                }
            }
        }
    }
}

fun isValidEmail(email: CharSequence): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}