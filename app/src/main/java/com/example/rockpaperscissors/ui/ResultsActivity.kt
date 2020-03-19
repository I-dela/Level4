package com.example.rockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.models.Game

import kotlinx.android.synthetic.main.activity_results.*
import kotlinx.android.synthetic.main.content_results.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultsActivity : AppCompatActivity() {

    private val gameList = arrayListOf<Game>()

    private lateinit var gameRepository: GameRepository

    private val resultAdapter  = ResultAdapter(gameList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        gameRepository = GameRepository(this)
        initViews()




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                deleteAllResults()
                true
            }
            android.R.id.home -> {
                backHome()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_results, menu)
        return true
    }

    fun initViews(){

        rvGames.adapter = resultAdapter

        rvGames.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvGames.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        getGamesFromDatabase()



    }

    fun getGamesFromDatabase(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameList.addAll( gameRepository.getAllGames())
            }
        }.invokeOnCompletion {
            this@ResultsActivity.resultAdapter.notifyDataSetChanged()
        }
    }

    fun deleteAllResults(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
        }.invokeOnCompletion {
            this@ResultsActivity.resultAdapter.notifyDataSetChanged()
        }

        backHome()

    }

    private fun backHome() {
        val intent = Intent(this@ResultsActivity, MainActivity::class.java)
        startActivity(intent)

    }

}
