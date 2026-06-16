package com.musicplayer.models

import android.graphics.Color

data class AppTheme(
    val name: String,
    val primaryColor: Int,
    val secondaryColor: Int,
    val backgroundColor: Int,
    val textColor: Int,
    val accentColor: Int
) {
    companion object {
        val MIDNIGHT = AppTheme(
            "Midnight",
            Color.parseColor("#1a1a2e"),
            Color.parseColor("#16213e"),
            Color.parseColor("#0f3460"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#e94560")
        )
        val OCEAN = AppTheme(
            "Ocean",
            Color.parseColor("#0077be"),
            Color.parseColor("#0096c7"),
            Color.parseColor("#00b4d8"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#00d9ff")
        )
        val SUNSET = AppTheme(
            "Sunset",
            Color.parseColor("#ff6b6b"),
            Color.parseColor("#ff8e72"),
            Color.parseColor("#ffa07a"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffd93d")
        )
        val FOREST = AppTheme(
            "Forest",
            Color.parseColor("#1b4332"),
            Color.parseColor("#2d6a4f"),
            Color.parseColor("#40916c"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#52b788")
        )
        val PURPLE_HAZE = AppTheme(
            "Purple Haze",
            Color.parseColor("#5a189a"),
            Color.parseColor("#7209b7"),
            Color.parseColor("#9d4edd"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#c77dff")
        )
        val ARCTIC = AppTheme(
            "Arctic",
            Color.parseColor("#0a1929"),
            Color.parseColor("#132f4c"),
            Color.parseColor("#1a3a52"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#90caf9")
        )
        val ROSE_GOLD = AppTheme(
            "Rose Gold",
            Color.parseColor("#b76e79"),
            Color.parseColor("#c9999a"),
            Color.parseColor("#ddbea9"),
            Color.parseColor("#3d2817"),
            Color.parseColor("#f5cac3")
        )
        val NEON = AppTheme(
            "Neon",
            Color.parseColor("#0d0221"),
            Color.parseColor("#3a0ca3"),
            Color.parseColor("#5a189a"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#ff006e")
        )

        val ALL_THEMES = listOf(
            MIDNIGHT, OCEAN, SUNSET, FOREST,
            PURPLE_HAZE, ARCTIC, ROSE_GOLD, NEON
        )
    }
}
