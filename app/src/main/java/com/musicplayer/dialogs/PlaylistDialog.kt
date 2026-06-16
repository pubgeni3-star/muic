package com.musicplayer.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListView
import android.widget.ArrayAdapter
import com.musicplayer.models.Playlist
import com.musicplayer.repository.MusicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistDialog(
    context: Context,
    private val repository: MusicRepository,
    private val songId: Long,
    private val scope: CoroutineScope
) {
    private val context = context
    private var playlists = mutableListOf<Playlist>()
    private lateinit var adapter: ArrayAdapter<String>

    fun show() {
        val dialogView = LayoutInflater.from(context).inflate(
            android.R.layout.simple_list_item_1,
            null
        )
        val listView = ListView(context)
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        loadPlaylists()

        val dialog = AlertDialog.Builder(context)
            .setTitle("Add to Playlist")
            .setView(listView)
            .setPositiveButton("Create New") { _, _ -> showCreatePlaylistDialog() }
            .setNegativeButton("Cancel", null)
            .create()

        listView.setOnItemClickListener { _, _, position, _ ->
            scope.launch {
                repository.addSongToPlaylist(playlists[position].id, songId)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun loadPlaylists() {
        scope.launch {
            repository.getAllPlaylists().collect { playlistList ->
                playlists = playlistList.toMutableList()
                withContext(Dispatchers.Main) {
                    adapter.clear()
                    adapter.addAll(playlistList.map { it.name })
                }
            }
        }
    }

    private fun showCreatePlaylistDialog() {
        val editText = EditText(context).apply {
            hint = "Playlist Name"
        }

        AlertDialog.Builder(context)
            .setTitle("Create Playlist")
            .setView(editText)
            .setPositiveButton("Create") { _, _ ->
                val playlistName = editText.text.toString()
                if (playlistName.isNotEmpty()) {
                    scope.launch {
                        val playlist = Playlist(name = playlistName)
                        val playlistId = repository.createPlaylist(playlist)
                        repository.addSongToPlaylist(playlistId, songId)
                        loadPlaylists()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
