package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
    val firstName = remember { mutableStateOf(sharedPref.getString("firstName", "") ?: "") }
    val lastName = remember { mutableStateOf(sharedPref.getString("lastName", "") ?: "") }
    val email = remember { mutableStateOf(sharedPref.getString("email", "") ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth(0.05F)
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .fillMaxWidth(0.5F)
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.02F))
        }
        Column() {
            Text(
                text = "Profile information:",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp, bottom = 30.dp)
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
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Black,
                    backgroundColor = LittleLemonColor.gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10)),
                enabled = false
            )

            Text(
                text = "Last Name",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 16.dp, bottom = 5.dp)
            )
            TextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                shape = RoundedCornerShape(10),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Black,
                    backgroundColor = LittleLemonColor.gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10)),
                enabled = false
            )

            Text(
                text = "Email",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 16.dp, bottom = 5.dp)
            )
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                shape = RoundedCornerShape(10),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Black,
                    backgroundColor = LittleLemonColor.gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10)),
                enabled = false
            )
        }

        Button(
            onClick = {
                sharedPref.edit().clear().apply()
                navController.navigate(Destinations.Onboarding) {
                    popUpTo(Destinations.Home) { inclusive = true }
                }
            },
            modifier = Modifier
                .padding(top = 16.dp, bottom = 30.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LittleLemonColor.yellow)
        ) {
            Text("Log out",
                color = LittleLemonColor.green
            )
        }
    }
}