package org.mathieu.cleanrmapi.koin
import org.koin.dsl.module
import org.mathieu.cleanrmapi.common.DesktopSoundPlayer
import org.mathieu.cleanrmapi.common.SoundPlayer

/**
 * Koin module providing dependencies specific to the desktop environment.
 *
 * This module configures and provides instances of components that are
 * typically only relevant when running the application on a desktop platform.
 *
 *  Properties:
 *  - [desktopModule] : A Koin module containing desktop-specific dependencies.
 */
val desktopModule = module {
    single<SoundPlayer> { DesktopSoundPlayer() }
}