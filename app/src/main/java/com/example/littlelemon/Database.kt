package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}


@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuItemDao {
    @Insert
    fun insertAll(menuItems: List<MenuItemEntity>)

    @Query("SELECT * FROM menu_items")
    fun getAll(): LiveData<List<MenuItemEntity>>

    @Query("SELECT (SELECT COUNT(*) FROM menu_items) == 0")
    fun isEmpty(): Boolean
}