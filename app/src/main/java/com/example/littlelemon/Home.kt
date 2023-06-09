package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonColor
import java.util.Locale


@Composable
fun Home(
    navController: NavHostController,
    viewModel: List<MenuItemEntity>,
    searchPhrase: String,
    onSearchPhraseChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.fillMaxWidth(0.05F))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .fillMaxWidth(0.5F)
                    .padding(horizontal = 20.dp)
            )
            IconButton(
                onClick = {
                    navController.navigate(Destinations.Profile)
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(20.dp))

                )
            }
        }
        Column(
            modifier = Modifier
                .background(LittleLemonColor.green)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = LittleLemonColor.yellow
                )
                Text(
                    text = stringResource(id = R.string.location),
                    fontSize = 24.sp,
                    color = LittleLemonColor.cloud
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.description),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(bottom = 28.dp, end = 20.dp)
                            .fillMaxWidth(0.6f),
                        color = LittleLemonColor.cloud
                    )
                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Upper Panel Image",
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(7.dp))
            ) {
                TextField(
                    value = searchPhrase,
                    onValueChange = onSearchPhraseChange,
                    label = { Text("Enter search phrase") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LittleLemonColor.gray)
                )
            }
        }


        val categoriesSet = remember { mutableStateOf<Set<String>>(emptySet()) }

        val categories = mutableListOf("all")

        categoriesSet.value = viewModel.map { it.category }.toSet()

        categories.addAll(categoriesSet.value)


        val selectedCategory = remember { mutableStateOf("") }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)) {
            Text(text = "Order for Delivery",
                color = LittleLemonColor.charcoal,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
                )

        }

        LazyRow {
            items(categories) { category ->
                Button(
                    onClick = {
                        if (category === "all") {
                            selectedCategory.value = ""
                        } else if (category === selectedCategory.value) {
                            selectedCategory.value = ""
                        } else {
                            selectedCategory.value = category
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =
                        if (category === "all" && selectedCategory.value === "") {
                            LittleLemonColor.green
                        } else if (category === selectedCategory.value) {
                            LittleLemonColor.green
                        } else {
                            LittleLemonColor.cloud
                        }
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(6.dp)

                ) {
                    Text(
                        text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        color =
                        if (category === "all" && selectedCategory.value === "") {
                            LittleLemonColor.cloud
                        } else if (category === selectedCategory.value) {
                            LittleLemonColor.cloud
                        } else {
                            LittleLemonColor.green
                        }
                    )

                }
            }
        }


        MenuItems(viewModel.filter { menuItem ->
            (selectedCategory.value.isEmpty() || menuItem.category == selectedCategory.value) &&
                    (searchPhrase.isEmpty() || menuItem.title.contains(
                        searchPhrase,
                        ignoreCase = true
                    ))
        })

    }
}


@Composable
fun MenuItems(itemsList: List<MenuItemEntity>) {
    LazyColumn {
        items(itemsList) { menuItem ->
            MenuItem(menuItem = menuItem)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemEntity) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(0.6f)
            ) {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "$${menuItem.price}",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(bottom = 8.dp)
                )}
                Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center) {


                    GlideImage(
                        model = menuItem.image,
                        contentDescription = "Menu item image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)

                    )
                }

        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
        color = LittleLemonColor.yellow
    )
}
