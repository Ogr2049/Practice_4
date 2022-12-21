package com.example.practice04

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice04.network.model.Photo

class PhotosListAdapter : RecyclerView.Adapter<PhotosListAdapter.PhotoViewHolder>() {

    private var photosList = mutableListOf<Photo>()

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_tab, parent, false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photosList[position]

        val image = holder.itemView.findViewById<ImageView>(R.id.photo_image)

        Glide.with(holder.itemView)
            .load(photo.photoUrl)
            .placeholder(ColorDrawable(0xFF808080.toInt()))
            .into(image)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(photos: List<Photo>) {
        photosList.addAll(photos)
        notifyDataSetChanged()
    }

    fun plusData(photos: List<Photo>) {
        val size = photosList.size - 1
        photosList.addAll(photos)
        notifyItemRemoved(size)
    }
}
