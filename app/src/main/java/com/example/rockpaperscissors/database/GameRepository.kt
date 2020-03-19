package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.models.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun insertGame(game: Game) = gameDao.insertGame(game)

    suspend fun deleteAllGames() = gameDao.deleteAllGames()

    suspend fun getAmountOf(string: String) = gameDao.getAmountOf(string)
}