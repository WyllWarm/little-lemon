package com.example.littlelemon

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    @SerialName("description")
    val desc: String,
    val price: String,
    val image: String,
    val category: String
){
    fun toMenuItemRoom() = MenuItemEntity(
        id,
        title,
        desc,
        price,
        image,
        category
    )
}