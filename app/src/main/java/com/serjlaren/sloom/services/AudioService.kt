package com.serjlaren.sloom.services

import android.content.Context
import android.media.MediaPlayer
import com.serjlaren.sloom.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


/*
* Created by Sergey Larionov (27.06.2022)
*/

@Singleton
class AudioService @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private var mediaPlayer = MediaPlayer.create(context, R.raw.guessed_sound)

    @Synchronized
    fun playGuessedSound() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(context, R.raw.guessed_sound)
            }
            mediaPlayer.start()
        } catch (e: Exception) {}
    }
}