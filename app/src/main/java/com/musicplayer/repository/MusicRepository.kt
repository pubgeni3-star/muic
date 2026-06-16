package com.musicplayer.repository

import com.musicplayer.database.FavoriteDao
import com.musicplayer.database.PlaylistDao
import com.musicplayer.database.PlaylistSongDao
import com.musicplayer.database.SongDao
import com.musicplayer.models.Favorite
import com.musicplayer.models.Playlist
import com.musicplayer.models.PlaylistSong
import com.musicplayer.models.Song
import kotlinx.coroutines.flow.Flow

class MusicRepository(
    private val songDao: SongDao,
    private val playlistDao: PlaylistDao,
    private val playlistSongDao: PlaylistSongDao,
    private val favoriteDao: FavoriteDao
) {
    // Song operations
    suspend fun insertSongs(songs: List<Song>) = songDao.insertSongs(songs)
    suspend fun insertSong(song: Song) = songDao.insertSong(song)
    fun getAllSongs(): Flow<List<Song>> = songDao.getAllSongs()
    fun getSongById(songId: Long): Flow<Song?> = songDao.getSongById(songId)
    fun getSongsByArtist(artist: String): Flow<List<Song>> = songDao.getSongsByArtist(artist)
    fun getSongsByAlbum(album: String): Flow<List<Song>> = songDao.getSongsByAlbum(album)
    fun searchSongs(query: String): Flow<List<Song>> = songDao.searchSongs("%$query%")
    fun getSongsCount(): Flow<Int> = songDao.getSongsCount()

    // Playlist operations
    suspend fun createPlaylist(playlist: Playlist): Long = playlistDao.insertPlaylist(playlist)
    suspend fun updatePlaylist(playlist: Playlist) = playlistDao.updatePlaylist(playlist)
    suspend fun deletePlaylist(playlistId: Long) = playlistDao.deletePlaylistById(playlistId)
    fun getAllPlaylists(): Flow<List<Playlist>> = playlistDao.getAllPlaylists()
    fun getPlaylistById(playlistId: Long): Flow<Playlist?> = playlistDao.getPlaylistById(playlistId)
    fun getPlaylistsCount(): Flow<Int> = playlistDao.getPlaylistsCount()

    // Playlist song operations
    suspend fun addSongToPlaylist(playlistId: Long, songId: Long) {
        val maxPosition = playlistSongDao.getMaxPosition(playlistId) ?: 0
        playlistSongDao.insertPlaylistSong(
            PlaylistSong(playlistId = playlistId, songId = songId, position = maxPosition + 1)
        )
    }

    suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long) {
        playlistSongDao.deleteFromPlaylist(playlistId, songId)
    }

    fun getSongIdsInPlaylist(playlistId: Long): Flow<List<Long>> =
        playlistSongDao.getSongIdsInPlaylist(playlistId)

    fun getPlaylistSongsCount(playlistId: Long): Flow<Int> =
        playlistSongDao.getPlaylistSongsCount(playlistId)

    suspend fun clearPlaylist(playlistId: Long) = playlistSongDao.clearPlaylist(playlistId)

    // Favorite operations
    suspend fun addFavorite(songId: Long) {
        favoriteDao.addFavorite(Favorite(songId = songId))
    }

    suspend fun removeFavorite(songId: Long) {
        favoriteDao.removeFavoriteBySongId(songId)
    }

    fun getAllFavorites(): Flow<List<Favorite>> = favoriteDao.getAllFavorites()
    fun isFavorite(songId: Long): Flow<Boolean> = favoriteDao.isFavorite(songId)
    fun getFavoriteSongIds(): Flow<List<Long>> = favoriteDao.getFavoriteSongIds()
    fun getFavoritesCount(): Flow<Int> = favoriteDao.getFavoritesCount()
}
