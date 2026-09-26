package com.example.photoprofile.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoprofile.R
import com.example.photoprofile.ui.dataclass.ImageItemUi

class LoadingAdapter() : RecyclerView.Adapter<LoadingAdapter.LoadingViewHolder>() {

    private val list = mutableListOf<ImageItemUi>()

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return LoadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {
        Glide.with(holder.image.context)
            .load(list[position].uri)
            .into(holder.image)

    }

    override fun getItemCount(): Int = list.size

    fun submitList(data: List<ImageItemUi>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}