package com.gayathri.jokesbook.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gayathri.jokesbook.R
import com.gayathri.jokesbook.data.model.Joke
import com.gayathri.jokesbook.utils.Utils
import kotlinx.android.synthetic.main.row_joke_types.view.*

class JokesTypesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var glide: RequestManager
    lateinit var view: View
    var list: List<Joke> = listOf()
    var listener: JokeSelectionListener? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JokesViewHolder {
        return JokesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_joke_types, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as JokesViewHolder).bind(
            joke = list[position],
            listener = listener,
            position = position
        )
    }

    inner class JokesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(joke: Joke, listener: JokeSelectionListener? = null, position: Int) {
            with(itemView) {
                view = itemView
                glide = Glide.with(context)

                row_types_layout.isVisible = list.isNotEmpty()
                val drawable = Utils.getDrawable()[position]
                row_types_imageView.setImageResource(drawable)
                row_types_textView.text = joke.setup

                setOnClickListener {
                    listener?.onJokeSelected(joke, position)
                }
            }
        }
    }

    interface JokeSelectionListener {
        fun onJokeSelected(joke: Joke, position: Int)
    }
}