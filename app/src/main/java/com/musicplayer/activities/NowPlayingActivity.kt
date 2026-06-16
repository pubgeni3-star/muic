package com.musicplayer.activities

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.musicplayer.R
import com.musicplayer.database.MusicDatabase
import com.musicplayer.models.Song
import com.musicplayer.repository.MusicRepository
import com.musicplayer.utils.EqualizerManager
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch

class NowPlayingActivity : AppCompatActivity() {
    private lateinit var database: MusicDatabase
    private lateinit var repository: MusicRepository
    private var equalizerManager: EqualizerManager? = null
    private var currentSong: Song? = null
    private var isFavorited = false
    private var albumRotation: ObjectAnimator? = null

    private lateinit var albumArt: CircleImageView
    private lateinit var songTitle: TextView
    private lateinit var songArtist: TextView
    private lateinit var songAlbum: TextView
    private lateinit var currentTime: TextView
    private lateinit var totalTime: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var btnPlayPause: ImageButton
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnRepeat: ImageButton
    private lateinit var btnShuffle: ImageButton
    private lateinit var btnFavorite: ImageButton
    private lateinit var btnPlaylist: ImageButton
    private lateinit var btnEqualizer: ImageButton
    private lateinit var btnShare: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)

        database = MusicDatabase.getDatabase(this)
        repository = MusicRepository(
            database.songDao(),
            database.playlistDao(),
            database.playlistSongDao(),
            database.favoriteDao()
        )

        initializeViews()
        setupListeners()
        loadCurrentSong()
    }

    private fun initializeViews() {
        albumArt = findViewById(R.id.albumArt)
        songTitle = findViewById(R.id.songTitle)
        songArtist = findViewById(R.id.songArtist)
        songAlbum = findViewById(R.id.songAlbum)
        currentTime = findViewById(R.id.currentTime)
        totalTime = findViewById(R.id.totalTime)
        seekBar = findViewById(R.id.seekBar)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnRepeat = findViewById(R.id.btnRepeat)
        btnShuffle = findViewById(R.id.btnShuffle)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnPlaylist = findViewById(R.id.btnPlaylist)
        btnEqualizer = findViewById(R.id.btnEqualizer)
        btnShare = findViewById(R.id.btnShare)
    }

    private fun setupListeners() {
        btnPlayPause.setOnClickListener { togglePlayPause() }
        btnPrevious.setOnClickListener { playPrevious() }
        btnNext.setOnClickListener { playNext() }
        btnRepeat.setOnClickListener { toggleRepeat() }
        btnShuffle.setOnClickListener { toggleShuffle() }
        btnFavorite.setOnClickListener { toggleFavorite() }
        btnPlaylist.setOnClickListener { showPlaylistDialog() }
        btnEqualizer.setOnClickListener { showEqualizerDialog() }
        btnShare.setOnClickListener { shareSong() }
    }

    private fun loadCurrentSong() {
        val songId = intent.getLongExtra("song_id", -1L)
        if (songId != -1L) {
            lifecycleScope.launch {
                repository.getSongById(songId).collect { song ->
                    if (song != null) {
                        currentSong = song
                        updateUI(song)
                        checkFavoriteStatus(song.id)
                    }
                }
            }
        }
    }

    private fun updateUI(song: Song) {
        songTitle.text = song.title
        songArtist.text = song.artist
        songAlbum.text = song.album
        totalTime.text = formatTime(song.duration)

        if (!song.albumArt.isNullOrEmpty()) {
            Glide.with(this)
                .load(song.albumArt)
                .placeholder(R.drawable.ic_music_note)
                .into(albumArt)
        }

        startAlbumRotation()
    }

    private fun startAlbumRotation() {
        if (albumRotation == null) {
            albumRotation = ObjectAnimator.ofFloat(albumArt, "rotation", 0f, 360f).apply {
                duration = 8000
                interpolator = LinearInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                start()
            }
        }
    }

    private fun stopAlbumRotation() {
        albumRotation?.cancel()
        albumRotation = null
    }

    private fun togglePlayPause() {
        // Implement play/pause logic with your MusicService
        btnPlayPause.isSelected = !btnPlayPause.isSelected
        if (btnPlayPause.isSelected) {
            btnPlayPause.setImageResource(R.drawable.ic_pause)
            startAlbumRotation()
        } else {
            btnPlayPause.setImageResource(R.drawable.ic_play)
            stopAlbumRotation()
        }
    }

    private fun playPrevious() {
        // Implement previous song logic
    }

    private fun playNext() {
        // Implement next song logic
    }

    private fun toggleRepeat() {
        // Implement repeat logic
        btnRepeat.isSelected = !btnRepeat.isSelected
    }

    private fun toggleShuffle() {
        // Implement shuffle logic
        btnShuffle.isSelected = !btnShuffle.isSelected
    }

    private fun toggleFavorite() {
        currentSong?.let { song ->
            lifecycleScope.launch {
                if (isFavorited) {
                    repository.removeFavorite(song.id)
                    btnFavorite.setImageResource(R.drawable.ic_favorite_outline)
                } else {
                    repository.addFavorite(song.id)
                    btnFavorite.setImageResource(R.drawable.ic_favorite)
                }
                isFavorited = !isFavorited
            }
        }
    }

    private fun checkFavoriteStatus(songId: Long) {
        lifecycleScope.launch {
            repository.isFavorite(songId).collect { isFav ->
                isFavorited = isFav
                btnFavorite.setImageResource(
                    if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
                )
            }
        }
    }

    private fun showPlaylistDialog() {
        // TODO: Show playlist selection dialog
    }

    private fun showEqualizerDialog() {
        // TODO: Show equalizer dialog with sliders
    }

    private fun shareSong() {
        currentSong?.let { song ->
            val shareText = "Now playing: ${song.title} by ${song.artist}\n${song.album}"
            val intent = android.content.Intent().apply {
                action = android.content.Intent.ACTION_SEND
                putExtra(android.content.Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(android.content.Intent.createChooser(intent, "Share Song"))
        }
    }

    private fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt()
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%d:%02d", minutes, secs)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAlbumRotation()
        equalizerManager?.release()
    }
}
