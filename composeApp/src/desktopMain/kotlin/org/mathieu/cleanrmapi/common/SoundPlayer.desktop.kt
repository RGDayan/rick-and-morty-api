package org.mathieu.cleanrmapi.common

import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

/**
 * DesktopSoundPlayer is a concrete implementation of the SoundPlayer interface
 * designed to play sound files on desktop environments using the Java Sound API.
 *
 * This class handles the loading and playing of a specific sound file ("spawn.ogg")
 * located in the "resources/raw/" directory. It utilizes the `AudioSystem` and
 * `Clip` classes from the `javax.sound.sampled` package to manage audio playback.
 *
 * @see SoundPlayer
 * @see java.io.File
 * @see javax.sound.sampled.AudioInputStream
 * @see javax.sound.sampled.AudioSystem
 * @see javax.sound.sampled.Clip
 */
class DesktopSoundPlayer : SoundPlayer {
    override fun playSound() {
        try {
            // Charger le fichier audio
            val soundFile = File("resources/raw/spawn.ogg")
            val audioInputStream: AudioInputStream = AudioSystem.getAudioInputStream(soundFile)
            val clip: Clip = AudioSystem.getClip()
            clip.open(audioInputStream)
            clip.start()  // Lancer la lecture du son
        } catch (e: Exception) {
            e.printStackTrace()  // Gestion d'erreurs
        }
    }
}