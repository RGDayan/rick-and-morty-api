package org.mathieu.cleanrmapi.common

/**
 * Interface for playing sounds.
 *
 * This interface defines the contract for any class that can play a sound.
 * Implementations are responsible for loading and managing the sound resource,
 * and initiating its playback.
 * Implementations are done in target platforms in order to provide the appropriate
 * sound playback functionality.
 */
interface SoundPlayer {
    fun playSound()
}