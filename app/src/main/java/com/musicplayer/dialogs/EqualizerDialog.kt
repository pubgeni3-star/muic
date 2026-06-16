package com.musicplayer.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.TextView
import com.musicplayer.databinding.DialogEqualizerBinding
import com.musicplayer.utils.EqualizerManager

class EqualizerDialog(
    context: Context,
    private val equalizerManager: EqualizerManager
) {
    private lateinit var binding: DialogEqualizerBinding
    private val dialog: AlertDialog

    init {
        binding = DialogEqualizerBinding.inflate(LayoutInflater.from(context))
        setupSliders()

        dialog = AlertDialog.Builder(context)
            .setTitle("Equalizer")
            .setView(binding.root)
            .setPositiveButton("Close", null)
            .setNegativeButton("Reset") { _, _ ->
                equalizerManager.resetAllBands()
                resetUI()
            }
            .create()
    }

    private fun setupSliders() {
        binding.apply {
            bassSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        equalizerManager.setBassBoost(progress.toShort())
                        bassValue.text = "Bass: $progress"
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            midrangeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        equalizerManager.setMidrangeBoost(progress.toShort())
                        midrangeValue.text = "Midrange: $progress"
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            trebleSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        equalizerManager.setTrebleBoost(progress.toShort())
                        trebleValue.text = "Treble: $progress"
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    private fun resetUI() {
        binding.apply {
            bassSeekbar.progress = 50
            midrangeSeekbar.progress = 50
            trebleSeekbar.progress = 50
            bassValue.text = "Bass: 50"
            midrangeValue.text = "Midrange: 50"
            trebleValue.text = "Treble: 50"
        }
    }

    fun show() {
        dialog.show()
    }
}
