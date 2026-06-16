package com.musicplayer.models

data class AppTheme(
    val name: String,
    val primaryColor: Int,
    val accentColor: Int,
    val backgroundColor: Int
) {
    companion object {
        val DARK_THEME = AppTheme(
            name = "Dark",
            primaryColor = 0xFF1a1a2e.toInt(),
            accentColor = 0xFFe94560.toInt(),
            backgroundColor = 0xFF0f0f1e.toInt()
        )

        val LIGHT_THEME = AppTheme(
            name = "Light",
            primaryColor = 0xFFf5f5f5.toInt(),
            accentColor = 0xFFe94560.toInt(),
            backgroundColor = 0xFFffffff.toInt()
        )

        val PURPLE_THEME = AppTheme(
            name = "Purple",
            primaryColor = 0xFF2d1b69.toInt(),
            accentColor = 0xFFb88ce3.toInt(),
            backgroundColor = 0xFF1a0f33.toInt()
        )

        val ALL_THEMES = listOf(DARK_THEME, LIGHT_THEME, PURPLE_THEME)
    }
}
