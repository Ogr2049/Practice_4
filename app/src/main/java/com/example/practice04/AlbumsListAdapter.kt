package com.example.practice04

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.example.practice04.network.model.Album

class AlbumsListAdapter : RecyclerView.Adapter<AlbumsListAdapter.AlbumViewHolder>() {

    private var albumsList = emptyList<Album>()

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.album_tab, parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumsList[position]

        holder.itemView.findViewById<TextView>(R.id.album_album_name).text = album.title
        holder.itemView.findViewById<TextView>(R.id.album_num_of_photos).text = when (album.size) {
            0L -> holder.itemView.context.getString(R.string.no_photos)
            1L -> String.format(holder.itemView.context.getString(R.string.one_photo), album.size)
            else -> String.format(holder.itemView.context.getString(R.string.many_photos),
                album.size)
        }

        Glide
            .get(holder.itemView.context)
            .setMemoryCategory(MemoryCategory.LOW)
        Glide
            .with(holder.itemView)
            .load(album.thumbUrl)
            .into(holder.itemView.findViewById(R.id.album_image))

        holder.itemView.findViewById<ConstraintLayout>(R.id.album_tab_constraint_layout)
            .setOnClickListener {
                if (album.size == 0L) {
                    Toast.makeText(
                        holder.itemView.context,
                        holder.itemView.context.getString(R.string.err_empty_album),
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val intent = Intent(holder.itemView.context, PhotosActivity::class.java)
                intent.putExtra("album_id", when (album.title) {
                    "Фотографии с моей страницы" -> "profile"
                    "Сохранённые фотографии" -> "saved"
                    else -> album.id.toString()
                })
                intent.putExtra("user_id", album.ownerId)
                holder.itemView.context.startActivity(intent)
            }
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(albums: List<Album>) {
        this.albumsList = albums
        notifyDataSetChanged()
    }
}
