package com.musicplayer.utils

import android.media.MediaPlayer
import android.media.audiofx.Equalizer
import android.util.Log

class EqualizerManager(private val mediaPlayer: MediaPlayer?) {
    private var equalizer: Equalizer? = null
    private val bands = mutableMapOf<Int, Short>()

    init {
        try {
            if (mediaPlayer != null && mediaPlayer.audioSessionId > 0) {
                equalizer = Equalizer(100, mediaPlayer.audioSessionId)
                equalizer?.enabled = true
                initializeBands()
            }
        } catch (e: Exception) {
            Log.e("EqualizerManager", "Error initializing equalizer", e)
        }
    }

    private fun initializeBands() {
        equalizer?.let { eq ->
            for (i in 0 until eq.numberOfBands) {
                bands[i] = eq.getBandLevel(i.toShort())
            }
        }
    }

    fun setBassBoost(value: Short) {
        val bassIndex = 0 // Usually the first band is bass
        if (equalizer != null && bassIndex < (equalizer?.numberOfBands ?: 0)) {
            try {
                val min = equalizer?.bandLevelRange?.get(0) ?: return
                val max = equalizer?.bandLevelRange?.get(1) ?: return
                val normalizedValue = (min + ((value / 100f) * (max - min))).toShort()
                equalizer?.setBandLevel(bassIndex.toShort(), normalizedValue)
                bands[bassIndex] = normalizedValue
            } catch (e: Exception) {
                Log.e("EqualizerManager", "Error setting bass boost", e)
            }
        }
    }

    fun setTrebleBoost(value: Short) {
        val trebleIndex = (equalizer?.numberOfBands ?: 1) - 1 // Last band is usually treble
        if (equalizer != null && trebleIndex >= 0 && trebleIndex < (equalizer?.numberOfBands ?: 0)) {
            try {
                val min = equalizer?.bandLevelRange?.get(0) ?: return
                val max = equalizer?.bandLevelRange?.get(1) ?: return
                val normalizedValue = (min + ((value / 100f) * (max - min))).toShort()
                equalizer?.setBandLevel(trebleIndex.toShort(), normalizedValue)
                bands[trebleIndex] = normalizedValue
            } catch (e: Exception) {
                Log.e("EqualizerManager", "Error setting treble boost", e)
            }
        }
    }

    fun setMidrangeBoost(value: Short) {
        val midIndex = ((equalizer?.numberOfBands ?: 5) / 2) // Middle band
        if (equalizer != null && midIndex >= 0 && midIndex < (equalizer?.numberOfBands ?: 0)) {
            try {
                val min = equalizer?.bandLevelRange?.get(0) ?: return
                val max = equalizer?.bandLevelRange?.get(1) ?: return
                val normalizedValue = (min + ((value / 100f) * (max - min))).toShort()
                equalizer?.setBandLevel(midIndex.toShort(), normalizedValue)
                bands[midIndex] = normalizedValue
            } catch (e: Exception) {
                Log.e("EqualizerManager", "Error setting midrange boost", e)
            }
        }
    }

    fun resetAllBands() {
        try {
            equalizer?.let { eq ->
                for (i in 0 until eq.numberOfBands) {
                    eq.setBandLevel(i.toShort(), 0)
                    bands[i] = 0
                }
            }
        } catch (e: Exception) {
            Log.e("EqualizerManager", "Error resetting bands", e)
        }
    }

    fun enable(enable: Boolean) {
        try {
            equalizer?.enabled = enable
        } catch (e: Exception) {
            Log.e("EqualizerManager", "Error enabling/disabling equalizer", e)
        }
    }

    fun release() {
        try {
            equalizer?.release()
            equalizer = null
        } catch (e: Exception) {
            Log.e("EqualizerManager", "Error releasing equalizer", e)
        }
    }

    fun isAvailable(): Boolean = equalizer != null
}
