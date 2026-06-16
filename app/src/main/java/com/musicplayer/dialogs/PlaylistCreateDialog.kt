package com.musicplayer.dialogs

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout

class PlaylistCreateDialog(
    context: Context,
    private val onPlaylistCreated: (String) -> Unit
) {
    private val context = context

    fun show() {
        val editText = EditText(context).apply {
            hint = "Playlist Name"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        AlertDialog.Builder(context)
            .setTitle("Create Playlist")
            .setView(editText)
            .setPositiveButton("Create") { _, _ ->
                val playlistName = editText.text.toString()
                if (playlistName.isNotEmpty()) {
                    onPlaylistCreated(playlistName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
