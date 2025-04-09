package org.mathieu.cleanrmapi.common

import android.content.Context
import android.media.MediaPlayer
import org.mathieu.cleanrmapi.R

/**
 * AndroidSoundPlayer is a concrete implementation of the SoundPlayer interface
 * that utilizes the Android MediaPlayer to play a sound resource.
 *
 * This class is responsible for managing the playback of a specific sound, in this case,
 * the "spawn" sound located in the raw resource folder. It handles the creation and
 * starting of the MediaPlayer instance.
 *
 * @property context The application context, required for accessing resources and creating
 *                  the MediaPlayer.
 * @constructor Creates an AndroidSoundPlayer instance.
 */
class AndroidSoundPlayer(private val context: Context) : SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    override fun playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.spawn)
        }
        mediaPlayer?.start()
    }
}