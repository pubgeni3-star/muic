package com.musicplayer.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val artist: String,
    val album: String,
    val albumArt: String? = null,
    val duration: Long,
    val path: String,
    val dateModified: Long = 0,
    var isFavorite: Boolean = false
)
