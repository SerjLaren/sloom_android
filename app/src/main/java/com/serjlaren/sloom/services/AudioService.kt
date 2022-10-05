package com.serjlaren.sloom.services

import android.content.Context
import android.media.MediaPlayer
import com.serjlaren.sloom.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioService @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var mediaPlayer = MediaPlayer.create(context, R.raw.guessed_sound)
    private var playSoundMutex = Mutex()

    suspend fun playGuessedSound() {
        playSoundMutex.withLock {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(context, R.raw.guessed_sound)
            }
            mediaPlayer.start()
        }
    }
}
