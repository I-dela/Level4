package com.example.rockpaperscissors.ui

import android.content.Intent
import android.graphics.ColorSpace.match
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.models.ComputerRandomChoice
import com.example.rockpaperscissors.models.Game
import com.example.rockpaperscissors.models.Options

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Rock, Paper, Scissors Kotlin"

        gameRepository = GameRepository(this)
    initViews()
        imageRock.setOnClickListener { begin(Options.ROCK) }
        imagePaper.setOnClickListener { begin(Options.PAPER) }
        imageScissor.setOnClickListener { begin(Options.SCISSORS) }


    }

    fun initViews() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                tvWonStatistics.text = gameRepository.getAmountOf("YOU WON").toString()
                tvDrawStatistics.text = gameRepository.getAmountOf("DRAW").toString()
                tvLoseStatistics.text = gameRepository.getAmountOf("COMPUTER WINS").toString()

            }
        }

    }


    fun begin(playerChoice: Options) {
        val computerChoice = ComputerRandomChoice.randomOption()

        var winner = play(computerChoice, playerChoice)

        var result = getResult(computerChoice, playerChoice, winner)

        updateUi(result)
        saveResultToDatabase(result)


    }

    fun play(computerChoice: Options, playerChoice: Options): String {
        return when {
            playerChoice.toString() == computerChoice.toString() -> "DRAW"
            playerChoice == Options.SCISSORS && computerChoice == Options.PAPER ||
                    playerChoice == Options.PAPER && computerChoice == Options.ROCK ||
                    playerChoice == Options.ROCK && computerChoice == Options.SCISSORS
            -> "YOU WON"
            else -> "COMPUTER WINS"
        }

    }

    fun getResult(computerChoice: Options, playerChoice: Options, winner:String) : Game{
        return Game(
            Calendar.getInstance().time.toString(),
            computerChoice.toString(),
            playerChoice.toString(),
            winner

        )
    }

    fun updateUi(result: Game){
        ivComputerChoice.setImageResource(getImage(result.moveComputer))
        ivPlayerChoice.setImageResource(getImage(result.movePlayer))
        tvResult.text = result.winner
        initViews()
    }


    fun getImage(choice: String): Int {
        if (choice == Options.SCISSORS.toString()) {
            return R.drawable.scissors
        }
        if (choice == Options.PAPER.toString()) {
            return R.drawable.paper
        }
        return R.drawable.rock
    }


    private fun saveResultToDatabase(result: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(result)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.history ->{

                startResultsActivity()
                true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun startResultsActivity(){
        val intent = Intent(this@MainActivity, ResultsActivity::class.java)
        startActivity(intent)
    }
}
