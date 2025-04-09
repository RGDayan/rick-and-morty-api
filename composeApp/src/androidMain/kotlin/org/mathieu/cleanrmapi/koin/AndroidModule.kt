package org.mathieu.cleanrmapi.koin


import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.mathieu.cleanrmapi.common.AndroidSoundPlayer
import org.mathieu.cleanrmapi.common.SoundPlayer

/**
 * Koin module providing dependencies related to the Android platform.
 *
 * This module configures and provides instances of platform-specific components,
 * such as the [SoundPlayer] implementation for Android.
 */
val androidModule = module {
    single<SoundPlayer> { AndroidSoundPlayer(androidContext()) }
}