package com.musicplayer.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.musicplayer.models.AppTheme

class ThemeDialog(
    context: Context,
    private val onThemeSelected: (AppTheme) -> Unit
) {
    private val context = context

    fun show() {
        val radioGroup = RadioGroup(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        AppTheme.ALL_THEMES.forEachIndexed { index, theme ->
            RadioButton(context).apply {
                id = index
                text = theme.name
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                radioGroup.addView(this)
            }
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle("Select Theme")
            .setView(radioGroup)
            .setPositiveButton("Apply") { _, _ ->
                val selectedIndex = radioGroup.checkedRadioButtonId
                if (selectedIndex >= 0 && selectedIndex < AppTheme.ALL_THEMES.size) {
                    onThemeSelected(AppTheme.ALL_THEMES[selectedIndex])
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
