package com.example.rockpaperscissors.models

import kotlin.random.Random

class ComputerRandomChoice {
    companion object {
        private val SEED = Math.random().toInt()
        private val RANDOM = Random(SEED)



        fun randomOption(): Options {
            return Options.values()[RANDOM.nextInt(Options.values().size)]
        }
    }
}