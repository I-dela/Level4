package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.models.Game
import kotlinx.android.synthetic.main.item_result.view.*
import com.example.rockpaperscissors.ui.UiImage.Companion.getImage

class ResultAdapter(private val results: List<Game>) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvItemResultTitle.text = game.winner
            itemView.ivComputerItemResult.setImageResource(getImage(game.moveComputer))
            itemView.ivPlayerItemResult.setImageResource(getImage(game.movePlayer))
            itemView.tvDateTime.text = game.datePlayed

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
    }

    override fun getItemCount(): Int = results.size


    override fun onBindViewHolder(holder: ResultAdapter.ViewHolder, position: Int) {
        holder.bind(results[position])
    }
}