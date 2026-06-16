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

class SongsFragment : Fragment() {
    private lateinit var database: MusicDatabase
    private lateinit var repository: MusicRepository
    private lateinit var songsRecyclerView: RecyclerView
    private lateinit var songsAdapter: SongsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_songs, container, false)
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

        songsRecyclerView = view.findViewById(R.id.songsRecyclerView)
        songsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        songsAdapter = SongsAdapter(
            mutableListOf(),
            onSongClick = { song ->
                // Navigate to NowPlayingActivity
            },
            onFavoriteClick = { song ->
                lifecycleScope.launch {
                    if (song.isFavorite) {
                        repository.removeFavorite(song.id)
                    } else {
                        repository.addFavorite(song.id)
                    }
                    song.isFavorite = !song.isFavorite
                    songsAdapter.notifyDataSetChanged()
                }
            }
        )
        songsRecyclerView.adapter = songsAdapter

        loadSongs()
    }

    private fun loadSongs() {
        lifecycleScope.launch {
            repository.getAllSongs().collect { songs ->
                songsAdapter.updateSongs(songs)
            }
        }
    }
}
