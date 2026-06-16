package com.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.R
import com.musicplayer.adapters.SongsAdapter
import com.musicplayer.database.MusicDatabase
import com.musicplayer.repository.MusicRepository
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private lateinit var database: MusicDatabase
    private lateinit var repository: MusicRepository
    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var favoritesAdapter: SongsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = MusicDatabase.getDatabase(requireContext())
        repository = MusicRepository(
            database.songDao(),
            database.playlistDao(),
            database.playlistSongDao(),
            database.favoriteDao()
        )

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        favoritesAdapter = SongsAdapter(
            mutableListOf(),
            onSongClick = { song ->
                // Navigate to NowPlayingActivity
            },
            onFavoriteClick = { song ->
                lifecycleScope.launch {
                    repository.removeFavorite(song.id)
                    song.isFavorite = false
                    loadFavorites()
                }
            }
        )
        favoritesRecyclerView.adapter = favoritesAdapter

        loadFavorites()
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            repository.getFavoriteSongIds().collect { favoriteIds ->
                val favoriteSongs = mutableListOf<com.musicplayer.models.Song>()
                favoriteIds.forEach { songId ->
                    repository.getSongById(songId).collect { song ->
                        if (song != null) {
                            song.isFavorite = true
                            favoriteSongs.add(song)
                        }
                    }
                }
                favoritesAdapter.updateSongs(favoriteSongs)
            }
        }
    }
}
