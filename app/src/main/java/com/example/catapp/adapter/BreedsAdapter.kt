package com.example.catapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catapp.R
import com.example.catapp.domain.model.Breed

class BreedsAdapter: RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder>() {

    inner class BreedsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        return BreedsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_breed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        val breed = differ.currentList[position]
        holder.itemView.apply {
            val image = breed.image?.url ?: R.drawable.ic_grinning_cat
            Glide.with(this).load(image).into(findViewById(R.id.ivBreedImage))
            findViewById<TextView>(R.id.twItemBreed).text = breed.name
            setOnClickListener { onItemClickListener?.let { it(breed) } }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Breed) -> Unit)? = null

    fun setOnItemClickListener(listener: (Breed) -> Unit) {
        onItemClickListener = listener
    }
}