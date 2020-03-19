package com.example.rockpaperscissors.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rockpaperscissors.models.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(product: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(id) as total FROM game_table WHERE winner = :string")
    suspend fun getAmountOf(string: String): Int
}