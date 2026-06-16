package com.musicplayer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musicplayer.models.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<Song>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song): Long

    @Update
    suspend fun updateSong(song: Song)

    @Query("SELECT * FROM songs ORDER BY title")
    fun getAllSongs(): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE id = :songId")
    fun getSongById(songId: Long): Flow<Song?>

    @Query("SELECT * FROM songs WHERE artist = :artist ORDER BY album, title")
    fun getSongsByArtist(artist: String): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE album = :album ORDER BY title")
    fun getSongsByAlbum(album: String): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE title LIKE :query OR artist LIKE :query OR album LIKE :query ORDER BY title")
    fun searchSongs(query: String): Flow<List<Song>>

    @Query("SELECT COUNT(*) FROM songs")
    fun getSongsCount(): Flow<Int>

    @Query("DELETE FROM songs WHERE id = :songId")
    suspend fun deleteSong(songId: Long)
}
