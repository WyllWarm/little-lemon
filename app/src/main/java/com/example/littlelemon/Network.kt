package com.example.littlelemon.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase

    init {
        database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    fun getAllDatabaseMenuItems(): LiveData<List<MenuItemRoom>> {
        return database.menuItemDao().getAll()
    }

    fun fetchMenuDataIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                saveMenuToDatabase(
                    database,
                    fetchMenu("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                )
            }
        }
    }
}

@Serializable
data class MenuNetwork(

    @SerialName("menu")
    val items: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: Double,

    @SerialName("category")
    val category: String,

    @SerialName("image")
    val imageUrl: String
){
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}

suspend fun fetchMenu(url: String): List<MenuItemNetwork> {
    val httpClient = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text", "plain"))
        }
    }
    val  httpResponse: MenuNetwork = httpClient.get(url).body()
    return httpResponse.items
}


fun saveMenuToDatabase(database: AppDatabase,  menuItemsNetwork: List<MenuItemNetwork>) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
}