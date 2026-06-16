package com.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.musicplayer.R
import com.musicplayer.adapters.PlaylistsAdapter
import com.musicplayer.database.MusicDatabase
import com.musicplayer.dialogs.PlaylistCreateDialog
import com.musicplayer.models.Playlist
import com.musicplayer.repository.MusicRepository
import kotlinx.coroutines.launch

class PlaylistsFragment : Fragment() {
    private lateinit var database: MusicDatabase
    private lateinit var repository: MusicRepository
    private lateinit var playlistsRecyclerView: RecyclerView
    private lateinit var playlistsAdapter: PlaylistsAdapter
    private lateinit var fabCreatePlaylist: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_playlists, container, false)
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

        playlistsRecyclerView = view.findViewById(R.id.playlistsRecyclerView)
        playlistsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fabCreatePlaylist = view.findViewById(R.id.fabCreatePlaylist)
        fabCreatePlaylist.setOnClickListener {
            showCreatePlaylistDialog()
        }

        playlistsAdapter = PlaylistsAdapter(
            mutableListOf(),
            onPlaylistClick = { playlist ->
                // Navigate to playlist details
            },
            onDeleteClick = { playlist ->
                lifecycleScope.launch {
                    repository.deletePlaylist(playlist.id)
                    loadPlaylists()
                }
            }
        )
        playlistsRecyclerView.adapter = playlistsAdapter

        loadPlaylists()
    }

    private fun loadPlaylists() {
        lifecycleScope.launch {
            repository.getAllPlaylists().collect { playlists ->
                playlistsAdapter.updatePlaylists(playlists)
            }
        }
    }

    private fun showCreatePlaylistDialog() {
        PlaylistCreateDialog(requireContext()) { playlistName ->
            lifecycleScope.launch {
                val playlist = Playlist(name = playlistName)
                repository.createPlaylist(playlist)
                loadPlaylists()
            }
        }.show()
    }
}
