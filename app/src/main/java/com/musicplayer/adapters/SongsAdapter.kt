package com.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicplayer.databinding.ItemSongBinding
import com.musicplayer.models.Song

class SongsAdapter(
    private val songs: MutableList<Song>,
    private val onSongClick: (Song) -> Unit,
    private val onFavoriteClick: (Song) -> Unit
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    inner class SongViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.apply {
                songTitle.text = song.title
                songArtist.text = song.artist

                if (!song.albumArt.isNullOrEmpty()) {
                    Glide.with(root)
                        .load(song.albumArt)
                        .into(songAlbumArt)
                }

                btnFavorite.setImageResource(
                    if (song.isFavorite) com.musicplayer.R.drawable.ic_favorite
                    else com.musicplayer.R.drawable.ic_favorite_outline
                )

                root.setOnClickListener { onSongClick(song) }
                btnFavorite.setOnClickListener { onFavoriteClick(song) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size

    fun updateSongs(newSongs: List<Song>) {
        songs.clear()
        songs.addAll(newSongs)
        notifyDataSetChanged()
    }
}
