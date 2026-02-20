package com.example.photoprofile.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoprofile.R
import com.example.photoprofile.ui.dataclass.PhotoUi


class ImageAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val list = mutableListOf<PhotoUi>()

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imgItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.image.context)
            .load(list[position].src.portrait)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick(list[position].src.large)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setPhotos(newPhotos: List<PhotoUi>) {
        list.clear()
        list.addAll(newPhotos)
        notifyDataSetChanged()
    }
}