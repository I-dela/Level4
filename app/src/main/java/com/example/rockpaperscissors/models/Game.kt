package com.example.rockpaperscissors.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game (


    @ColumnInfo(name = "datePlayed")
    var datePlayed: String,

    @ColumnInfo(name = "moveComputer")
    var moveComputer: String,

    @ColumnInfo(name = "movePlayer")
    var movePlayer: String,

    @ColumnInfo(name = "winner")
    var winner: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null


): Parcelable{
}