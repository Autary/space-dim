package com.example.spacedim.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacedim.R
import com.example.spacedim.classes.User
import com.squareup.picasso.Picasso


class HighScoreAdapter(private val users: List<User>) :
    RecyclerView.Adapter<HighScoreAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.avatar)
        val nameTextView = itemView.findViewById<TextView>(R.id.name)
        val scoreTextView = itemView.findViewById<TextView>(R.id.player_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val userRowView = inflater.inflate(R.layout.row_players_score, parent, false)
        return ViewHolder(userRowView)
    }


    override fun onBindViewHolder(viewHolder: HighScoreAdapter.ViewHolder, position: Int) {
        val user: User = users.get(position)
        val imageView = viewHolder.imageView
        val nameTextView = viewHolder.nameTextView
        val scoreTextView = viewHolder.scoreTextView
        nameTextView.setText(user.name)
        scoreTextView.setText(user.score.toString())
        Picasso.get().load(user.avatar).into(imageView);
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return users.size
    }
}


