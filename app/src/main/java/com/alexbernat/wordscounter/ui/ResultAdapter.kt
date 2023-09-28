package com.alexbernat.wordscounter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexbernat.wordscounter.databinding.ItemResultBinding
import com.alexbernat.wordscounter.domain.model.Word

private val DiffUtilCallback = object : DiffUtil.ItemCallback<Word>() {

    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}


class ResultAdapter : ListAdapter<Word, ResultAdapter.WordViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val binding = ItemResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val hexagram = currentList[position]
        holder.onBind(hexagram)
    }

    inner class WordViewHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(word: Word) {
            with(binding) {
                textViewWord.text = word.name
                textViewCount.text = word.frequency.toString()
            }
        }
    }
}