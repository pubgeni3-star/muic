# 🎵 BeatFlow — Android Music Player

A full-featured, beautifully animated Android music player built in Kotlin.

---

## Features

| Feature | Details |
|---|---|
| **Music Scanner** | Scans device storage for all audio files (≥30s) via MediaStore |
| **Now Playing** | Full-screen player with album art, seekbar, time display |
| **Controls** | Play, Pause, Stop, Next, Previous |
| **Shuffle** | Random song order |
| **Repeat** | None / All / One modes |
| **Mini Player** | Persistent bottom bar while browsing |
| **Albums View** | Grid layout with album art |
| **Artists View** | List with song/album counts |
| **Search** | Filter songs by title, artist, album |
| **8 Themes** | Midnight, Ocean, Sunset, Forest, Purple Haze, Arctic, Rose Gold, Neon |
| **Dynamic Background** | Album art palette colors the Now Playing screen |
| **Animations** | Rotating album art, sound wave (Lottie), staggered list entry, button bounces |
| **Foreground Service** | Plays in background with lockscreen notification |
| **Notification Controls** | Play/Pause/Next/Prev/Stop from notification |

---

## Project Structure

```
app/src/main/
├── AndroidManifest.xml
├── java/com/musicplayer/
│   ├── activities/
│   │   ├── SplashActivity.kt       ← Animated logo splash
│   │   ├── MainActivity.kt         ← Tabs: Songs / Albums / Artists
│   │   ├── NowPlayingActivity.kt   ← Full player screen
│   │   └── ThemeActivity.kt        ← Theme picker grid
│   ├── adapters/
│   │   ├── SongsAdapter.kt
│   │   ├── AlbumsAdapter.kt
│   │   ├── ArtistsAdapter.kt
│   │   ├── ThemeAdapter.kt
│   │   └── ViewPagerAdapter.kt
│   ├── fragments/
│   │   ├── SongsFragment.kt
│   │   ├── AlbumsFragment.kt
│   │   └── ArtistsFragment.kt
│   ├── models/
│   │   ├── Song.kt
│   │   └── AppTheme.kt
│   ├── services/
│   │   └── MusicService.kt         ← MediaPlayer foreground service
│   └── utils/
│       ├── MusicScanner.kt         ← MediaStore scanner (coroutine)
│       └── ThemeManager.kt         ← Theme persistence
└── res/
    ├── anim/          ← 12 animation files
    ├── drawable/      ← All vector icons + splash gradient
    ├── layout/        ← All screen & item layouts
    ├── menu/          ← Toolbar menu
    └── values/        ← colors, strings, themes (8 themes)
```

---

## Setup in Android Studio

1. **Open** Android Studio → File → Open → select the `MusicPlayer` folder
2. **Sync** Gradle (it will download dependencies automatically)
3. **Add Lottie JSON** for the sound wave animation:
   - Download a sound wave Lottie from [lottiefiles.com](https://lottiefiles.com/search?q=sound+wave)
   - Place it at `app/src/main/res/raw/sound_wave.json`
   - Create the `raw/` folder if it doesn't exist
4. **Run** on a physical device or emulator with Android 8.0+ (API 26+)

### Permissions
The app requests:
- `READ_MEDIA_AUDIO` (Android 13+) or `READ_EXTERNAL_STORAGE` (older)
- `FOREGROUND_SERVICE` for background playback

---

## Adding More Features (Next Steps)

| Feature | How |
|---|---|
| **Equalizer** | Use `android.media.audiofx.Equalizer` |
| **Lyrics** | Integrate LrcView library + fetch from API |
| **Playlists** | Add Room database + playlist UI |
| **Sleep Timer** | `CountDownTimer` → pause service |
| **Cast to Chromecast** | Google Cast SDK |
| **Widget** | `AppWidgetProvider` with RemoteViews |
| **Visualizer** | `android.media.audiofx.Visualizer` + Canvas |

---

## Dependencies (app/build.gradle)

```
Material Components 1.11.0
Glide 4.16.0          — album art image loading
Lottie 6.3.0          — sound wave animation
Palette 1.0.0         — dynamic color from album art
CircleImageView 3.1.0 — circular album art thumbnails
Coroutines 1.7.3      — async music scanning
ViewPager2 1.0.0      — tabs
```
