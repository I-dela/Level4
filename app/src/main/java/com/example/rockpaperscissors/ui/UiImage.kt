package com.example.rockpaperscissors.ui

import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.models.Options

class UiImage {
    companion object{

        fun getImage(choice: String): Int {
            if (choice == Options.SCISSORS.toString()) {
                return R.drawable.scissors
            }
            if (choice == Options.PAPER.toString()) {
                return R.drawable.paper
            }
            return R.drawable.rock
        }
    }
}